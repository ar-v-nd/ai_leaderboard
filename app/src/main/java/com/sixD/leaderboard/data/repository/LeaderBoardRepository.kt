package com.sixD.leaderboard.data.repository

import android.util.Log
import com.sixD.leaderboard.data.api.RetrofitClient
import com.sixD.leaderboard.data.mapper.LeaderBoardMapper
import com.sixD.leaderboard.ui.main.model.AiDataModel

/**
 * Repository class responsible for fetching leaderboard data from the remote API.
 */
class LeaderBoardRepository {

    companion object {
        private const val TAG = "LeaderBoardRepository"
    }

    /**
     * Fetches leaderboard data from the API and maps it to the domain model [AiDataModel].
     *
     * @return A list of [AiDataModel] objects representing the leaderboard.
     * @throws Exception if the network request fails or data mapping fails.
     */
    suspend fun fetchLeaderBoardAsAiDataModel(): List<AiDataModel> {
        Log.d(TAG, "Starting API call to fetch leaderboard data")
        try {
            val leaderBoardData = RetrofitClient.api.getLeaderBoardData()
            Log.d(TAG, "API call successful. Mapped items count: ${leaderBoardData.leaderBoardItems.size}")

            val aiDataModels = LeaderBoardMapper.mapToAiDataModelList(leaderBoardData.leaderBoardItems)
            Log.d(TAG, "Mapped ${aiDataModels.size} items to AiDataModel")

            return aiDataModels
        } catch (e: Exception) {
            Log.e(TAG, "API call failed", e)
            throw e
        }
    }
}
