package com.torrydo.beenalone.adapter

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.torrydo.beenalone.databinding.ItemAdBinding
import com.torrydo.beenalone.databinding.ItemOptionBinding
import com.torrydo.beenalone.interfaces.GodClick
import com.torrydo.beenalone.interfaces.GodSwitch
import com.torrydo.beenalone.models.mOption
import com.torrydo.beenalone.models.mUser
import com.torrydo.beenalone.utility.Utils

class Adapter_options(
    private val mArrs: ArrayList<mOption>,
    private val godClick: GodClick,
    private val deviceWidth: Int,
    private val godSwitch: GodSwitch,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class OptionHolder(val optionBinding: ItemOptionBinding) :
        RecyclerView.ViewHolder(optionBinding.root)

    class AdHolder(val adBinding: ItemAdBinding) : RecyclerView.ViewHolder(adBinding.root)

    private val AD_TYPE = 1
    private val OPTION_TYPE = 0

    override fun getItemViewType(position: Int): Int {

        return if (mArrs[position].isAd) {
            AD_TYPE
        } else {
            OPTION_TYPE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == OPTION_TYPE) {
            val tempOptionBinding =
                ItemOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            tempOptionBinding.itemOptionLayout.also { layout ->
                layout.layoutParams.height = (deviceWidth.toFloat() * 0.25).toInt()
            }

            tempOptionBinding.itemOptionLayout.setOnClickListener { v ->
                godClick.onClick(v)
            }



            return OptionHolder(tempOptionBinding)
        } else {
            val tempAdBinding =
                ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            tempAdBinding.itemAdLayout.also { layout ->
                layout.layoutParams.height = (deviceWidth.toFloat() * 0.25).toInt()
            }

            tempAdBinding.itemAdLayout.setOnClickListener {
                godClick.onClick(it)
            }

            return AdHolder(tempAdBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val checkViewType = getItemViewType(position)
        if (checkViewType == OPTION_TYPE) {
            val mBind = (holder as OptionHolder).optionBinding
//            Utils.open_tiny_flyup(mBind.itemOptionLayout)

            if (mArrs[position].hasSwitch == true) {
                mBind.itemOptionSwitch.also { sw ->
                    sw.visibility = View.VISIBLE
                    sw.isChecked = mArrs[position].choosed ?: false
                    sw.setOnCheckedChangeListener { compoundButton, b ->
                        godSwitch.onSwitch(b,mArrs[position].order)
                    }
                }
            }

            mBind.itemOptionImage.setImageDrawable(ContextCompat.getDrawable(mBind.root.context,
                mArrs[position].image!!
            ))

            mBind.itemOptionTitle.text = mArrs[position].title ?: "title"

        } else {
            val mBind = (holder as AdHolder).adBinding
//            Utils.open_tiny_flyup(mBind.itemAdLayout)

            mBind.itemAdImage.setImageDrawable(ContextCompat.getDrawable(mBind.root.context,
                mArrs[position].image!!
            ))

            mBind.itemAdTitle.text = mArrs[position].title
            mBind.itemAdContent.text = mArrs[position].content
        }
    }


    override fun getItemCount(): Int {
        return mArrs.size
    }


}