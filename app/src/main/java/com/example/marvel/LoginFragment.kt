package com.example.marvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentLoginBinding
import com.example.marvel.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar

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
        val mySnackbar = Snackbar.make(view, "Error al iniciar sesión", Snackbar.LENGTH_SHORT)

        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            println("Email: $email, Password: $password")
            if (email == "admin" && password == "admin") {
                findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
            }else{mySnackbar.show()}
        }
        binding.btnSignin.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_SigninFragment)
        }
        binding.btnGoogle.setOnClickListener {
            mySnackbar.show()
        }
        binding.btnGoogle.setOnClickListener {
            mySnackbar.show()
        }
        binding.btnFacebook.setOnClickListener {
            mySnackbar.show()
        }
        binding.btnTwitter.setOnClickListener {
            mySnackbar.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}