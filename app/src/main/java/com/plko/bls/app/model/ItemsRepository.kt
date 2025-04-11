package com.plko.bls.app.model

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepository @Inject constructor() {

    private val itemsFlow = MutableStateFlow(List(5) { "Item ${it + 1}" })

    suspend fun add(item: String) {
        delay(2000L)

        verifyNowDuplicates(item)

        itemsFlow.update { it + item }
    }

    fun getItems(): Flow<List<String>> {
        return itemsFlow.onStart { delay(3000L) }
    }

    suspend fun getItem(index: Int): String {
        delay(1000)

        if (index == 0) throw LoadDataException()

        return itemsFlow.value[index]
    }

    suspend fun update(index: Int, item: String) {
        delay(2000)

        verifyNowDuplicates(item, index)

        itemsFlow.update { oldList ->
            oldList.toMutableList().apply { set(index, item) }
        }
    }

    private fun verifyNowDuplicates(title: String, index: Int = 0) {
        val duplicatedItemIndex = itemsFlow.value.indexOf(title)
        if (duplicatedItemIndex != -1 && duplicatedItemIndex != index) {
            throw DuplicateException(title)
        }
    }
}