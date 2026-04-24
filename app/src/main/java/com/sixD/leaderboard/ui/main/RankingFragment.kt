package com.sixD.leaderboard.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sixD.leaderboard.databinding.FragmentRankingBinding
import com.sixD.leaderboard.ui.common.adapter.LeaderBoardAdapter
import com.sixD.leaderboard.util.DialogUtils

/**
 * A [Fragment] that displays the leaderboard ranking.
 * It shows the top 3 users in a prominent "Top 3" section and the rest of the users in a scrollable list.
 *
 * This fragment handles:
 * - Back-press events to exit the application.
 * - Refreshing data via [SwipeRefreshLayout].
 * - Displaying error and empty state dialogs.
 */
class RankingFragment : Fragment() {

    private var _binding: FragmentRankingBinding? = null

    /**
     * Binding instance for accessing UI elements in fragment_ranking.xml.
     * Valid only between [onCreateView] and [onDestroyView].
     */
    private val binding get() = requireNotNull(_binding)

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: LeaderBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Intercept back button to exit the app directly from this screen
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })

        setUpUI()
        observeData()
    }

    /**
     * Initializes the RecyclerView adapter, layout manager, and click listeners.
     */
    private fun setUpUI() {
        adapter = LeaderBoardAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)

            iconClose.setOnClickListener {
                requireActivity().finish()
            }
        }
    }

    /**
     * Sets up Observers for the ViewModel's data and error states.
     */
    private fun observeData() {
        // Observe leaderboard data
        viewModel.data.observe(viewLifecycleOwner) { data ->
            if (data != null && data.isNotEmpty()) {
                // Submit list excluding the top 3 items which are shown in top3_container
                data.drop(3).let {
                    adapter.submitList(it)
                }
            } else if (data != null) {
                // Handle empty list case
                DialogUtils.showApiErrorDialog(
                    context = requireContext(),
                    errorMessage = "No data found",
                    onRetry = {
                        viewModel.loadData()
                    },
                    onClose = {
                        requireActivity().finish()
                    })
            }
        }

        // Observe error states
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                when (it) {
                    is MainViewModel.ErrorType.NetworkError -> {
                        DialogUtils.showNoInternetDialog(context = requireContext(), onRetry = {
                            viewModel.loadData()
                        }, onClose = {
                            requireActivity().finish()
                        })
                    }

                    is MainViewModel.ErrorType.ApiError -> {
                        DialogUtils.showApiErrorDialog(
                            context = requireContext(),
                            errorMessage = it.message,
                            onRetry = {
                                viewModel.loadData()
                            },
                            onClose = {
                                requireActivity().finish()
                            })
                    }
                }
                viewModel.clearError() // Reset error state after showing dialog
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
