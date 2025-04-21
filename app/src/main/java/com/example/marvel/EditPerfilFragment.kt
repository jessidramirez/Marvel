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
import com.example.marvel.databinding.FragmentEditperfilBinding
import com.example.marvel.databinding.FragmentPerfilBinding
import java.util.Calendar

class EditPerfilFragment : Fragment() {

    private var _binding: FragmentEditperfilBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditperfilBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.age.setOnFocusChangeListener() { _, hasFocus ->
            if (hasFocus) {
                fecha()
            }
        }
        binding.imgPerfil.setOnClickListener() {
            val pickImg = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            changeImage.launch(pickImg)
        }

        binding.btnCancelar.setOnClickListener {
            findNavController().navigate(R.id.action_EditPerfilFragment_to_PerfilFragment)
        }
        binding.btnGuardar.setOnClickListener {
            var nombre = binding.name.text.toString()
            var apellido = binding.lastname.text.toString()
            var correo = binding.mail.text.toString()
            var fecha = binding.age.text.toString()

            println("Nombre: $nombre, Apellido: $apellido, Correo: $correo, Fecha: $fecha")

            //Falta guardar los datos en la base de datos
        }

    }
    fun fecha(): Unit {
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