package com.greedy.vincent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.greedy.template.API.Item
import com.greedy.template.API.ResponseData
import com.greedy.vincent.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentList : Fragment() {

    lateinit var binding: FragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostsAdapter
    private lateinit var responseData: ResponseData
    private lateinit var itemList: MutableList<Item?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        recyclerView = binding.postsView
        loadData()
        return  binding.root
    }

    private fun loadData() {

        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {
                val response = PostsService.getPostsService().posts()
                if (response.isSuccessful) {
                    responseData = response.body()!!
                    itemList = responseData.response.body.items.item.toMutableList()
                } else {
                    Log.d("Error", "${response.message()}")
                }
            }
            adapter = PostsAdapter(itemList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }
}