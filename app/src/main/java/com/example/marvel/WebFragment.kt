package com.example.marvel

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.marvel.databinding.FragmentWebBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWebBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            var url="http://www.poli.edu.co/";
        binding.button.setOnClickListener(){
            url = binding.searchView.query.toString();
            val vista=binding.webView;
            vista.getSettings().setJavaScriptEnabled(true);
            vista.loadUrl(url);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}