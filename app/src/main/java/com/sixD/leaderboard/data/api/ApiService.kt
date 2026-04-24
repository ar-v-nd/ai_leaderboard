package com.sixD.leaderboard.data.api

import com.sixD.leaderboard.data.model.LeaderBoardModel
import retrofit2.http.GET

/**
 * Interface defining the API endpoints for the leaderboard application.
 */
interface ApiService {

    /**
     * Retrieves the leaderboard data from the remote server.
     * @return A [LeaderBoardModel] containing a list of leaderboard items.
     */
    @GET("v1/3c705a4c-aac7-4173-990d-bd31996e850b")
    suspend fun getLeaderBoardData(): LeaderBoardModel
}
