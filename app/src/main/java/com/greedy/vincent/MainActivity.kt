package com.greedy.vincent

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.vincent.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        auth = Firebase.auth


        binding.join.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }


        binding.btnLogin.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()) {

                signIn(email, password)
            } else {
                Toast.makeText(this, "email과 password를 입력해주세요", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    moveResultPage()
                } else {
                    Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            moveResultPage()
        }
    }


    fun moveResultPage() {
        startActivity(Intent(this, ResultActivity::class.java))
        finish()
    }





}