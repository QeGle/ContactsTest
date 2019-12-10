package com.qegle.contactstestapp.presentation.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("isGone")
fun setIsGone(view: View, value: Boolean) {
    view.visibility = if (value) View.GONE else View.VISIBLE
}

@BindingAdapter("isHidden")
fun setIsHidden(view: View, value: Boolean) {
    view.visibility = if (value) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("isVisible")
fun setIsVisible(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("isEnabled")
fun setIsEnabled(view: View, value: Boolean) {
    view.isEnabled = value
    view.alpha = if (value) 1.0f else 0.6f
}

@BindingAdapter("isEnabled")
fun setIsEnabled(view: SwipeRefreshLayout, value: Boolean) {
    view.isEnabled = value
}

@BindingAdapter("isRefreshing")
fun setIsRefreshing(view: SwipeRefreshLayout, value: Boolean) {
    view.isRefreshing = value
}