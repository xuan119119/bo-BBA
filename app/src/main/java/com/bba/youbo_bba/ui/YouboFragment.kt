package com.bba.youbo_bba.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
        initTabs()
        observeUiState()
        viewModel.loadData()
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            // 保留与 ViewModel 的绑定，如需可传递给子 Fragment 使用
            binding.tvContent.text = state.content
        }
    }

    private fun initTabs() {
        // 默认选中“精选”
        selectTab(Tab.FEATURED)

        binding.tabFollow.setOnClickListener { selectTab(Tab.FOLLOW) }
        binding.tabFeatured.setOnClickListener { selectTab(Tab.FEATURED) }
        binding.tabNearby.setOnClickListener { selectTab(Tab.NEARBY) }

        binding.ivLeftAction.setOnClickListener {
            // TODO: 打开日历 / 关注等
        }
        binding.ivRightAction.setOnClickListener {
            // TODO: 打开扫一扫 / 全屏等
        }
    }

    private fun selectTab(tab: Tab) {
        val selectedColor = 0xFF333333.toInt()
        val normalColor = 0xFF808080.toInt()

        binding.tvFollow.setTextColor(if (tab == Tab.FOLLOW) selectedColor else normalColor)
        binding.tvFeatured.setTextColor(if (tab == Tab.FEATURED) selectedColor else normalColor)
        binding.tvNearby.setTextColor(if (tab == Tab.NEARBY) selectedColor else normalColor)

        binding.indicatorFollow.isVisible = tab == Tab.FOLLOW
        binding.indicatorFeatured.isVisible = tab == Tab.FEATURED
        binding.indicatorNearby.isVisible = tab == Tab.NEARBY

        val fragment: Fragment = when (tab) {
            Tab.FOLLOW -> YouboFollowFragment()
            Tab.FEATURED -> YouboFeaturedFragment()
            Tab.NEARBY -> YouboNearbyFragment()
        }

        childFragmentManager
            .beginTransaction()
            .replace(binding.contentContainer.id, fragment)
            .commit()
    }

    private enum class Tab {
        FOLLOW, FEATURED, NEARBY
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
