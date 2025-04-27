package com.example.marvel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        val referenciaUsuarios = database.getReference("Usuarios") // Cambia "Usuarios" por el nodo correspondiente
        println("/Usuarios/"+FirebaseAuth.getInstance().currentUser?.uid.toString())
        referenciaUsuarios.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (usuarioSnapshot in snapshot.children) {
                        if (usuarioSnapshot.key == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
                            val nombre = usuarioSnapshot.child("nombre").getValue(String::class.java)
                            val apellido = usuarioSnapshot.child("apellido").getValue(String::class.java)
                            val correo = usuarioSnapshot.child("correo").getValue(String::class.java)
                            val fecha = usuarioSnapshot.child("fecha").getValue(String::class.java)
                            val foto = usuarioSnapshot.child("foto").getValue(String::class.java)
                            binding.name.text = "Nombre: $nombre"
                            binding.lastname.text = "Apellido: $apellido"
                            binding.mail.text = "Correo: $correo"
                            binding.age.text = "fecha: $fecha"
                            if (foto.equals("defaultImage")) {
                                binding.imgPerfil.setImageResource(com.example.marvel.R.drawable.perfil)
                            } else {
                                binding.imgPerfil.setImageResource(com.example.marvel.R.drawable.perfil)
                            }
                            break
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


        binding.btnCerrarSesion.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_PerfilFragment_to_FirstFragment)
        }
        binding.btnEditar.setOnClickListener {
            findNavController().navigate(R.id.action_PerfilFragment_to_EditPerfilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}