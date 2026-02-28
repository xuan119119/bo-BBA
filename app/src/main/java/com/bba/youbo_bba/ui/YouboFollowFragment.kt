package com.bba.youbo_bba.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bba.youbo_bba.R
import com.bba.youbo_bba.databinding.FragmentYouboFollowBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

/**
 * 有播 - 关注
 */
class YouboFollowFragment : Fragment() {

    private var _binding: FragmentYouboFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentYouboFollowBinding.inflate(inflater, container, false)
        Glide.with(this)
            .load(R.drawable.tx1)
            .transform(CircleCrop())
            .into(binding.ivAvatar1)

        Glide.with(this)
            .load(R.drawable.tx2)
            .transform(CircleCrop())
            .into(binding.ivAvatar2)

        Glide.with(this)
            .load(R.drawable.tx3)
            .transform(CircleCrop())
            .into(binding.ivAvatar3)

        Glide.with(this)
            .load(R.drawable.tx1)  // 第4个用 tx1
            .transform(CircleCrop())
            .into(binding.ivAvatar4)

        Glide.with(this)
            .load(R.drawable.tx2)  // 第5个用 tx2
            .transform(CircleCrop())
            .into(binding.ivAvatar5)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}

