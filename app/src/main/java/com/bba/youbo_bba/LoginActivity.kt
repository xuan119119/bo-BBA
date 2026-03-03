package com.bba.youbo_bba

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bba.youbo_bba.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val prefs by lazy {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    private var codeTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSkip.setOnClickListener {
            finish()
        }

        binding.tvGetCode.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            if (phone.length != 11) {
                Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startCodeCountdown()
            Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener {
            val phone = binding.etPhone.text.toString().trim()
            val code = binding.etCode.text.toString().trim()
            if (!binding.cbAgree.isChecked) {
                Toast.makeText(this, "请勾选并同意相关协议", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phone.length != 11) {
                Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (code.isEmpty()) {
                Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.layoutWechat.setOnClickListener {
            Toast.makeText(this, "暂未接入微信登录", Toast.LENGTH_SHORT).show()
        }

        binding.tvAgreements.apply {
            text = buildAgreementSpannable()
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = 0
        }
    }

    private fun startCodeCountdown() {
        binding.tvGetCode.isEnabled = false
        codeTimer?.cancel()
        codeTimer = object : CountDownTimer(60_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvGetCode.text = "${millisUntilFinished / 1000}s"
            }
            override fun onFinish() {
                binding.tvGetCode.isEnabled = true
                binding.tvGetCode.text = "获取验证码"
            }
        }.start()
    }

    private fun buildAgreementSpannable(): SpannableString {
        val text = "我已阅读并接受《有播用户服务协议》和《有播隐私协议》"
        val spannable = SpannableString(text)
        val link1 = "《有播用户服务协议》"
        val link2 = "《有播隐私协议》"
        val start1 = text.indexOf(link1)
        val start2 = text.indexOf(link2)
        if (start1 >= 0) {
            spannable.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(this@LoginActivity, "打开用户服务协议", Toast.LENGTH_SHORT).show()
                }
            }, start1, start1 + link1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(
                ForegroundColorSpan(0xFF2AA7D9.toInt()),
                start1,
                start1 + link1.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        if (start2 >= 0) {
            spannable.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(this@LoginActivity, "打开隐私协议", Toast.LENGTH_SHORT).show()
                }
            }, start2, start2 + link2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(
                ForegroundColorSpan(0xFF2AA7D9.toInt()),
                start2,
                start2 + link2.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }

    override fun onDestroy() {
        codeTimer?.cancel()
        super.onDestroy()
    }

    companion object {
        const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
}

