package es.jaimesuarez.rindustest.album.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.album.model.AlbumDisplay
import es.jaimesuarez.rindustest.common.util.inflate

class AlbumAdapter(
    private var albums: MutableList<AlbumDisplay> = mutableListOf(),
    private val onAlbumClick: (AlbumDisplay) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(parent.inflate(R.layout.item_album))
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.onBind(albums[position])
    }

    fun submitList(newAlbums: List<AlbumDisplay>) {
        albums.addAll(newAlbums)
        notifyDataSetChanged()
    }

    inner class AlbumViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun onBind(album: AlbumDisplay) = view.apply {
            setOnClickListener { onAlbumClick(album) }
            findViewById<TextView>(R.id.tv_album_title).text = album.title
        }
    }
}
