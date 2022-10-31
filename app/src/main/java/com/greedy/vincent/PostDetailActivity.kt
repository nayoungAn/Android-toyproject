package com.greedy.vincent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.greedy.sqlite.SqliteHelper
import com.greedy.template.API.Item
import com.greedy.vincent.databinding.ActivityPostDetailBinding

class PostDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPostDetailBinding.inflate(layoutInflater) }
    private lateinit var item: Item
    private lateinit var itemList: MutableList<Item?>
    val helper = SqliteHelper(this, "comment", 1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CommentRecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper.selectComment())
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

        /* 메모 저장 버튼 이벤트 */
        binding.saveButton.setOnClickListener {
            /* 메모 내용이 입력 된 경우만 동작 */
            if (binding.editComment.text.toString().isNotEmpty()) {
                val comment = Comment(null, binding.editComment.text.toString(), System.currentTimeMillis())
                helper.insertComment(comment)

                /* DB가 변동 되었을 때 화면도 변동될 수 있도록 adapter의 data를 수정하고
                *  데이터가 변화 했음을 알린다. */
                adapter.listData.clear()
                adapter.listData.addAll(helper.selectComment())
                adapter.notifyDataSetChanged()

                /* 입력란 비우기 */
                binding.editComment.setText("")
            }
            else { Log.d("오류","오류")}
        }
    }

    private fun setListenerToEditText() {
        binding.editComment.setOnKeyListener { view, keyCode, event ->
            // Enter Key Action
            if (event.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                // 키패드 내리기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editComment.windowToken, 0)
                true
            }

            false
        }
    }

}