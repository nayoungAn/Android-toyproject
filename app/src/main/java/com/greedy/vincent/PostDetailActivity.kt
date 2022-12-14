package com.greedy.vincent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.greedy.sqlite.SqliteHelper
import com.greedy.vincent.databinding.ActivityPostDetailBinding

class PostDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPostDetailBinding.inflate(layoutInflater) }
    val helper = SqliteHelper(this, "comment", 1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val contentId = intent.getStringExtra("contentId")
        val adapter = CommentRecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper.selectComment(contentId!!))
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setListenerToEditText()

        val facltNm = intent.getStringExtra("facltNm")
        val intro = intent.getStringExtra("intro")
        val induty = intent.getStringExtra("induty")
        val addr1 = intent.getStringExtra("addr1")
        val sbrsCl = intent.getStringExtra("sbrsCl")
        val lineIntro = intent.getStringExtra("lineIntro")
        val firstImageUrl = intent.getStringExtra("firstImageUrl")
        binding.lineIntro.text=lineIntro
        binding.facltNm.text=facltNm
        binding.intro.text=intro
        binding.induty.text=induty
        binding.addr1.text=addr1
        binding.sbrsCl.text=sbrsCl

        Glide.with(binding.imageView2).load(firstImageUrl).into(binding.imageView2)

        binding.saveButton.setOnClickListener {
            if (binding.editComment.text.toString().isNotEmpty()) {
                val comment = Comment(null, contentId!!, binding.editComment.text.toString(), System.currentTimeMillis())
                helper.insertComment(comment)

                adapter.listData.clear()
                adapter.listData.addAll(helper.selectComment(contentId))
                adapter.notifyDataSetChanged()

                /* ????????? ????????? */
                binding.editComment.setText("")
            }
            else { Log.d("??????","??????")}
        }
    }

    private fun setListenerToEditText() {
        binding.editComment.setOnKeyListener { view, keyCode, event ->
            // Enter Key Action
            if (event.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                // ????????? ?????????
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editComment.windowToken, 0)
                true
            }

            false
        }
    }

}