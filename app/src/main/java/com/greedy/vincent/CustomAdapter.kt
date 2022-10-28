package com.greedy.vincent

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.greedy.vincent.databinding.ItemRecyclerBinding

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    /* RecyclerView에서 사용할 CampList Data */
    var listData = mutableListOf<CampList>()
    /* 호출 횟수 카운트를 위한 변수 */
    var createViewHolderCount: Int = 0
    var bindViewHolderCount: Int = 0

    /* 아이템 레이아웃을 생성하는 콜백 메소드 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        /* 첫 번째 인자 : 해당 뷰가 속한 뷰 그룹의 context를 이용하여 layoutInflater를 만들어 전달
        * 두 번째 인자 : 인플레이션을 진행할 뷰 그룹을 전달
        * 세 번째 인자 : true인 경우 root를 지정하고 그 아래 인플레이션 화면을 붙이지만,
        * false인 경우 최상위 레이아웃의 속성을 기본으로 레이아웃 적용 (리사이클러의 레이아웃) */
        val binding
                = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        Log.d("Adapter", "onCreateViewHolder 호출 ${createViewHolderCount++}")

        /* binding을 전달한 Holder 객체를 반환 */
        return Holder(binding)
    }

    /* data를 바인딩하는 콜백 메소드 */
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val camplist = listData[position]
        holder.setMemo(camplist)

        Log.d("Adapter", "onBindViewHolder 호출 ${bindViewHolderCount++}")

    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

    /* 각 메모를 클릭 했을 때 동작하는 이벤트 */
    init {
        /* 리사이클러뷰 레이아웃의 최상단 루트는 item_recycler에 작성한 레이아웃인 LinearLayout이다 */
        binding.root.setOnClickListener {
            /* 간단하게 토스트 메세지를 띄웠지만 메모 상세보기로 startActivity를 호출하여 새로운 액티비티를 보여주는 형태로 처리할 수 있다. */
            Toast.makeText(binding.root.context, "클릭 된 아이템 = ${binding.txtTitle.text}", Toast.LENGTH_SHORT).show()
        }
    }

    fun setMemo(campList: CampList) {
        binding.txtTitle.text = "${campList.title}"
        binding.txtAdd.text = "${campList.add}"
    }

}
