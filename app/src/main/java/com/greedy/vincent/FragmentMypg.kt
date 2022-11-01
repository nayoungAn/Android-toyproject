package com.greedy.vincent

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Gallery
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.greedy.vincent.databinding.FragmentMypgBinding
import java.lang.Exception


class FragmentMypg : Fragment() {
    var imageView: ImageView? = null
    private val REQUEST_CODE = 0
    var uri: Uri? = null
    private lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentMypgBinding
    lateinit var Gallerybutton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypgBinding.inflate(inflater, container, false)

        auth = Firebase.auth


        binding.currentUser.text = "${auth.currentUser?.email}"
        //binding.currentUser.text = "${auth.currentUser?.createdAt}"


        binding.btnLogout.setOnClickListener {

            val intent = Intent(this.context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


            auth.signOut()
        }

        //  Firebase 이미지 가져오기
        val photoUrl = auth.currentUser?.photoUrl
        Glide.with(binding.imageView2).load(photoUrl).into(binding.imageView2); //이미지사진 넣기

        /* 이미지 버튼 클릭시 */
        imageView = binding.imageView2
        Gallerybutton = binding.GalleryButton

        imageView?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, REQUEST_CODE);
        }

        /* 등록하기 버튼 클릭시 */
        Gallerybutton.setOnClickListener {
            val user = Firebase.auth.currentUser
            user?.let {
                for (profile in it.providerData) {

                    val photoUrl = profile.photoUrl

                    val profileUpdates = userProfileChangeRequest {
                        photoUri =
                            Uri.parse(uri.toString()) }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(binding.root.context,"등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                Log.d(TAG, "User profile updated.")
                            }
                        }

                }
            }
        }



        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)


    }

    //선택한 사진 불러오기


    override fun onActivityResult(requestCode : Int, resultCode : Int ,data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    uri= data?.getData();
                    Glide.with(binding.imageView2).load(uri).into(binding.imageView2); //다이얼로그 이미지사진에 넣기

                } catch (e : Exception) {

                }
            } else if (resultCode == RESULT_CANCELED) {// 취소시 호출할 행동 쓰기
            }
        }
    }





}