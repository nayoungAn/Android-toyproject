package com.greedy.vincent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.vincent.databinding.FragmentListBinding

class FragmentList : Fragment() {

    lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        /* Memo 샘플 데이터 100개 생성 */
        val data: MutableList<CampList> = loadData()

        /* CustomAdapter 생성 */
        var adapter = CustomAdapter()
        adapter.listData = data

        /* RecyclerView의 adapter에 CustomAdapter 설정 */
        binding.recyclerView.adapter = adapter

        /* 리사이클러뷰에 화면을 보여주는 형태를 결정하는 레이아웃 매니저 연결 */
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        return  binding.root
    }


    fun loadData(): MutableList<CampList> {
        val data: MutableList<CampList> = mutableListOf()

        for(no in 1 .. 100) {
            val title = "테스트 메모 제목입니다. $no"
            val add = "주소 입력입니다."
            var camplist = CampList(title, add)
            data.add(camplist)
        }

        return data
    }

}