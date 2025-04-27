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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentEditperfilBinding
import com.example.marvel.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Base64
import java.util.Calendar

class EditPerfilFragment : Fragment() {

    private var _binding: FragmentEditperfilBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth= FirebaseAuth.getInstance()
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
        val database = FirebaseDatabase.getInstance()
        val referenciaUsuarios = database.getReference("Usuarios") // Cambia "Usuarios" por el nodo correspondiente
        println("/Usuarios/"+ FirebaseAuth.getInstance().currentUser?.uid.toString())
        referenciaUsuarios.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (usuarioSnapshot in snapshot.children) {
                        if (usuarioSnapshot.key == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
                            val nombre = usuarioSnapshot.child("nombre").getValue(String::class.java)
                            val apellido = usuarioSnapshot.child("apellido").getValue(String::class.java)
                            val correo = usuarioSnapshot.child("correo").getValue(String::class.java)
                            val fecha = usuarioSnapshot.child("fecha").getValue(String::class.java)
                            val foto = usuarioSnapshot.child("foto").getValue(String::class.java)
                            binding.name.setText(nombre)
                            binding.lastname.setText(apellido)
                            binding.mail.setText(correo)
                            binding.age.setText(fecha)
                            if (foto.equals("defaultImage")) {
                                binding.imgPerfil.setImageResource(com.example.marvel.R.drawable.perfil)
                            } else {
                                binding.imgPerfil.setImageResource(com.example.marvel.R.drawable.perfil)
                            }


                        }
                    }
                } else {
                    println("No se encontraron datos")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al leer los datos: ${error.message}")
            }
        })

        binding.btnCancelar.setOnClickListener {
            findNavController().navigate(R.id.action_EditPerfilFragment_to_PerfilFragment)
        }
        binding.btnGuardar.setOnClickListener {
            var nombre = binding.name.text.toString()
            var apellido = binding.lastname.text.toString()
            var correo = binding.mail.text.toString()
            var fecha = binding.age.text.toString()

            actualizarUsuario(nombre, apellido, correo, fecha, "defaultImage")

            Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_EditPerfilFragment_to_PerfilFragment)
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
    private fun actualizarUsuario(nombre: String, apellido: String, correo: String, fecha: String, foto: String){
        database = FirebaseDatabase.getInstance().getReference("Usuarios").child(FirebaseAuth.getInstance().currentUser?.uid.toString())
        val user = hashMapOf<String,Any>(
            "nombre" to nombre,
            "apellido" to apellido,
            "correo" to correo,
            "fecha" to fecha,
            "foto" to foto
        )
        database.updateChildren(user)
    }
}