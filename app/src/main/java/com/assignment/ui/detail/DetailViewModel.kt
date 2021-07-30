package com.assignment.ui.detail

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.assignment.arc.SingleLiveEvent
import com.assignment.ui.base.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor() : BaseViewModel() {
    private val url = SingleLiveEvent<String?>()
    fun getUrl(): SingleLiveEvent<String?> = url
    fun setUrl(url: String) {
        this.url.value = url
    }

    inner class Client : WebViewClient() {
        override fun onReceivedError(
            view: WebView, request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            showLoading(true)
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            showLoading(false)
        }
    }

    fun getWebViewClient(): WebViewClient {
        return Client()
    }
}