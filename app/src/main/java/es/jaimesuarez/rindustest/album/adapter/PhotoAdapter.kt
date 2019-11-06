package es.jaimesuarez.rindustest.album.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.album.model.PhotoDisplay
import es.jaimesuarez.rindustest.common.util.inflate

class PhotoAdapter(
    private val photos: MutableList<PhotoDisplay> = mutableListOf(),
    private val onPhotoClick: (PhotoDisplay) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(parent.inflate(R.layout.item_photo))

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.onBind(photos[position])
    }

    fun setPhotos(newPhotos: List<PhotoDisplay>) {
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(photo: PhotoDisplay) = view.apply {
            setOnClickListener { onPhotoClick(photo) }
            findViewById<TextView>(R.id.tv_photo_title).text = photo.title

            Glide.with(view)
                .load(photo.thumbnail)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(findViewById(R.id.iv_photo))
        }
    }
}
