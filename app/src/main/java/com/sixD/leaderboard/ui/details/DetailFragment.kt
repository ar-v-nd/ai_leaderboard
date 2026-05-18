package com.sixD.leaderboard.ui.details

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sixD.leaderboard.databinding.FragmentDetailBinding
import com.sixD.leaderboard.ui.common.model.AiDataModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val aiDataModel: AiDataModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("data", AiDataModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable("data") as? AiDataModel
        }

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        Log.d("Details Fragment", "onCreateView: Received AiDataModel: $aiDataModel.name")

        binding.data = aiDataModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}