package com.noreplypratap.innobuzz.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.noreplypratap.innobuzz.R
import com.noreplypratap.innobuzz.databinding.FragmentPostDataBinding
import com.noreplypratap.innobuzz.model.UsersPostsModel
import com.noreplypratap.innobuzz.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDataFragment : Fragment(R.layout.fragment_post_data) {

    private lateinit var binding: FragmentPostDataBinding
    private var postsData : UsersPostsModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDataBinding.bind(view)

        getData()

    }

    private fun getData() {
        val data = arguments?.getString("nextData")
        if (data != null){
            postsData = Gson().fromJson(data,UsersPostsModel::class.java)
            setView()
        }
        else{
            Log.d(Constants.TAG,"No Data........")
        }
    }

    private fun setView() {
        binding.tvId.text = "ID = ${postsData?.id.toString()}"
        binding.tvBody.text = postsData?.body.toString()
        binding.tvTitle.text = postsData?.title.toString()
        binding.tvUserId.text = "User ID : ${postsData?.userId.toString()}"
    }

}