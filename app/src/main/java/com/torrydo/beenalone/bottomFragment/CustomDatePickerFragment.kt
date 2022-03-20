package com.torrydo.beenalone.bottomFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.FragmentCustomDatePickerBinding
import com.torrydo.beenalone.vModel.MyViewModel
import java.util.*

class CustomDatePickerFragment : BottomSheetDialogFragment() {

    private val mViewModel:MyViewModel by activityViewModels()
    private var binding: FragmentCustomDatePickerBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDatePickerBinding.inflate(LayoutInflater.from(requireContext()))
        defaultStuffs()


        myEyes()
        clicker()
        return binding!!.root
    }

    private fun myEyes() {
            binding!!.bottomSheetTitle.text = mViewModel.lonelyDate.value
    }

    private fun clicker() {
        binding!!.bottomSheetButtonPositive.setOnClickListener {
            val dd = binding!!.bottomSheetNumberPicker1.value
            val mm = binding!!.bottomSheetNumberPicker2.value
            val yyyy = binding!!.bottomSheetNumberPicker3.value
            mViewModel.lonelyDay = dd
            mViewModel.lonelyMonth = mm
            mViewModel.lonelyYear = yyyy
            mViewModel.lonelyDate.value = "$dd/$mm/$yyyy"

            mViewModel.mPref.edit().putString(CONSTANT.LONELY_DATE,mViewModel.lonelyDate.value).apply()

            mViewModel.refrehStatus()

            this.dismiss()
        }
        binding!!.bottomSheetButtonNegative.setOnClickListener {
            this.dismiss()
        }
    }

    private fun defaultStuffs() {

        calendarAlgorithm()
    }


    private fun calendarAlgorithm() {
        binding!!.bottomSheetNumberPicker3.also { np3 ->
            np3.maxValue = 2100
            np3.minValue = 1800
            np3.value = mViewModel.lonelyYear

            // thuật toán tính giải năm
            np3.setOnValueChangedListener { numberPicker3, oldValue3, newValue3 ->

                if (newValue3 % 4 == 0) {
                    val tempMonth1 = binding!!.bottomSheetNumberPicker2.value
                    if (tempMonth1 == 2) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 29
                    } else if (tempMonth1 == 4 || tempMonth1 == 6 || tempMonth1 == 9 || tempMonth1 == 11) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 30
                    } else {
                        binding!!.bottomSheetNumberPicker1.maxValue = 31
                    }

                    setCurrentDate(
                        binding!!.bottomSheetNumberPicker1.value,
                        binding!!.bottomSheetNumberPicker2.value,
                        binding!!.bottomSheetNumberPicker3.value
                    )

                } else {
                    val tempMoth2 = binding!!.bottomSheetNumberPicker2.value
                    if (tempMoth2 == 2) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 28
                    } else if (tempMoth2 == 4 || tempMoth2 == 6 || tempMoth2 == 9 || tempMoth2 == 11) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 30
                    } else {
                        binding!!.bottomSheetNumberPicker1.maxValue = 31
                    }

                    setCurrentDate(
                        binding!!.bottomSheetNumberPicker1.value,
                        binding!!.bottomSheetNumberPicker2.value,
                        binding!!.bottomSheetNumberPicker3.value
                    )
                }

            }
        }

        binding!!.bottomSheetNumberPicker1.also { np1 ->
            if (mViewModel.lonelyMonth == 2) {
                if (mViewModel.lonelyYear % 4 == 0) {
                    np1.maxValue = 29
                } else {
                    np1.maxValue = 28
                }
            } else if (mViewModel.lonelyMonth == 4 || mViewModel.lonelyMonth == 6 || mViewModel.lonelyMonth == 9 || mViewModel.lonelyMonth == 11) {
                np1.maxValue = 30
            } else {
                np1.maxValue = 31
            }
            np1.setOnValueChangedListener { numberPicker, old, new ->
                setCurrentDate(
                    binding!!.bottomSheetNumberPicker1.value,
                    binding!!.bottomSheetNumberPicker2.value,
                    binding!!.bottomSheetNumberPicker3.value
                )
            }

            np1.minValue = 1
            np1.value = mViewModel.lonelyDay
        }
        binding!!.bottomSheetNumberPicker2.also { np2 ->
            np2.maxValue = 12
            np2.minValue = 1
            np2.value = mViewModel.lonelyMonth

            setCurrentDate(
                binding!!.bottomSheetNumberPicker1.value,
                binding!!.bottomSheetNumberPicker2.value,
                binding!!.bottomSheetNumberPicker3.value
            )

            np2.setOnValueChangedListener { numberPicker, old, new ->

                if (binding!!.bottomSheetNumberPicker3.value % 4 == 0) {
                    if (new == 2) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 29
                    } else if (new == 4 || new == 6 || new == 9 || new == 11) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 30
                    } else {
                        binding!!.bottomSheetNumberPicker1.maxValue = 31
                    }

                    setCurrentDate(
                        binding!!.bottomSheetNumberPicker1.value,
                        binding!!.bottomSheetNumberPicker2.value,
                        binding!!.bottomSheetNumberPicker3.value
                    )

                } else {
                    if (new == 2) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 28
                    } else if (new == 4 || new == 6 || new == 9 || new == 11) {
                        binding!!.bottomSheetNumberPicker1.maxValue = 30
                    } else {
                        binding!!.bottomSheetNumberPicker1.maxValue = 31
                    }

                    setCurrentDate(
                        binding!!.bottomSheetNumberPicker1.value,
                        binding!!.bottomSheetNumberPicker2.value,
                        binding!!.bottomSheetNumberPicker3.value
                    )

                }
            }
        }
    }

    private fun setCurrentDate(day: Int, month: Int, year: Int) {
        binding!!.bottomSheetTitle.text = "$day/$month/$year"
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