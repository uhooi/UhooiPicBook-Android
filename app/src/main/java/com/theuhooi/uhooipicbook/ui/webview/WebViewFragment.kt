package com.theuhooi.uhooipicbook.ui.webview

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.theuhooi.uhooipicbook.R
import com.theuhooi.uhooipicbook.databinding.FragmentWebViewBinding
import timber.log.Timber

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    // region Stored Instance Properties

    @Suppress("UnusedPrivateMember")
    private val viewModel: WebViewViewModel by viewModels() // TODO: Use

    // endregion

    // region View Life-Cycle Methods

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWebViewBinding.bind(view)
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val urlString = request?.url.toString()

                return when {
                    urlString.startsWith("http://") || urlString.startsWith("https://") -> false
                    urlString.startsWith("intent://") -> {
                        try {
                            val intent = Intent.parseUri(urlString, Intent.URI_INTENT_SCHEME)
                                ?: return false

                            if (intent.scheme == "http" || intent.scheme == "https") {
                                val fallbackUrlString = intent.getStringExtra(
                                    BROWSER_FALLBACK_URL_EXTRA_NAME
                                ) ?: return false
                                binding.webview.loadUrl(fallbackUrlString)
                                return true
                            }

                            val context = binding.webview.context
                            val info = context?.packageManager?.resolveActivity(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )
                            if (info != null) {
                                context.startActivity(intent)
                            } else {
                                val fallbackUrlString = intent.getStringExtra(
                                    BROWSER_FALLBACK_URL_EXTRA_NAME
                                )
                                val browserIntent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrlString))
                                context?.startActivity(browserIntent)
                            }
                            true
                        } catch (e: Exception) {
                            Timber.e(e)
                            false
                        }
                    }
                    else -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
                        binding.webview.context?.startActivity(intent)
                        true
                    }
                }
            }
        }

        val args: WebViewFragmentArgs by navArgs()
        binding.webview.loadUrl(args.urlString)
    }

    // endregion

    // region Companion Object

    companion object {
        private const val BROWSER_FALLBACK_URL_EXTRA_NAME = "browser_fallback_url"
    }

    // endregion

}
