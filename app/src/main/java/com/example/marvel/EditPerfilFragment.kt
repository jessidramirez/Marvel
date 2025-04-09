package com.example.marvel

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.age.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, y, m, d ->
                binding.age.setText("$d/${m + 1}/$y")
            }, year, month, day).show()
        }
        binding.btnCancelar.setOnClickListener {
            findNavController().navigate(R.id.action_EditPerfilFragment_to_PerfilFragment)
        }
        binding.btnGuardar.setOnClickListener {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}