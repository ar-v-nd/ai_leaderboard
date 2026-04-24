package com.sixD.leaderboard.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardModel(
    @SerialName("models")
    val leaderBoardItems: List<LeaderBoardItem> = listOf()
)