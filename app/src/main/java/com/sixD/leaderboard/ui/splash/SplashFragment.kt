package com.sixD.leaderboard.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sixD.leaderboard.R
import com.sixD.leaderboard.databinding.FragmentSplashBinding
import com.sixD.leaderboard.util.DialogUtils
import com.sixD.leaderboard.ui.main.MainViewModel

/**
 * A [Fragment] representing the splash screen of the application.
 * It is responsible for initiating the initial data fetch and navigating to the [RankingFragment]
 * once data is successfully loaded.
 */
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    /**
     * Binding instance for accessing UI elements in fragment_splash.xml.
     * Valid only between [onCreateView] and [onDestroyView].
     */
    private val binding get() = requireNotNull(_binding)

    private val viewModel: SplashViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData() 
    }

    /**
     * Observes [MainViewModel] and [SplashViewModel] LiveData to handle data loading and navigation.
     */
    private fun observeLiveData() {
        // Observe leaderboard data to trigger navigation or show error
        mainViewModel.data.observe(viewLifecycleOwner) { data ->
            if (data != null && data.isNotEmpty()) {
                viewModel.startNavigation()
            } else if (data != null) {
                // Handle empty list case with a retry/close dialog
                DialogUtils.showApiErrorDialog(
                    context = requireContext(),
                    errorMessage = "No data found",
                    onRetry = { mainViewModel.loadData() },
                    onClose = { requireActivity().finish() }
                )
            }
        }
        
        // Trigger initial data load
        viewModel.isProgressVisible.observe(viewLifecycleOwner) {
            mainViewModel.loadData()
        }

        // Handle error states from the MainViewModel
        mainViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                when (it) {
                    is MainViewModel.ErrorType.NetworkError -> {
                        DialogUtils.showNoInternetDialog(
                            context = requireContext(),
                            onRetry = { mainViewModel.loadData() },
                            onClose = { requireActivity().finish() }
                        )
                    }
                    is MainViewModel.ErrorType.ApiError -> {
                        DialogUtils.showApiErrorDialog(
                            context = requireContext(),
                            errorMessage = it.message,
                            onRetry = { mainViewModel.loadData() },
                            onClose = { requireActivity().finish() }
                        )
                    }
                }
                mainViewModel.clearError() // Reset error state
            }
        }

        // Observe navigation trigger
        viewModel.isStartNavigation.observe(viewLifecycleOwner) { isStartNavigation ->
            if (isStartNavigation) {
                findNavController().navigate(R.id.action_splash_to_ranking)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
