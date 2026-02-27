package com.bba.youbo_bba

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
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

    private val prefs by lazy {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

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
        binding.bottomNavigation.selectedItemId =
            savedInstanceState?.getInt(KEY_SELECTED_NAV, R.id.nav_youbo) ?: R.id.nav_youbo

        maybeShowTeenModeDialog()
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

    private fun maybeShowTeenModeDialog() {
        val hasKnown = prefs.getBoolean(KEY_TEEN_MODE_KNOWN, false)
        if (hasKnown) {
            // 青少年模式已经知道了，直接尝试展示粉丝礼包弹窗
            maybeShowFansRewardDialog()
            return
        }

        val dialog = AppCompatDialog(this)
        dialog.setContentView(R.layout.dialog_teen_mode)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnIKnow = dialog.findViewById<TextView>(R.id.btnIKnow)
        val btnSetTeenMode = dialog.findViewById<TextView>(R.id.btnSetTeenMode)
        val cbNoMoreTips = dialog.findViewById<CheckBox>(R.id.cbNoMoreTips)
        val layoutNoMoreTips = dialog.findViewById<LinearLayout>(R.id.layoutNoMoreTips)

        // 设置点击整个区域切换复选框状态
        layoutNoMoreTips?.setOnClickListener {
            cbNoMoreTips?.isChecked = !(cbNoMoreTips?.isChecked ?: false)
        }

        val confirmAndDismiss = {
            // 根据复选框状态决定是否永久隐藏
            val shouldNotShowAgain = cbNoMoreTips?.isChecked ?: false
            prefs.edit()
                .putBoolean(KEY_TEEN_MODE_KNOWN, shouldNotShowAgain)
                .putBoolean(KEY_TEEN_MODE_NO_MORE_TIPS, shouldNotShowAgain)
                .apply()
            dialog.dismiss()
            // 青少年弹窗关闭后，再尝试展示粉丝礼包弹窗
            maybeShowFansRewardDialog()
        }

        // 检查是否曾经选择过"不再提示"
        val hasChosenNoMore = prefs.getBoolean(KEY_TEEN_MODE_NO_MORE_TIPS, false)
        if (hasChosenNoMore) {
            // 如果之前选择了不再提示，则自动勾选并设置已知状态
            cbNoMoreTips?.isChecked = true
            confirmAndDismiss()
            return
        }

        btnIKnow?.setOnClickListener {
            confirmAndDismiss()
        }

        btnSetTeenMode?.setOnClickListener {
            // TODO: 如果有青少年模式设置页，在这里跳转
            confirmAndDismiss()
        }

        dialog.show()
        setDialogWidth(dialog)
    }

    private var countdownTimer: CountDownTimer? = null

    private fun maybeShowFansRewardDialog() {
        val hasKnown = prefs.getBoolean(KEY_FANS_REWARD_KNOWN, false)
        if (hasKnown) return

        val dialog = AppCompatDialog(this)
        dialog.setContentView(R.layout.dialog_fans_reward)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnClose = dialog.findViewById<TextView>(R.id.btnCloseFans)
        val btnOpenVip = dialog.findViewById<TextView>(R.id.btnOpenVip)
        val btnLater = dialog.findViewById<TextView>(R.id.btnLater)
        val tvCountdown = dialog.findViewById<TextView>(R.id.tvCountdown)

        // 启动倒计时
        startCountdown(tvCountdown)

        val confirmAndDismiss = {
            countdownTimer?.cancel()
            prefs.edit().putBoolean(KEY_FANS_REWARD_KNOWN, true).apply()
            dialog.dismiss()
        }

        btnClose?.setOnClickListener {
            confirmAndDismiss()
        }

        btnOpenVip?.setOnClickListener {
            // TODO: 跳转到VIP开通页面
            confirmAndDismiss()
        }

        btnLater?.setOnClickListener {
            confirmAndDismiss()
        }

        dialog.setOnDismissListener {
            countdownTimer?.cancel()
        }

        dialog.show()
        setDialogWidth(dialog)
    }

    private fun startCountdown(textView: TextView?) {
        countdownTimer?.cancel()
        
        // 设置24小时倒计时（实际项目中应该从服务器获取剩余时间）
        val totalTime = 24 * 60 * 60 * 1000L // 24小时
        
        countdownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = (millisUntilFinished / (1000 * 60 * 60)).toInt()
                val minutes = ((millisUntilFinished / (1000 * 60)) % 60).toInt()
                val seconds = ((millisUntilFinished / 1000) % 60).toInt()
                
                val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                textView?.text = timeString
            }
            
            override fun onFinish() {
                textView?.text = "00:00:00"
            }
        }.start()
    }

    private fun setDialogWidth(dialog: AppCompatDialog) {
        val window = dialog.window ?: return
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.82f).toInt()
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        private const val KEY_SELECTED_NAV = "selected_nav_item"
        private const val TAG_YOUBO = "YouboFragment"
        private const val TAG_HAOKAN = "HaokanFragment"
        private const val TAG_MESSAGE = "MessageFragment"
        private const val TAG_MINE = "MineFragment"
        private const val KEY_TEEN_MODE_KNOWN = "teen_mode_known"
        private const val KEY_TEEN_MODE_NO_MORE_TIPS = "teen_mode_no_more_tips"
        private const val KEY_FANS_REWARD_KNOWN = "fans_reward_known"
    }
}

