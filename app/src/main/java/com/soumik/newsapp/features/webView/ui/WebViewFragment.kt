package com.soumik.newsapp.features.webView.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.soumik.newsapp.core.SharedViewModel
import com.soumik.newsapp.core.utils.Event
import com.soumik.newsapp.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {

    private lateinit var mBinding: FragmentWebViewBinding
    private val args: WebViewFragmentArgs by navArgs()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWebViewBinding.inflate(inflater)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViews()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setViews() {
        sharedViewModel.showBottomNav.value= Event(false)
        mBinding.apply {
            webView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
            }

            with(webView) {
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                clearHistory()
                isHorizontalScrollBarEnabled = false
                isVerticalScrollBarEnabled = false
                webViewClient = Client()
            }

            webView.loadUrl(args.url ?: "")
        }
    }

    inner class Client : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url = request?.url
            view?.loadUrl(url.toString())
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            mBinding.progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            mBinding.progressBar.visibility = View.GONE
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            mBinding.progressBar.visibility = View.GONE
        }
    }
}