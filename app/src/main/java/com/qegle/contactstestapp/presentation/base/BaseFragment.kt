package com.qegle.contactstestapp.presentation.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.qegle.contactstestapp.R
import java.io.IOException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLPeerUnverifiedException

open class BaseFragment : Fragment() {

    protected fun handleError(throwable: Throwable) {
        val resId = createErrorMessage(throwable)
        val view = activity?.findViewById<View>(R.id.coordinator) ?: return
        Snackbar.make(view, getString(resId), Snackbar.LENGTH_LONG).show()
    }

    private fun createErrorMessage(throwable: Throwable): Int {
        if (throwable is IOException) {
            if (throwable is SocketTimeoutException) {
                return R.string.error_network_unreachable
            }

            if (throwable is SSLPeerUnverifiedException) {
                return R.string.error_network_untrusted
            }

            return R.string.error_network_disconnected
        }

        return R.string.error_network_default
    }
}