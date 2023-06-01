package com.example.evoucher.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentWebViewBinding

class WebViewFragment : BaseFragment<FragmentWebViewBinding>(FragmentWebViewBinding::inflate) {

    private val args: WebViewFragmentArgs by navArgs()

    override fun initialize() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(args.urlArg)
        binding.pbLoading.visibility = VISIBLE
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                // Callback khi bắt đầu tải trang
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                // Callback khi tải trang hoàn thành
                binding.pbLoading.visibility = GONE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                // Callback khi có lỗi xảy ra trong quá trình tải trang
                binding.pbLoading.visibility = GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                // Callback khi có yêu cầu tải trang mới
                // Trả về true nếu bạn muốn xử lý yêu cầu tải trang theo cách tùy chỉnh
                // Trả về false để cho WebView xử lý yêu cầu tải trang mặc định
                return false
            }
        }

    }

    override fun initObserve() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = WebViewFragment()
    }
}