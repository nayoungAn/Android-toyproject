package com.greedy.vincent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.vincent.databinding.ActivityMainBinding
import com.greedy.vincent.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityResultBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        /* 로그인한 계정의 email 화면 출력 */
        binding.currentUser.text = "${auth.currentUser?.email}"

        /* 로그아웃 버튼 클릭 이벤트 */
        binding.btnLogout.setOnClickListener {

            /* 로그아웃 후 로그인 화면으로 갈 때 activity stack을 비우고 이동 */
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

            /* firebase 서버에도 로그아웃 요청 */
            auth.signOut()
        }

    }

}