package com.example.marvel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.marvel.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPerfil.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_PerfilFragment)
        }
        binding.btnFotos.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FotosFragment)
        }
        binding.btnVideos.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_VideosFragment)
        }
        binding.btnWeb.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_WebFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}