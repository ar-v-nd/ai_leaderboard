package com.sixD.leaderboard.ui.common.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sixD.leaderboard.ui.common.model.AiDataModel

/**
 * [DiffUtil.ItemCallback] implementation for [AiDataModel].
 * Used by [LeaderBoardAdapter] to calculate the difference between two lists and optimize updates.
 */
class RankingDiffUtil : DiffUtil.ItemCallback<AiDataModel>() {
    /**
     * Checks if two items represent the same conceptual object (e.g., by ID or unique rank).
     */
    override fun areItemsTheSame(oldItem: AiDataModel, newItem: AiDataModel): Boolean {
        return oldItem.rank == newItem.rank
    }

    /**
     * Checks if the contents of two items are identical.
     */
    override fun areContentsTheSame(oldItem: AiDataModel, newItem: AiDataModel): Boolean {
        return oldItem == newItem
    }
}
