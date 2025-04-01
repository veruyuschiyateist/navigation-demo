package com.plko.bls.app.model

sealed class LoadResult<out T> {

    data object Loading : LoadResult<Nothing>()

    data class Success<T>(val data: T) : LoadResult<T>()
}