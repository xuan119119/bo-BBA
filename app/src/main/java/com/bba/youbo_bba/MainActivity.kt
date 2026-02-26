package com.bba.youbo_bba

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bba.youbo_bba.databinding.ActivityMainBinding
import com.bba.youbo_bba.ui.HaokanFragment
import com.bba.youbo_bba.ui.MineFragment
import com.bba.youbo_bba.ui.MessageFragment
import com.bba.youbo_bba.ui.YouboFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            val youboFragment = YouboFragment()
            val haokanFragment = HaokanFragment()
            val messageFragment = MessageFragment()
            val mineFragment = MineFragment()
            supportFragmentManager.beginTransaction().apply {
                add(R.id.nav_host_fragment, mineFragment, TAG_MINE)
                add(R.id.nav_host_fragment, messageFragment, TAG_MESSAGE)
                add(R.id.nav_host_fragment, haokanFragment, TAG_HAOKAN)
                add(R.id.nav_host_fragment, youboFragment, TAG_YOUBO)
                hide(mineFragment)
                hide(messageFragment)
                hide(haokanFragment)
                commitNow()
            }
            activeFragment = youboFragment
        } else {
            activeFragment = supportFragmentManager.findFragmentByTag(
                when (savedInstanceState.getInt(KEY_SELECTED_NAV, R.id.nav_youbo)) {
                    R.id.nav_haokan -> TAG_HAOKAN
                    R.id.nav_message -> TAG_MESSAGE
                    R.id.nav_mine -> TAG_MINE
                    else -> TAG_YOUBO
                }
            ) ?: supportFragmentManager.findFragmentByTag(TAG_YOUBO)
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_youbo -> supportFragmentManager.findFragmentByTag(TAG_YOUBO)
                R.id.nav_haokan -> supportFragmentManager.findFragmentByTag(TAG_HAOKAN)
                R.id.nav_message -> supportFragmentManager.findFragmentByTag(TAG_MESSAGE)
                R.id.nav_mine -> supportFragmentManager.findFragmentByTag(TAG_MINE)
                else -> null
            }
            if (fragment != null) switchFragment(fragment) else false
        }
        binding.bottomNavigation.selectedItemId = savedInstanceState?.getInt(KEY_SELECTED_NAV, R.id.nav_youbo) ?: R.id.nav_youbo
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_NAV, binding.bottomNavigation.selectedItemId)
    }

    private fun switchFragment(fragment: Fragment): Boolean {
        if (activeFragment == fragment) return true
        activeFragment?.let {
            supportFragmentManager.beginTransaction().apply {
                hide(it)
                show(fragment)
                commit()
            }
        }
        activeFragment = fragment
        return true
    }

    companion object {
        private const val KEY_SELECTED_NAV = "selected_nav_item"
        private const val TAG_YOUBO = "YouboFragment"
        private const val TAG_HAOKAN = "HaokanFragment"
        private const val TAG_MESSAGE = "MessageFragment"
        private const val TAG_MINE = "MineFragment"
    }
}
