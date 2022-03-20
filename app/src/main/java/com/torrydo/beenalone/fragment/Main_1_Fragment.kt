package com.torrydo.beenalone.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.utils.LottieValueAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.databinding.FragmentMain1Binding
import com.torrydo.beenalone.utility.ConvertSmth
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import kotlin.random.Random


class Main_1_Fragment : Fragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentMain1Binding? = null

    private var deviceWidth = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMain1Binding.inflate(inflater, container, false)

        defaultStuffs()
        defaultUI()


        clicker()
        myEyes()
        return binding!!.root
    }


    var b = true
    var edittext: EditText? = null
    var buttonNegative: TextView? = null
    var buttonPositive: MaterialButton? = null
    var alertDialog: AlertDialog? = null
    fun applyNullToEditor() {
        edittext = null
        buttonNegative = null
        buttonPositive = null
        alertDialog = null
    }

    private fun clicker() {

        binding!!.main1Avatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            try {
                startActivityForResult(intent, CONSTANT.REQUEST_SELECT_AVATAR)
            } catch (e: Exception) {
                Log.e("BUGLOL", "cant choose img")
            }

        }

        binding!!.main1ChooseImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            try {
                startActivityForResult(intent, CONSTANT.REQUEST_SELECT_IMAGE_IN_ALBUM)
            } catch (e: Exception) {
                Log.e("BUGLOL", "cant choose img")
            }

        }

        binding!!.main1TxtRankTitle.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_rankBottomFragment)
        }
        binding!!.main1RankIcon.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_rankBottomFragment)
        }

        binding!!.main1TxtCoinTitle.setOnClickListener {
            Snackbar.make(binding!!.main1Layout, "useless currency", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.main_color))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color_light))
                .setAnchorView(R.id.main_bottomNavView)
                .show()
        }

        binding!!.main1TxtExpTitle.setOnClickListener {
            Snackbar.make(binding!!.main1Layout, "lonely experience", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.main_color))
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.text_color_light))
                .setAnchorView(R.id.main_bottomNavView)
                .show()
        }

        binding!!.main1TxtNameBottom.also {
            it.text = getString(R.string.lvl_title) + " : " + mViewModel.getCurrentLvl()
        }

        binding!!.main1TxtName.setOnClickListener {
            alertDialog =
                MaterialAlertDialogBuilder(
                    requireContext(),
                    R.style.alertDialog_roundCorner
                ).setView(R.layout.fragment_dialog_txt_editor)
                    .create()

            alertDialog!!.show()

            edittext = alertDialog!!.findViewById(R.id.editor_edittext)
            buttonNegative = alertDialog!!.findViewById(R.id.editor_negative)
            buttonPositive = alertDialog!!.findViewById(R.id.editor_positive)

            buttonPositive!!.setOnClickListener {
                if (edittext!!.text.toString().isEmpty() || edittext!!.text.toString().length < 3) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.editor_warning_shortName),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    mViewModel.mPref.edit()
                        .putString(CONSTANT.USER_NAME, edittext!!.text.toString())
                        .apply()

                    alertDialog?.cancel()
                    mViewModel.refrehStatus()

                    applyNullToEditor()
                }
            }

            buttonNegative!!.setOnClickListener {
                alertDialog?.cancel()
                applyNullToEditor()
            }

            alertDialog?.setOnCancelListener {
                applyNullToEditor()
            }
        }

        binding!!.main1Minimize.setOnClickListener {
            mViewModel.motionChecker.value = true
            binding!!.main1Layout.transitionToEnd()
        }
        binding!!.main1Maximize.setOnClickListener {
            mViewModel.motionChecker.value = false
            binding!!.main1Layout.transitionToStart()
        }

        binding!!.main1ReuseCatAvatar.circleImageView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_dashboardBottomFragment)
        }

        binding!!.main1TxtCounter.setOnClickListener {
            if (b) {
                mViewModel.aloneFor()
                b = false
            } else {
                mViewModel.howManyDay()
                b = true
            }
        }

        binding!!.main1TxtAlonefrom.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_customDatePickerFragment)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun defaultStuffs() {

        binding!!.main1Layout.setOnTouchListener { view, motionEvent ->
            requireActivity().findViewById<ViewPager2>(R.id.main_viewPager).isUserInputEnabled =
                true
            return@setOnTouchListener false
        }
    }

    private fun defaultUI() {
        deviceWidth = Utils.getDeviceWidth_and_Height(requireActivity(), 0)

        loadImageToView(binding!!.main1Img, CONSTANT.BACKGROUND_NAME)
        loadImageToView(binding!!.main1Avatar, CONSTANT.AVATAR_NAME)

        binding!!.main1Rank.also {
//            it.requestLayout()
            it.layoutParams.width = (deviceWidth.toLong() / 6).toInt()
        }

        binding!!.main1ReuseCatAvatar.circleImageView.also { avt ->
            avt.visibility = View.VISIBLE
            avt.requestLayout()
            avt.layoutParams.width = ConvertSmth.getMyPx((deviceWidth.toLong() / 20).toInt())
            avt.layoutParams.height = ConvertSmth.getMyPx((deviceWidth.toLong() / 20).toInt())
        }


        binding!!.main1Main.also {
            it.requestLayout()
            it.layoutParams.height = (deviceWidth.toLong() / 2 * 1.7).toInt()
        }

        binding!!.main1Avatar.also {
            it.requestLayout()
            it.layoutParams.width = ConvertSmth.getMyPx((deviceWidth.toLong() / 18).toInt())
            it.layoutParams.height = ConvertSmth.getMyPx((deviceWidth.toLong() / 18).toInt())
        }

        binding!!.main1Frame.also {
            it.requestLayout()
            it.layoutParams.width = ConvertSmth.getMyPx((deviceWidth.toLong() / 15).toInt())
            it.layoutParams.height = ConvertSmth.getMyPx((deviceWidth.toLong() / 15).toInt())
        }

        displayMessenger()
    }


    private fun myEyes() {

        mViewModel.mName.observe(viewLifecycleOwner, {
            binding!!.main1TxtName.text = it
        })

        mViewModel.motionChecker.observe(viewLifecycleOwner, { b ->
            if (b) {
                binding!!.main1ReuseCatAvatar.circleImageView.isEnabled = false
                binding!!.main1RankIcon.isEnabled = false
                binding!!.main1TxtAlonefrom.isEnabled = false
                binding!!.main1TxtRankTitle.isEnabled = false
                binding!!.main1TxtExpTitle.isEnabled = false
                binding!!.main1TxtCoinTitle.isEnabled = false
            } else {
                binding!!.main1ReuseCatAvatar.circleImageView.isEnabled = true
                binding!!.main1RankIcon.isEnabled = true
                binding!!.main1TxtAlonefrom.isEnabled = true
                binding!!.main1TxtRankTitle.isEnabled = true
                binding!!.main1TxtExpTitle.isEnabled = true
                binding!!.main1TxtCoinTitle.isEnabled = true
            }
        })

        mViewModel.audio_paused.observe(viewLifecycleOwner, { paused ->

            if (!paused) {
                Utils.ifinite_rotate(binding!!.main1ReuseCatAvatar.circleImageView)
            } else {
                binding!!.main1ReuseCatAvatar.circleImageView.clearAnimation()
            }

        })

        mViewModel.timer.observe(viewLifecycleOwner, {
//            Toast.makeText(requireContext(),"on tick",Toast.LENGTH_SHORT).show()
            mViewModel.cdt.start()
            displayMessenger()
        })

        mViewModel.lonelyDate.observe(viewLifecycleOwner, { str ->
            binding!!.main1TxtAlonefrom.text = getString(R.string.main1_alonefrom) + str

        })

        mViewModel.mRankIcon.observe(viewLifecycleOwner, { rankIcon ->
            when (rankIcon) {
                CONSTANT.PEASANT -> {
                    displayRankUI(
                        R.drawable.ic_peasant,
                        R.color.peasant,
                        getString(R.string.main1_rank),
                        getString(R.string.peasant),
                        R.drawable.frame_peasant
                    )

                }

                CONSTANT.TROOPER -> {

                    displayRankUI(
                        R.drawable.ic_sword,
                        R.color.trooper,
                        getString(R.string.main1_rank),
                        getString(R.string.trooper),
                        R.drawable.frame_trooper
                    )

                }
                CONSTANT.WARRIOR -> {
                    displayRankUI(
                        R.drawable.ic_helmet,
                        R.color.warrior,
                        getString(R.string.main1_rank),
                        getString(R.string.warrior),
                        R.drawable.frame_warrior
                    )

                }
                CONSTANT.MAGE -> {
                    displayRankUI(
                        R.drawable.ic_mage_stick,
                        R.color.mage,
                        getString(R.string.main1_rank),
                        getString(R.string.mage),
                        R.drawable.frame_mage
                    )

                }
                CONSTANT.ADVENTURER -> {
                    displayRankUI(
                        R.drawable.ic_adventurer,
                        R.color.adventurer,
                        getString(R.string.main1_rank),
                        getString(R.string.adventurer),
                        R.drawable.frame_adventurer
                    )

                }
                CONSTANT.NOBLE -> {
                    displayRankUI(
                        R.drawable.ic_star_medal,
                        R.color.noble,
                        getString(R.string.main1_rank),
                        getString(R.string.noble),
                        R.drawable.frame_noble
                    )

                }
                CONSTANT.PHILOSOPHER -> {
                    displayRankUI(
                        R.drawable.ic_blue_diamond,
                        R.color.philosopher,
                        getString(R.string.main1_rank),
                        getString(R.string.philosopher),
                        R.drawable.frame_philosopher
                    )

                }
                CONSTANT.KING -> {
                    displayRankUI(
                        R.drawable.ic_crown,
                        R.color.king,
                        getString(R.string.main1_rank),
                        getString(R.string.king),
                        R.drawable.frame_king
                    )
                }
            }
        })

        mViewModel.mExp.observe(viewLifecycleOwner,
            { exp ->
                binding!!.main1TxtExpTitle.text = getString(R.string.main1_exp) + exp.toString()

//            val tempLv = mViewModel.mLevel.value ?: 0
                val currentLv = mViewModel.getCurrentLvl(exp.toLong())
                binding!!.main1TxtLvl.text = currentLv.toString()

            })
        mViewModel.mRank.observe(viewLifecycleOwner,
            { rank ->

                binding!!.main1TxtRankTitle.text = Utils.getTextWithPartialColor(
                    ContextCompat.getColor(requireContext(), R.color.peasant),
                    getString(R.string.main1_rank),
                    rank,
                    underline = true,
                    bold = true
                )
            })
        mViewModel.mCoin.observe(viewLifecycleOwner,
            { coin ->
                binding!!.main1TxtCoinTitle.text = Utils.getTextWithPartialColor(
                    ContextCompat.getColor(requireContext(), R.color.coin_color),
                    getString(R.string.main1_currency),
                    coin.toString(),
                    underline = true,
                    bold = false
                )
            })

//        mViewModel.mLevel.observe(viewLifecycleOwner,{ lvl ->
//            binding!!.main1TxtLvl.text = lvl.toString()
//        })

        mViewModel.mLonelyFor.observe(viewLifecycleOwner,
            { lf ->
                binding!!.main1TxtCounter.text = lf
            })


    }

    private fun displayRankUI(
        icon: Int,
        mainColor: Int,
        str1: String,
        str2: String,
        frameDrawable: Int
    ) {
        binding!!.main1RankIcon.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                icon
            )
        )
        binding!!.main1TxtRankTitle.text = Utils.getTextWithPartialColor(
            ContextCompat.getColor(
                requireContext(),
                mainColor
            ),
            str1,
            str2,
            underline = true,
            bold = true
        )

        binding!!.main1Frame.background = ContextCompat.getDrawable(requireContext(), frameDrawable)

//        when(TYPE){
//            CONSTANT.PEASANT -> {
//                binding!!.main1Frame.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_peasant))
//                binding!!.main1Rank.background = DrawableCompat.setTint(
//                    DrawableCompat.wrap(binding!!.main1Rank.getDrawable()),
//                    ContextCompat.getColor(context, R.color.another_nice_color)
//                );
//            }
//        }

    }

    private fun displayMessenger() {

        binding!!.main1MessengerContent.visibility = View.INVISIBLE
        binding!!.main1Messenger.visibility = View.INVISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            delay(500L)
            binding!!.main1LottieTyping.also {
                withContext(Dispatchers.Main) {
                    it.visibility = View.VISIBLE
                    it.playAnimation()
                    it.repeatCount = LottieValueAnimator.INFINITE
                }
            }
            delay(2500L)
            withContext(Dispatchers.Main) {
                binding!!.main1LottieTyping.also {
                    it.visibility = View.INVISIBLE
                    it.cancelAnimation()
                    it.repeatCount = 0
                }

                Utils.open_fade_fast(requireContext(), binding!!.main1Messenger)

                binding!!.main1MessengerContent.also {
                    val quoteCatArrays = resources.getStringArray(R.array.cat_array)
                    val deathNum = Random.nextInt(0, quoteCatArrays.size )

                    it.text = quoteCatArrays[deathNum].toString()
                    Utils.open_fade_fast(requireContext(), it)
                }

            }
        }
    }

    fun loadImageToView(v: ImageView, resourceName: String) {
        try {
            val myFile = File(
                requireContext().getExternalFilesDir(CONSTANT.FILE_FOLDER),
                resourceName
            )
            if (myFile.exists()) {
                Glide.with(requireContext().applicationContext)
                    .load(myFile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(v)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("BUGLOL", "cant get saved background from folder")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == CONSTANT.REQUEST_SELECT_IMAGE_IN_ALBUM || requestCode == CONSTANT.REQUEST_SELECT_AVATAR) && data != null) {
            try {
                val imageUri: Uri = data.data!!
                val imageStream: InputStream =
                    requireContext().contentResolver.openInputStream(imageUri)!!
                val bm = BitmapFactory.decodeStream(imageStream)

                if (requestCode == CONSTANT.REQUEST_SELECT_IMAGE_IN_ALBUM) {
                    binding!!.main1Img.setImageBitmap(bm)
                    mViewModel.saveImageToExternalStorage(bm, CONSTANT.BACKGROUND_NAME)
                } else if (requestCode == CONSTANT.REQUEST_SELECT_AVATAR) {
                    binding!!.main1Avatar.setImageBitmap(bm)
                    mViewModel.saveImageToExternalStorage(bm, CONSTANT.AVATAR_NAME)
                }

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.refrehStatus()
        b = true

        if (mViewModel.audio_paused.value != null) {
            if (mViewModel.audio_paused.value == false) {
                Utils.ifinite_rotate(binding!!.main1ReuseCatAvatar.circleImageView)
            } else {
                binding!!.main1ReuseCatAvatar.circleImageView.clearAnimation()
            }
        }


    }

    override fun onPause() {
        super.onPause()

        binding!!.main1ReuseCatAvatar.circleImageView.clearAnimation()

        if (requireActivity().isFinishing) {
            if (binding != null) {
                binding!!.root.removeAllViews()
                binding = null
            }
        }
    }

}