package com.example.marvel

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R

class VideoAdapter(
    private val videoUris: List<Uri>,
    private val onItemClick: (Uri) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailView: ImageView = itemView.findViewById(R.id.videoThumbnailView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val uri = videoUris[position]

        // Glide puede cargar miniaturas de videos también
        Glide.with(holder.itemView.context)
            .load(uri)
            .thumbnail(0.1f)
            .into(holder.thumbnailView)

        holder.thumbnailView.setOnClickListener {
            onItemClick(uri)
        }
    }

    override fun getItemCount(): Int = videoUris.size
}