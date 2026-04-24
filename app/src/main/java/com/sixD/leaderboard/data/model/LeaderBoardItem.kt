package com.sixD.leaderboard.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardItem(
    @SerialName("Category")
    val category: String = "",
    @SerialName("Company")
    val company: String = "",
    @SerialName("Description")
    val description: String = "",
    @SerialName("modelID")
    val modelID: String = "",
    @SerialName("Name")
    val name: String = "",
    @SerialName("Poster")
    val poster: String = "",
    @SerialName("Rating")
    val rating: String = "",
    @SerialName("Released")
    val released: String = ""
)