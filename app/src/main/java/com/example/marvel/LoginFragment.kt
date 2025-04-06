package com.example.marvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentLoginBinding
import com.example.marvel.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
        }
        binding.btnGoogle.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
        }
        binding.btnFacebook.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
        }
        binding.btnTwitter.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}