package com.bba.youbo_bba.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bba.youbo_bba.databinding.FragmentYouboBinding
import com.bba.youbo_bba.viewmodel.YouboViewModel

/**
 * 有播 Fragment (MVVM)
 */
class YouboFragment : Fragment() {

    private var _binding: FragmentYouboBinding? = null
    private val binding get() = _binding!!

    private val viewModel: YouboViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYouboBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        viewModel.loadData()
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.tvContent.text = state.content
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
