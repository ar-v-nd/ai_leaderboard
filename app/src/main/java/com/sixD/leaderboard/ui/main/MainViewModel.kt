package com.sixD.leaderboard.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixD.leaderboard.data.repository.LeaderBoardRepository
import com.sixD.leaderboard.ui.common.model.AiDataModel
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Shared ViewModel for managing leaderboard data and UI state.
 * Handles data fetching, refresh state, and error communication via sealed [ErrorType].
 */
class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val repository = LeaderBoardRepository()

    private val _data = MutableLiveData<List<AiDataModel>>()
    val data: LiveData<List<AiDataModel>> = _data

    private val _isRefreshing = MutableLiveData<Boolean>(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _error = MutableLiveData<ErrorType?>()
    val error: LiveData<ErrorType?> = _error

    /**
     * Fetches leaderboard data from the repository.
     * Updates [data], [isRefreshing], and [error] LiveData based on the result.
     * Uses [viewModelScope] for safe coroutine management.
     */
    fun loadData() {
        Log.d(TAG, "loadData() called")
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                Log.d(TAG, "Launching coroutine to fetch data")
                val result = repository.fetchLeaderBoardAsAiDataModel()
                _data.value = result
                _error.value = null
                Log.d(TAG, "Data loaded successfully. Items count: ${result.size}")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading data", e)
                if (e is IOException) {
                    _error.value = ErrorType.NetworkError
                } else {
                    _error.value = ErrorType.ApiError(e.message ?: "Unknown Error")
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    /**
     * Safely retrieves an [AiDataModel] at the specified [index] from the current [data].
     * @param index The position of the item in the list.
     * @return The item if the index is valid, otherwise null.
     */
    fun getData(index: Int): AiDataModel? {
        return data.value?.getOrNull(index)
    }

    /**
     * Clears the current error state.
     */
    fun clearError() {
        _error.value = null
    }

    /**
     * Sealed class representing different types of errors that can occur during data operations.
     */
    sealed class ErrorType {
        /** Represents a network connectivity issue. */
        object NetworkError : ErrorType()

        /**
         * Represents an error returned by the API or an unexpected exception.
         * @property message The error message to display.
         */
        data class ApiError(val message: String) : ErrorType()
    }
}
