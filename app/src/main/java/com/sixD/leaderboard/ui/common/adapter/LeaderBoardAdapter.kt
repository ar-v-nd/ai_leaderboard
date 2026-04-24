package com.sixD.leaderboard.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sixD.leaderboard.databinding.ItemLeaderboardBinding
import com.sixD.leaderboard.ui.common.model.AiDataModel

/**
 * [RecyclerView.Adapter] for the leaderboard list.
 * Uses [ListAdapter] with [RankingDiffUtil] for efficient list updates.
 */
class LeaderBoardAdapter : ListAdapter<AiDataModel, LeaderBoardAdapter.LeaderBoardViewHolder>(
    RankingDiffUtil()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderBoardViewHolder {
        val binding = ItemLeaderboardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LeaderBoardViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LeaderBoardViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), position, itemCount)
    }

    /**
     * ViewHolder class for leaderboard items.
     * Handles data binding and conditional padding/visibility based on item position.
     */
    inner class LeaderBoardViewHolder(
        private val binding: ItemLeaderboardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Binds the [AiDataModel] to the layout.
         * @param item The data model to bind.
         * @param position Current position in the list.
         * @param totalCount Total number of items in the list.
         */
        fun bind(item: AiDataModel, position: Int, totalCount: Int) {
            binding.model = item
            binding.isFirstItem = (position == 0)
            binding.isLastItem = (position == totalCount - 1)
            binding.executePendingBindings()
        }
    }
}
