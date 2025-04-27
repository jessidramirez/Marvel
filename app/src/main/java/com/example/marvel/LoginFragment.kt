package com.example.marvel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val mySnackbar = Snackbar.make(view, "Error al iniciar sesión", Snackbar.LENGTH_SHORT)

        binding.btnLogin.setOnClickListener {
            val email = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            //VALIDAR CREDENCIALES
            if(email.isEmpty()){
                binding.etUsername.error = "El correo es obligatorio"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }else if(password.isEmpty()){
                binding.etPassword.error = "La contraseña es obligatoria"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_LoginFragment_to_SecondFragment)
                        }else{
                            Toast.makeText( context, "Error al Iniciar", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
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