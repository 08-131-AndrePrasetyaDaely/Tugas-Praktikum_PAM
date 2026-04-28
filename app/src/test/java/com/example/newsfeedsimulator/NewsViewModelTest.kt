package com.example.newsfeedsimulator

import com.example.newsfeedsimulator.viewmodel.NewsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private val viewModel = NewsViewModel()

    @Test
    fun `test incrementReadCount updates StateFlow`() = runTest {
        assertEquals(0, viewModel.readCount.value)
        viewModel.incrementReadCount()
        assertEquals(1, viewModel.readCount.value)
    }

    @Test
    fun `test fetchNewsDetailAsync returns correct string`() = runTest {
        val result = viewModel.fetchNewsDetailAsync(123)
        assertTrue(result.contains("ID 123"))
    }

    @Test
    fun `test flow emits transformed data`() = runTest {
        val firstItem = viewModel.newsDisplayFlow.first()
        // Cek apakah data sudah ditransform (ada kurung siku di category)
        assertTrue(firstItem.category.startsWith("["))
        assertTrue(firstItem.category.endsWith("]"))
    }
}
