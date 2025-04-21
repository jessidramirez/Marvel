package com.example.marvel

import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.marvel.databinding.DialogImageBinding

class ImageDialogFragment : DialogFragment() {

    private var _binding: DialogImageBinding? = null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Para que el fondo sea transparente
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageUri?.let {
            Glide.with(requireContext())
                .load(it)
                .into(binding.fullscreenImage)
        }

        // Cerrar el popup al tocar la imagen
        binding.fullscreenImage.setOnClickListener {
            dismiss()
        }
    }

    fun setImageUri(uri: Uri) {
        imageUri = uri
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
