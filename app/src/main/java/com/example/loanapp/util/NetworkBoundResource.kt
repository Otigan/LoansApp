package com.example.loanapp.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    responseHandler: ResponseHandler
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            saveFetchResult(fetch())
            query().map {
                Resource.Success(it)
            }
        } catch (e: Exception) {
            query().map {
                responseHandler.handleException(e, it)
            }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}