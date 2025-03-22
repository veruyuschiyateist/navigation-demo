package com.plko.bls.app.model

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val itemsFlow = MutableStateFlow(List(5) { "Item ${it + 1}" })

    suspend fun add(item: String) {
        delay(2000L)
        itemsFlow.update { it + item }
    }

    fun getItems(): Flow<List<String>> {
        return itemsFlow.onStart { delay(3000L) }
    }


}