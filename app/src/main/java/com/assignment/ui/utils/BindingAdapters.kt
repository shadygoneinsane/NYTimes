package com.assignment.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

@BindingAdapter("glideUrl")
fun ImageView.loadImage(imageUri: String?) {
    imageUri?.let {
        Glide.with(context).load(imageUri).into(this)
    }
}

@BindingAdapter("glideSrc", "glideCenterCrop", "glideCircularCrop", requireAll = false)
fun ImageView.bindGlideSrc(
    @DrawableRes drawableRes: Int?,
    centerCrop: Boolean = false,
    circularCrop: Boolean = false
) {
    if (drawableRes == null) return

    createGlideRequest(context, drawableRes, centerCrop, circularCrop).into(this)
}

private fun createGlideRequest(
    context: Context,
    @DrawableRes src: Int,
    centerCrop: Boolean,
    circularCrop: Boolean
)
        : RequestBuilder<Drawable> {
    val req = Glide.with(context).load(src)
    if (centerCrop) req.centerCrop()
    if (circularCrop) req.circleCrop()
    return req
}

@BindingAdapter("isVisible")
fun View.showHide(isVisible: Boolean?) {
    visibility = if (isVisible != null && isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("setWebViewClient")
fun WebView.setWebViewClient(client: WebViewClient) {
    webViewClient = client
}

@BindingAdapter("loadUrl")
fun WebView.loadUrl(url: String?) {
    url?.let { loadUrl(it) }
}