package com.bba.youbo_bba

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.app.AppCompatActivity

class WelCome : AppCompatActivity() {

    private val prefs by lazy {
        getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstLaunch = prefs.getBoolean("is_first_launch", true)
        if (!isFirstLaunch) {
            goToMain()
            return
        }

        showAgreementDialog()
    }

    private fun showAgreementDialog() {
        val dialog = AppCompatDialog(this)
        dialog.setContentView(R.layout.dialog_user_agreement)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvContent = dialog.findViewById<TextView>(R.id.tvContent)
        val btnDisagree = dialog.findViewById<TextView>(R.id.btnDisagree)
        val btnAgree = dialog.findViewById<TextView>(R.id.btnAgree)

        tvContent?.apply {
            text = buildAgreementSpannable()
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        btnDisagree?.setOnClickListener {
            dialog.dismiss()
            finishAffinity()
        }

        btnAgree?.setOnClickListener {
            prefs.edit().putBoolean("is_first_launch", false).apply()
            dialog.dismiss()
            goToMain()
        }

        dialog.show()
        setDialogWidth(dialog)
    }

    private fun buildAgreementSpannable(): SpannableString {
        val content = (
            "用户服务及隐私协议\n" +
                "我们将通过《有播用户协议》和《隐私保护指引》帮助您了解我们如何收集、使用、存储和共享个人信息的情况，以及您所享有的相关权利。\n\n" +
                "为了向您提供直播服务、精选购物等功能，我们需要保存个人信息；您可以在相关页面访问，更正您的个人信息。\n" +
                "1.为了给您提供交易相关功能，我们会收集、使用必要的信息；\n" +
                "2.基于您的明示授权，我们可能会获取您的相机权限（发布商品或开播）、麦克风权限（发布商品或视频）、存储权限（缓存文件、上传和保存图片）等，您可在权限拒绝或取消授权；\n" +
                "3.在您浏览视频、观看直播时缓存相关文件，我们会申请存储权限，同时为了信息推送和安全风控需要，我们会申请系统权限收集设备信息、日…"
            )

        val ss = SpannableString(content)
        applyLinkSpan(ss, content, "《有播用户协议》")
        applyLinkSpan(ss, content, "《隐私保护指引》")
        return ss
    }

    private fun applyLinkSpan(ss: SpannableString, full: String, target: String) {
        val start = full.indexOf(target)
        if (start < 0) return
        val end = start + target.length

        ss.setSpan(
            ForegroundColorSpan(getColor(R.color.agreement_link)),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        ss.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(this@WelCome, target, Toast.LENGTH_SHORT).show()
                }

                override fun updateDrawState(ds: android.text.TextPaint) {
                    ds.isUnderlineText = false
                }
            },
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun setDialogWidth(dialog: AppCompatDialog) {
        val window = dialog.window ?: return
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.88f).toInt()
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

