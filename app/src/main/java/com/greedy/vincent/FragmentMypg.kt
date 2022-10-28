package com.greedy.vincent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.vincent.databinding.FragmentMypgBinding

class FragmentMypg : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentMypgBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypgBinding.inflate(inflater, container, false)

        auth = Firebase.auth


        binding.currentUser.text = "${auth.currentUser?.email}"


        binding.btnLogout.setOnClickListener {


            val intent = Intent(this.context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)


            auth.signOut()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}