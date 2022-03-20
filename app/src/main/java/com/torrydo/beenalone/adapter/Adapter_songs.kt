package com.torrydo.beenalone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.ItemSongBinding
import com.torrydo.beenalone.interfaces.GodTouch

class Adapter_songs(private val songsList: ArrayList<Int>, private val godClick: GodTouch) :
    RecyclerView.Adapter<Adapter_songs.SongHolder>() {

    class SongHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {

        val tempBind = ItemSongBinding.inflate(LayoutInflater.from(parent.context))

        tempBind.itemSongLayout.setOnClickListener {
            godClick.onTouch(it)
        }

        return SongHolder(tempBind)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {

        val songname: String =
            holder.binding.root.context.resources.getResourceEntryName(songsList[position])
                .replace("bensound_", "") ?: "null vcl"
        holder.binding.itemSongName.text = songname
        holder.binding.itemSongSource.text =
            holder.binding.root.context.resources.getString(R.string.song_source)
    }

    override fun getItemCount(): Int {
        return songsList.size
    }
}