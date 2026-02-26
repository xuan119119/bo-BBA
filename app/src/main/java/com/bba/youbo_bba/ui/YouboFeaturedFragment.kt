package com.bba.youbo_bba.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bba.youbo_bba.databinding.FragmentYouboFeaturedBinding

/**
 * 有播 - 精选（含搜索）
 */
class YouboFeaturedFragment : Fragment() {

    private var _binding: FragmentYouboFeaturedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYouboFeaturedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearch()
    }

    private fun initSearch() {
        fun doSearch() {
            val keyword = binding.etSearch.text.toString().trim()
            if (keyword.isEmpty()) {
                Toast.makeText(requireContext(), "请输入搜索内容", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "搜索：$keyword", Toast.LENGTH_SHORT).show()
                // TODO: 在这里接入真正的搜索逻辑
            }
        }

        binding.btnSearch.setOnClickListener { doSearch() }

        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP)
            ) {
                doSearch()
                true
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

