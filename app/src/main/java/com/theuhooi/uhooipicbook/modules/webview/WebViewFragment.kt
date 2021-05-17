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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.theuhooi.uhooipicbook.databinding.FragmentWebViewBinding
import com.theuhooi.uhooipicbook.modules.webview.viewmodels.WebViewViewModel

class WebViewFragment : Fragment() {

    // region Stored Instance Properties

    private val args: WebViewFragmentArgs by navArgs()

    @Suppress("UnusedPrivateMember")
    private val viewModel: WebViewViewModel by viewModels() // TODO: Use

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    // endregion

    // region View Life-Cycle Methods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl(args.urlString)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    // endregion

    // region Companion Object

    companion object {
        private const val BROWSER_FALLBACK_URL_EXTRA_NAME = "browser_fallback_url"
    }

    // endregion

}
