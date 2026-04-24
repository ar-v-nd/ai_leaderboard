package com.sixD.leaderboard.data.mapper

import com.sixD.leaderboard.data.model.LeaderBoardItem
import com.sixD.leaderboard.ui.main.model.AiDataModel

/**
 * Mapper object to convert API response models to domain-level data models.
 */
object LeaderBoardMapper {

    /**
     * Transforms a single [LeaderBoardItem] into an [AiDataModel].
     *
     * @param item The source API data model.
     * @param rank The calculated numerical rank to assign to the model.
     * @return A mapped [AiDataModel] ready for UI use.
     */
    fun mapToAiDataModel(item: LeaderBoardItem, rank: Int = 0): AiDataModel {
        return AiDataModel(
            category = item.category,
            company = item.company,
            description = item.description,
            modelID = item.modelID,
            name = item.name,
            poster = item.poster,
            rating = item.rating,
            released = item.released,
            rank = rank.toString()
        )
    }

    /**
     * Transforms a list of [LeaderBoardItem] into a sorted list of [AiDataModel].
     * The list is sorted in descending order by rating before mapping.
     *
     * @param items The list of source API items.
     * @return A sorted and mapped list of [AiDataModel] objects.
     */
    fun mapToAiDataModelList(items: List<LeaderBoardItem>): List<AiDataModel> {
        return items.sortedByDescending { it.rating.toDoubleOrNull() ?: 0.0 }.mapIndexed { index, item ->
            mapToAiDataModel(item, rank = index + 1)
        }
    }
}
