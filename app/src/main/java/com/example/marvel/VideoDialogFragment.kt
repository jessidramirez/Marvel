package com.example.marvel

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.DialogFragment
import com.example.marvel.databinding.DialogImageBinding

class VideoDialogFragment : DialogFragment() {

    private var _binding: DialogImageBinding? = null
    private val binding get() = _binding!!
    private var videoUri: Uri? = null

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

        videoUri?.let { uri ->
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(binding.fullscreenVideo)

            binding.fullscreenVideo.setMediaController(mediaController)
            binding.fullscreenVideo.setVideoURI(uri)
            binding.fullscreenVideo.start()
        }

        // Cerrar el popup al tocar el video
        binding.fullscreenVideo.setOnClickListener {
            dismiss()
        }
    }

    fun setVideoUri(uri: Uri) {
        videoUri = uri
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.fullscreenVideo.stopPlayback()
        _binding = null
    }
}