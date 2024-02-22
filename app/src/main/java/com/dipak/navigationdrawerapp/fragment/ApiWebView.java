package com.dipak.navigationdrawerapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dipak.navigationdrawerapp.R;

public class ApiWebView extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private WebView webView;
    private ProgressBar loadingIndicator;

    public ApiWebView() {
        // Required empty public constructor
    }

    public void setApiData(String api) {
        Bundle args = new Bundle();
        args.putString("key", api);
        setArguments(args);
    }

    public static ApiWebView newInstance(String param1, String param2) {
        ApiWebView fragment = new ApiWebView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_api_web_view, container, false);
        String url="";
        Bundle args = getArguments();
        if (args != null) {
            url = args.getString("key", "");

        }

        webView = view.findViewById(R.id.webView);
        loadingIndicator = view.findViewById(R.id.loadingIndicator);

        setupWebView(url);


        return view;
    }

    private void setupWebView(String url) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set a WebViewClient to handle page navigation
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                // Show loading indicator when the page starts loading
                loadingIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Hide loading indicator when the page finishes loading
                loadingIndicator.setVisibility(View.GONE);
            }
        });

        // Load a URL (replace "https://www.example.com" with your URL)
        webView.loadUrl(url);

    }
}