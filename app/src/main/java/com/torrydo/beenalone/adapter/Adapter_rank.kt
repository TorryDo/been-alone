package com.torrydo.beenalone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.ItemClassBinding
import com.torrydo.beenalone.interfaces.GodClick
import com.torrydo.beenalone.models.MyRank

class Adapter_rank(private val rankList: ArrayList<MyRank>, private val currentLvl : Int, private val godClick: GodClick) :
    RecyclerView.Adapter<Adapter_rank.RankHolder>() {

    class RankHolder(val binding: ItemClassBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankHolder {

        val tempBind = ItemClassBinding.inflate(LayoutInflater.from(parent.context))

        tempBind.itemRankLayout.setOnClickListener {
            godClick.onClick(it)
        }

        return RankHolder(tempBind)
    }

    override fun onBindViewHolder(holder: RankHolder, position: Int) {

        holder.binding.itemRankNumber.text = rankList[position].reached.toString()

        if(currentLvl > rankList[position].reached ){
            holder.binding.itemRankIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    rankList[position].icon
                )
            )

            holder.binding.itemRankTitle.text = rankList[position].title
            holder.binding.itemRankContent.text = rankList[position].content

        }else{
            holder.binding.itemRankIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.ic_lock
                )
            )

            holder.binding.itemRankTitle.text = holder.binding.root.context.getString(R.string.warning_not_reached_lvl_title)
            holder.binding.itemRankContent.text = holder.binding.root.context.getString(R.string.warning_not_reached_lvl_content)

        }



    }

    override fun getItemCount(): Int {
        return rankList.size
    }
}