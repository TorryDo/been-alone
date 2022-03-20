package com.torrydo.beenalone.fragment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.torrydo.beenalone.CONSTANT
import com.torrydo.beenalone.R
import com.torrydo.beenalone.adapter.Adapter_options
import com.torrydo.beenalone.databinding.FragmentMain3Binding
import com.torrydo.beenalone.interfaces.GodClick
import com.torrydo.beenalone.interfaces.GodSwitch
import com.torrydo.beenalone.models.mOption
import com.torrydo.beenalone.utility.Utils
import com.torrydo.beenalone.vModel.MyViewModel
import kotlinx.coroutines.*
import www.sanju.motiontoast.MotionToast
import java.util.*


class Main_3_Fragment : Fragment() {

    private val mViewModel: MyViewModel by activityViewModels()
    private var binding: FragmentMain3Binding? = null

    private val GOOGLE_SIGNIN_CODE = 69

    /** log in FB*/
    private lateinit var cbm: CallbackManager

    private var mAdapter: Adapter_options? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var mArrayOption: ArrayList<mOption>? = null

    private val UPGRDATE_TO_PRO = 0
    private val RATE_APP = 1
    private val DARK_MODE = 2
    private val CONTACT = 4
    private val CLEAR_CACHE = 5
    private val DELETE_CLOUD = 6
    private val SIGN_OUT = 7
    private val ABOUT = 8
    private val PLAY_MUSIC_WHEN_STARTING = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMain3Binding.inflate(inflater, container, false)
        defaultStuffs()


        clicker()
        defaultUI()
        myEyes()
        return binding!!.root
    }

    private fun myEyes() {

        mViewModel.mDarkMode.observe(viewLifecycleOwner, {
            mArrayOption!![DARK_MODE].choosed = it
            mAdapter?.notifyDataSetChanged()

        })
        mViewModel.mPlayMusic.observe(viewLifecycleOwner, {
            mArrayOption!![PLAY_MUSIC_WHEN_STARTING].choosed = it
            mAdapter?.notifyDataSetChanged()
        })

        mViewModel.myUser.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding!!.main3LoginFb.visibility = View.INVISIBLE
                binding!!.main3IcFb.visibility = View.INVISIBLE
                binding!!.main3Name.also {
                    it.visibility = View.VISIBLE
                    it.text = user.userName
                }
            } else {
                binding!!.main3LoginFb.visibility = View.VISIBLE
                binding!!.main3Name.visibility = View.INVISIBLE
                binding!!.main3IcFb.visibility = View.VISIBLE
            }
        })
    }

    private fun clicker() {

        binding!!.main3LoginFb.setOnClickListener {
            if (mViewModel.firebaseAuth.currentUser != null) {
                Firebase.auth.signOut()
                mViewModel.disconnectFromFacebook()
                Toast.makeText(requireContext(), "fb signout", Toast.LENGTH_SHORT).show()
            } else {
                LoginManager.getInstance().logInWithReadPermissions(
                    this@Main_3_Fragment,
                    arrayListOf("email", "public_profile")
                )
            }
        }

//        binding!!.main3LoginGoogle.setOnClickListener {
//
//            val gsa: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireContext())
//
//            googleSignInOptions =
//                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////                        .requestIdToken(getString(com.google.firebase.database.R.string.default_web_client_id))
//                    .requestEmail()
//                    .build()
//            googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions!!)
//
//            if (gsa != null) {
//                googleSignInClient!!.signOut().addOnCompleteListener {
//                    Toast.makeText(
//                        requireContext(),
//                        gsa.displayName + " -- " + gsa.email,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            } else {
//                startActivityForResult(googleSignInClient!!.signInIntent, GOOGLE_SIGNIN_CODE)
//            }
//        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun defaultStuffs() {

        binding!!.main3Recycler.also { recy ->

            mArrayOption = arrayListOf<mOption>(
                mOption(
                    R.drawable.ic_death_click,
                    getString(R.string.option_title_1),
                    getString(R.string.option_content_1),
                    hasSwitch = false,
                    choosed = null,
                    isAd = true,
                    order = UPGRDATE_TO_PRO
                ),
                mOption(
                    R.drawable.ic_five_stars,
                    getString(R.string.option_title_2),
                    getString(R.string.option_content_2),
                    hasSwitch = false,
                    choosed = null,
                    isAd = true,
                    order = RATE_APP
                ),
                mOption(
                    R.drawable.ic_darkmode, getString(R.string.option_title_3), null,
                    hasSwitch = true,
                    choosed = mViewModel.mDarkMode.value,
                    isAd = false, DARK_MODE
                ),
                mOption(
                    R.drawable.ic_music, getString(R.string.option_title_9), null,
                    hasSwitch = true,
                    choosed = mViewModel.mPlayMusic.value,
                    isAd = false,
                    order = PLAY_MUSIC_WHEN_STARTING
                ),
                mOption(
                    R.drawable.ic_paper_plane,
                    getString(R.string.option_title_4),
                    getString(R.string.option_content_4),
                    hasSwitch = false,
                    choosed = null,
                    isAd = true,
                    order = CONTACT
                ),
                mOption(
                    R.drawable.ic_rocket, getString(R.string.option_title_8), null,
                    hasSwitch = false,
                    choosed = null,
                    isAd = false,
                    order = CLEAR_CACHE
                ),
//                mOption(
//                    R.drawable.ic_emptybox, getString(R.string.option_title_5), getString(R.string.option_content_5),
//                    hasSwitch = false,
//                    choosed = null,
//                    isAd = true,
//                    order = DELETE_CLOUD
//                ),
                mOption(
                    R.drawable.ic_signout, getString(R.string.option_title_6), null,
                    hasSwitch = false,
                    choosed = null,
                    isAd = false,
                    order = SIGN_OUT
                ),
                mOption(
                    R.drawable.ic_info, getString(R.string.option_title_7), null,
                    hasSwitch = false,
                    choosed = null,
                    isAd = false,
                    order = ABOUT
                ),
            )

            mAdapter =
                Adapter_options(
                    mArrayOption!!,
                    object : GodClick {
                        @SuppressLint("QueryPermissionsNeeded")
                        override fun onClick(v: View) {
                            val position = recy.getChildLayoutPosition(v)
                            when (mArrayOption!![position].order) {
                                UPGRDATE_TO_PRO -> {
                                    /** upgrade to pro */

                                }
                                RATE_APP -> {
                                    /** rate app */
                                    val packageName = getString(R.string.package_name)
                                    val uri = Uri.parse("market://details?id=$packageName")
                                    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                                    try {
                                        startActivity(myAppLinkToMarket)
                                    } catch (e: ActivityNotFoundException) {
                                        MotionToast.createColorToast(
                                            requireActivity(),
                                            "ERROR",
                                            "Not Available",
                                            MotionToast.TOAST_ERROR,
                                            MotionToast.GRAVITY_BOTTOM,
                                            MotionToast.LONG_DURATION,
                                            null
                                        )
                                    }
                                }
//                                DARK_MODE -> {   /** dark mode */
//                                    triggerDarkMode( ! (mArrayOption!![position].choosed ?: true) )
//                                }
//                                PLAY_MUSIC_WHEN_STARTING -> {   /** play music when starting */
//                                    triggerPlayMusic( ! (mArrayOption!![position].choosed ?: false) )
//                                }
                                CONTACT -> {
                                    /** suggestion & contact */
                                    val addresses = arrayOf(getString(R.string.dev_email))
                                    val subject = getString(R.string.email_title_contact)
                                    val attachment: Uri? = null

                                    val intent = Intent(Intent.ACTION_SEND);

                                    intent.data = Uri.parse("mailto:");
                                    intent.type = "message/rfc822";

//                            intent.type = "*/*";
                                    intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                    intent.putExtra(Intent.EXTRA_STREAM, attachment);
                                    if (intent.resolveActivity(requireActivity().packageManager) != null) {
                                        startActivity(Intent.createChooser(intent, "send Email"));
                                    }
                                }
                                CLEAR_CACHE -> {
                                    /** clear cache */
                                    GlobalScope.launch(Dispatchers.IO) {
                                        try {
                                            val b = requireContext().cacheDir.deleteRecursively()
                                            withContext(Dispatchers.Main) {
                                                if (b) {
                                                    delay(1000L)
                                                    MotionToast.createColorToast(
                                                        requireActivity(),
                                                        "SUCCESS",
                                                        "storage has been optimized",
                                                        MotionToast.TOAST_SUCCESS,
                                                        MotionToast.GRAVITY_BOTTOM,
                                                        MotionToast.LONG_DURATION,
                                                        null
                                                    )
                                                }
                                            }
                                        } catch (e: Exception) {
                                            MotionToast.createColorToast(
                                                requireActivity(),
                                                "ERROR",
                                                "error appear",
                                                MotionToast.TOAST_ERROR,
                                                MotionToast.GRAVITY_BOTTOM,
                                                MotionToast.LONG_DURATION,
                                                null
                                            )
                                        }
                                    }
                                }
                                DELETE_CLOUD -> {
                                    /** delete data in the cloud */

                                }
                                SIGN_OUT -> {
                                    /** sign out */
                                    mViewModel.disconnectFromFacebook()
                                    mViewModel.firebaseAuth.signOut()
                                    mViewModel.myUser.value = null
                                }
                                ABOUT -> {
                                    /** about */

                                }


                            }
                        }
                    },
                    Utils.getDeviceWidth_and_Height(requireActivity(), 0),
                    object : GodSwitch {
                        override fun onSwitch(value: Boolean, type: Int) {
                            when (type) {
                                DARK_MODE -> {
                                    triggerDarkMode(value)
                                }
                                PLAY_MUSIC_WHEN_STARTING -> {
                                    triggerPlayMusic(value)
                                }
                            }
                        }

                    }
                )
            mLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            recy.setHasFixedSize(true)
            recy.layoutManager = mLayoutManager
            recy.adapter = mAdapter
        }

        cbm = CallbackManager.Factory.create()

        lifecycleScope.launch(Dispatchers.IO) {

            LoginManager.getInstance().registerCallback(cbm, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Log.d("_info", "facebook:onSuccess:$loginResult")
                    handleFacebookAccessToken(loginResult.accessToken)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Log.d("_info", "facebook:onCancel")
                    Toast.makeText(requireContext(), "cancel", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Log.d("_info", "facebook:onError", error)
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            })
        }


        binding!!.root.setOnTouchListener { view, motionEvent ->
            requireActivity().findViewById<ViewPager2>(R.id.main_viewPager).isUserInputEnabled =
                true
            return@setOnTouchListener false
        }
        binding!!.main3Recycler.setOnTouchListener { view, motionEvent ->
            requireActivity().findViewById<ViewPager2>(R.id.main_viewPager).isUserInputEnabled =
                true
            return@setOnTouchListener false
        }
    }

    private fun triggerDarkMode(value: Boolean) {
        mViewModel.mPref.edit().putBoolean(CONSTANT.DARKMODE, value).apply()
        mViewModel.refrehStatus()
    }

    private fun triggerPlayMusic(value: Boolean) {
        mViewModel.mPref.edit().putBoolean(CONSTANT.PLAY_MUSIC, value).apply()
        mViewModel.refrehStatus()
    }


    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("_info", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mViewModel.firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("_info", "signInWithCredential:success")
                    Toast.makeText(
                        requireContext(),
                        "signInWithCredential:success",
                        Toast.LENGTH_SHORT
                    ).show()
//                    val user = firebaseAuth!!.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("_info", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }
    }

    private fun defaultUI() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        cbm.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == GOOGLE_SIGNIN_CODE) {
//            if (data != null && resultCode == AppCompatActivity.RESULT_OK) {
//
//                val tempX: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
//                val credential = GoogleAuthProvider.getCredential(tempX.result!!.idToken.toString(), null)
//                mViewModel.firebaseAuth!!.signInWithCredential(credential).addOnCompleteListener {
//                    Toast.makeText(
//                        requireContext(),
//                        "signIn success : ${tempX.result!!.displayName}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                }
//            } else {
//                Toast.makeText(requireContext(), "error appeared", Toast.LENGTH_SHORT).show()
//            }
//        }
    }


}