package com.torrydo.beenalone.bottomFragment

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.adapter.Adapter_rank
import com.torrydo.beenalone.databinding.FragmentRankBottomBinding
import com.torrydo.beenalone.interfaces.GodClick
import com.torrydo.beenalone.models.MyRank
import com.torrydo.beenalone.vModel.MyViewModel

class RankBottomFragment : BottomSheetDialogFragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentRankBottomBinding? = null

    private var mArrayRank: ArrayList<MyRank>? = null
    private var mAdapter: Adapter_rank? = null
    private var lm: LinearLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBottomBinding.inflate(inflater, container, false)
        defaultStuffs()


        defaultUI()
        clicker()
        return binding!!.root
    }

    private fun defaultStuffs() {
        mArrayRank = arrayListOf<MyRank>(
            MyRank(
                1,
                R.drawable.ic_peasant,
                getString(R.string.peasant),
                getString(R.string.peasant_content)
            ),
            MyRank(
                10,
                R.drawable.ic_sword,
                getString(R.string.trooper),
                getString(R.string.trooper_content)
            ),
            MyRank(
                25,
                R.drawable.ic_helmet,
                getString(R.string.warrior),
                getString(R.string.warrior_content)
            ),
            MyRank(
                50,
                R.drawable.ic_mage_stick,
                getString(R.string.mage),
                getString(R.string.mage_content)
            ),
            MyRank(
                100,
                R.drawable.ic_adventurer,
                getString(R.string.adventurer),
                getString(R.string.adventurer_content)
            ),
            MyRank(
                170,
                R.drawable.ic_star_medal,
                getString(R.string.noble),
                getString(R.string.noble_content)
            ),
            MyRank(
                300,
                R.drawable.ic_blue_diamond,
                getString(R.string.philosopher),
                getString(R.string.philosopher_content)
            ),
            MyRank(
                500,
                R.drawable.ic_crown,
                getString(R.string.king),
                getString(R.string.king_content)
            )
        )

        lm = LinearLayoutManager(requireContext())


    }

    private fun clicker() {
    }

    //    private var popMenu : PopupMenu? = null
    private fun defaultUI() {

        binding!!.rankRecycler.also { rec ->

            mAdapter = Adapter_rank(
                mArrayRank!!,
                mViewModel.getCurrentLvl(mViewModel.mExp.value?.toLong() ?: 1),
                object : GodClick {
                    override fun onClick(v: View) {

                        val reachR =
                            mArrayRank!![binding!!.rankRecycler.getChildLayoutPosition(v)].reached

                        if (mViewModel.getCurrentLvl(mViewModel.mExp.value?.toLong() ?: 1) > reachR)
                        {
                            var popMenu: PopupMenu? = PopupMenu(requireContext(), v)
                            var menu: Menu? = popMenu!!.menu
                            menu!!.add(getString(R.string.menu_text))

                            popMenu.setOnMenuItemClickListener {

                                mViewModel.mPref.edit().putInt(
                                    CONSTANT.USER_RANK_ICON,
                                    reachR
                                ).apply()

                                mViewModel.refrehStatus()

                                popMenu = null
                                menu = null

                                true
                            }

                            popMenu?.setOnDismissListener {
                                popMenu = null
                                menu = null
                            }

                            popMenu?.show()

                        } else {
                            Toast.makeText(requireContext(),getString(R.string.warning_not_reached_lvl_title),Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            rec.setHasFixedSize(true)
            rec.layoutManager = lm
            rec.adapter = mAdapter
        }

    }


    override fun onPause() {
        super.onPause()
        if (isRemoving) {
            binding!!.root.removeAllViews()
            binding = null
        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}