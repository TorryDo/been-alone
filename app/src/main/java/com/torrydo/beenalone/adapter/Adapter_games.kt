package com.torrydo.beenalone.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.ItemGameBinding
import com.torrydo.beenalone.interfaces.GodClick
import com.torrydo.beenalone.interfaces.GodTouch
import com.torrydo.beenalone.models.Mgame

class Adapter_games(private val gameList: ArrayList<Mgame>, private val godTouch: GodTouch,private val godClick: GodClick) :
    RecyclerView.Adapter<Adapter_games.GameHolder>() {

    class GameHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {

        val tempBind = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        tempBind.root.setOnTouchListener { view, motionEvent ->
            godTouch.onTouch(view)
            return@setOnTouchListener false
        }

        tempBind.root.setOnClickListener {
            godClick.onClick(it)
        }

        return GameHolder(tempBind)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {

        val pic = gameList[position].hasPicture
        if (pic) {
            Log.i("_info", "làm sau")
        } else {
            holder.binding.itemGameCardview.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    gameList[position].backgroundColor
                )
            )
        }
        holder.binding.itemGameName.text = gameList[position].name
        holder.binding.itemGameName.setTextColor(
            ContextCompat.getColor(
                holder.binding.root.context,
                gameList[position].textColor
            )
        )


    }

    override fun getItemCount(): Int {
        return gameList.size
    }
}