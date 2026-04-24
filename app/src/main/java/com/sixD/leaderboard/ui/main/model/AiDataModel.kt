package com.sixD.leaderboard.ui.main.model

/**
 * Data model representing an AI entry in the leaderboard.
 *
 * @property category The category of the AI model.
 * @property company The company that developed the AI model.
 * @property description A brief description of the AI model.
 * @property modelID Unique identifier for the model.
 * @property name The display name of the AI model.
 * @property poster URL for the model's avatar or poster image.
 * @property rating The performance rating of the model.
 * @property released The release date or status of the model.
 * @property rank The numerical rank of the model in the leaderboard.
 */
data class AiDataModel(
    val category: String = "",
    val company: String = "",
    val description: String = "",
    val modelID: String = "",
    val name: String = "",
    val poster: String = "",
    val rating: String = "",
    val released: String = "",
    val rank: String = ""
)
