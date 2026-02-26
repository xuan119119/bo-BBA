package com.bba.youbo_bba.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bba.youbo_bba.databinding.FragmentHaokanBinding
import com.bba.youbo_bba.viewmodel.HaokanViewModel

/**
 * 好看 Fragment (MVVM)
 */
class HaokanFragment : Fragment() {

    private var _binding: FragmentHaokanBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HaokanViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHaokanBinding.inflate(inflater, container, false)
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
