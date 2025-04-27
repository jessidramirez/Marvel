package com.example.marvel
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentSigninBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Base64
import java.util.Calendar
import com.google.firebase.auth.FirebaseAuth as FirebaseAuth1

class SignInFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var pickImg: Intent

    private lateinit var auth: FirebaseAuth1
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth1.getInstance()
        binding.age.setOnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                fecha()
            }
        }
        binding.imgPerfil.setOnClickListener{
            pickImg = Intent(
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
            val foto = imageToBase64(pickImg.toString())

            //Crear usuario
            if(nombre.isEmpty()){
                Toast.makeText(requireContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(apellido.isEmpty()){
                Toast.makeText(requireContext(), "El apellido es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(correo.isEmpty()){
                Toast.makeText(requireContext(), "El correo es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(fecha.isEmpty()){
                Toast.makeText(requireContext(), "La fecha de nacimiento es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(password.isEmpty()){
                Toast.makeText(requireContext(), "La contraseña es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{

                SignInUsuario(nombre, apellido, correo, fecha, password, foto)
            }
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
    private fun SignInUsuario(nombre: String, apellido: String, correo: String, fecha: String, password: String, foto: String){
        auth.createUserWithEmailAndPassword(correo,password)
            .addOnCompleteListener{task->
            if(task.isSuccessful){
                var uid:String = ""
                uid=auth.currentUser!!.uid
                reference= FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)
                val hashMap=HashMap<String,Any>()

                hashMap["uid"]=uid
                hashMap["nombre"]=nombre
                hashMap["apellido"]=apellido
                hashMap["correo"]=correo
                hashMap["fecha"]=fecha
                hashMap["foto"]=foto
                hashMap["buscar"]=correo.lowercase()

                reference.updateChildren(hashMap).addOnCompleteListener{task2  ->
                    if(task2.isSuccessful){
                        val intent = Intent(this@SignInFragment.context, MainActivity::class.java)
                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_SigninFragment_to_LoginFragment)
                        startActivity(intent)
                    }
                }.addOnFailureListener{e ->
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
                }else{
                    Toast.makeText(requireContext(), "Error en el registro", Toast.LENGTH_SHORT).show()
                }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun imageToBase64(imagePath: String): String {
        return try {
            val file = File(imagePath)
            val fileInputStream = FileInputStream(file)
            val bytes = fileInputStream.readBytes()
            fileInputStream.close()
            return Base64.getEncoder().encodeToString(bytes)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return "defaultImage" // Devuelve nulo o un mensaje de error manejable
        }
    }
}