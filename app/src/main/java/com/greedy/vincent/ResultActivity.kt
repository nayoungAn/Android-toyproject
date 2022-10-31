package com.greedy.vincent

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.greedy.vincent.databinding.ResultActivityBinding

class ResultActivity : AppCompatActivity() {


    private val binding by lazy { ResultActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* 프래그먼트 리스트를 만든다. */
        val fragmentList = listOf(FragmentMain(), FragmentList(), FragmentMypg())

        /* 프래그먼트 어댑터를 생성하고 프래그먼트 목록을 저장한다. */
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList as List<Fragment>

        /* viewPager를 다루기 위한 adapter 연결 */
        binding.viewPager.adapter = adapter

        /* TabLayout 추가 시 작성 */
        var tabTitles = listOf<String>("MAIN", "LIST", "MYPG", "INFO")

        /* TabLayout과 ViewPager 연결을 위해 TabLayoutMediator를 사용 */
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

    }


}