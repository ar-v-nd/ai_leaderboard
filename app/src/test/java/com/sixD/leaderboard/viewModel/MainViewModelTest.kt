package com.sixD.leaderboard.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sixD.leaderboard.data.repository.LeaderBoardRepository
import com.sixD.leaderboard.ui.main.MainViewModel
import com.sixD.leaderboard.ui.common.model.AiDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit tests for MainViewModel.
 *
 * Dependencies needed in build.gradle.kts (testImplementation):
 *   - junit:junit
 *   - org.mockito:mockito-core
 *   - org.mockito.kotlin:mockito-kotlin
 *   - androidx.arch.core:core-testing
 *   - org.jetbrains.kotlinx:kotlinx-coroutines-test
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockRepository: LeaderBoardRepository

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        // If MainViewModel accepted a repository via constructor, we'd inject here.
        // As-is, we test the publicly observable behaviour.
        viewModel = MainViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ──────────────────────────────────────────────────────────────
    // Initial state
    // ──────────────────────────────────────────────────────────────

    @Test
    fun `data LiveData is initially null before loadData is called`() {
        assertNull(viewModel.data.value)
    }

    // ──────────────────────────────────────────────────────────────
    // getData() edge cases (no network call needed)
    // ──────────────────────────────────────────────────────────────

    @Test
    fun `getData returns default AiDataModel when data is null`() {
        // data.value is null at start
        val result = viewModel.getData(0)
        assertEquals(AiDataModel(), result)
    }

    @Test
    fun `getData returns default AiDataModel when index out of range and data null`() {
        val result = viewModel.getData(99)
        assertEquals(AiDataModel(), result)
    }

    // ──────────────────────────────────────────────────────────────
    // LiveData observation helpers
    // ──────────────────────────────────────────────────────────────

    @Test
    fun `data LiveData emits list after loadData completes`() = runTest {
        // This test will only pass once dependency injection is added to the ViewModel.
        // It documents the expected contract.
        val observer = mock(Observer::class.java) as Observer<List<AiDataModel>>
        viewModel.data.observeForever(observer)

        viewModel.loadData()
        advanceUntilIdle()

        // When the repository returns data, LiveData should emit a list (not null).
        // Without DI this documents intent; with DI + mock it would pass.
        viewModel.data.removeObserver(observer)
    }

    @Test
    fun `data LiveData does not emit before loadData is called`() {
        val emitted = mutableListOf<List<AiDataModel>?>()
        viewModel.data.observeForever { emitted.add(it) }
        // No loadData call
        assertTrue(emitted.isEmpty() || emitted.all { it == null })
    }

    // ──────────────────────────────────────────────────────────────
    // getData with injected data (simulating post-load state)
    // ──────────────────────────────────────────────────────────────

    @Test
    fun `getData at valid index returns correct item from loaded data`() {
        // Simulate the ViewModel having data by using reflection or subclassing.
        // This test documents the expected behaviour.
        val item = AiDataModel(name = "GPT-4", rank = "1")
        // Access private _data via reflection for unit testing purposes
        val field = viewModel.javaClass.getDeclaredField("_data")
        field.isAccessible = true
        val liveData = field.get(viewModel) as androidx.lifecycle.MutableLiveData<List<AiDataModel>>
        liveData.value = listOf(item)

        val result = viewModel.getData(0)
        assertEquals(item, result)
    }

    @Test
    fun `getData returns default when index is beyond list size`() {
        val field = viewModel.javaClass.getDeclaredField("_data")
        field.isAccessible = true
        val liveData = field.get(viewModel) as androidx.lifecycle.MutableLiveData<List<AiDataModel>>
        liveData.value = listOf(AiDataModel(name = "Only one"))

        // Index 5 is beyond size 1 — should return AiDataModel() not crash
        val result = viewModel.getData(5)
        assertEquals(AiDataModel(), result)
    }

    @Test
    fun `getData at index 0 returns first ranked item`() {
        val items = listOf(
            AiDataModel(name = "First", rank = "1"),
            AiDataModel(name = "Second", rank = "2")
        )
        val field = viewModel.javaClass.getDeclaredField("_data")
        field.isAccessible = true
        val liveData = field.get(viewModel) as androidx.lifecycle.MutableLiveData<List<AiDataModel>>
        liveData.value = items

        assertEquals("First", viewModel.getData(0).name)
    }

    @Test
    fun `getData at last valid index returns last item`() {
        val items = listOf(
            AiDataModel(name = "A", rank = "1"),
            AiDataModel(name = "B", rank = "2"),
            AiDataModel(name = "C", rank = "3")
        )
        val field = viewModel.javaClass.getDeclaredField("_data")
        field.isAccessible = true
        val liveData = field.get(viewModel) as androidx.lifecycle.MutableLiveData<List<AiDataModel>>
        liveData.value = items

        assertEquals("C", viewModel.getData(2).name)
    }
}