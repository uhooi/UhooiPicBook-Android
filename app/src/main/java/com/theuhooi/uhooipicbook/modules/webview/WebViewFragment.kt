package com.theuhooi.uhooipicbook.modules.webview

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.theuhooi.uhooipicbook.R

class WebViewFragment : Fragment() {

    // region Stored Instance Properties

    private val args: WebViewFragmentArgs by navArgs()

    private var webView: WebView? = null

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_web_view, container, false)
        this.webView = view.findViewById(R.id.webview)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val urlString = request?.url.toString()
                if (urlString == null) {
                    return false
                }

                return when {
                    urlString.startsWith("http://") || urlString.startsWith("https://") -> false
                    urlString.startsWith("intent://") -> {
                        try {
                            val context = webView?.context
                            val intent = Intent.parseUri(urlString, Intent.URI_INTENT_SCHEME)
                            if (context == null || intent == null) {
                                return false
                            }

                            if (intent.scheme == "http" || intent.scheme == "https") {
                                val fallbackUrl = intent.getStringExtra(
                                    BROWSER_FALLBACK_URL_EXTRA_NAME
                                )
                                webView?.loadUrl(fallbackUrl)
                                return true
                            }

                            val info = context.packageManager.resolveActivity(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )
                            if (info != null) {
                                context.startActivity(intent)
                            } else {
                                val fallbackUrl = intent.getStringExtra(
                                    BROWSER_FALLBACK_URL_EXTRA_NAME
                                )
                                val browserIntent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl))
                                context.startActivity(browserIntent)
                            }
                            true
                        } catch (e: Exception) {
                            false
                        }
                    }
                    else -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
                        webView?.context?.startActivity(intent)
                        true
                    }
                }
            }
        }

        this.webView?.settings?.javaScriptEnabled = true
        this.webView?.loadUrl(this.args.urlString)
    }

    // endregion

    // region Companion Object

    companion object {
        const val BROWSER_FALLBACK_URL_EXTRA_NAME = "browser_fallback_url"
    }

    // endregion

}
