package com.example.marvel

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentSigninBinding
import java.util.Calendar

class SignInFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.age.setOnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                fecha()
            }
        }
        binding.imgPerfil.setOnClickListener{
            val pickImg = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            changeImage.launch(pickImg)
        }
        binding.btnCancelar.setOnClickListener {
            findNavController().navigate(R.id.action_SigninFragment_to_LoginFragment)
        }
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.name.text.toString()
            val apellido = binding.lastname.text.toString()
            val correo = binding.mail.text.toString()
            val fecha = binding.age.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val foto = binding.imgPerfil.toString()
            //Crear usuario
            val usuario = Usuario.crearUsuario(nombre, apellido, correo, fecha, password, foto)

            println(usuario)

            //Falta guardar los datos en la base de datos
        }

    }
    private fun fecha(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, y, m, d ->
            binding.age.setText("$d/${m + 1}/$y")
        }, year, month, day).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private val changeImage = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            val imgUri = data?.data
            binding.imgPerfil.setImageURI(imgUri)
        }
    }
}