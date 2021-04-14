package com.theuhooi.uhooipicbook.modules.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        this.webView?.settings?.javaScriptEnabled = true
        this.webView?.webViewClient = WebViewClient()
        this.webView?.loadUrl(this.args.urlString)
    }

    // endregion

}
