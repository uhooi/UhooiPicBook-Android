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
                val url = request?.url.toString() ?: return false
                return when {
                    url.startsWith("http:") || url.startsWith("https:") -> false
                    url.startsWith("intent://") -> {
                        try {
                            val context = webView?.context ?: return false
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                ?: return false

                            val packageManager: PackageManager = context.packageManager
                            val info = packageManager.resolveActivity(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )
                            when {
                                /* This statement can be omitted if you are not strict about
                                 * opening the Google form url in WebView & can be opened in an
                                 * External Browser
                                 */
                                intent.scheme == "https" || intent.scheme == "http" -> {
                                    val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                                    webView?.loadUrl(fallbackUrl)
                                    return true
                                }
                                info != null -> context.startActivity(intent)
                                else -> {
                                    // Call external browser
                                    val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(fallbackUrl)
                                    )
                                    context.startActivity(browserIntent)
                                }
                            }
                            true
                        } catch (e: Exception) {
                            false
                        }
                    }
                    else -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        webView?.context?.startActivity(intent)
                        true
                    }
                }
            }
        }
        this.webView?.loadUrl(this.args.urlString)
    }

    // endregion

}
