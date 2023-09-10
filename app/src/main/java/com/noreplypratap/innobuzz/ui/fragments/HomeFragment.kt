package com.noreplypratap.innobuzz.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.noreplypratap.innobuzz.R
import com.noreplypratap.innobuzz.databinding.FragmentHomeBinding
import com.noreplypratap.innobuzz.model.UsersPostsModel
import com.noreplypratap.innobuzz.ui.adapters.UsersDataAdapter
import com.noreplypratap.innobuzz.utils.Constants
import com.noreplypratap.innobuzz.utils.Resource
import com.noreplypratap.innobuzz.utils.Utils
import com.noreplypratap.innobuzz.utils.isOnline
import com.noreplypratap.innobuzz.viewmodel.IbUserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val ibUserViewModel : IbUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        setHasOptionsMenu(true)

        loadFromDB()

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

    }

    override fun onResume() {
        super.onResume()
        if (Utils.status){
            Toast.makeText(context,"WhatsApp Launched", Toast.LENGTH_SHORT).show()
            Utils.status = false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_items, menu)
        val btn = menu.findItem(R.id.app_bar_accessibility)
        btn.setOnMenuItemClickListener {

            if (context?.isOnline() == true){
                Toast.makeText(context,"Online Data", Toast.LENGTH_SHORT).show()
                apiCall()
            }else{
                Toast.makeText(context,"No Internet", Toast.LENGTH_SHORT).show()
            }

            true

        }

    }

    private fun loadFromDB() {
        showProgressBar()
        ibUserViewModel.getSavedData().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                setRecyclerView(it as ArrayList<UsersPostsModel>)
                hideProgressBar()
            } else if (context?.isOnline() == true) {
                apiCall()
            } else {
                Log.d(Constants.TAG, "No Internet")
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun apiCall() {
        Log.d(Constants.TAG,"API Started .......")
        ibUserViewModel.getUsersPosts().invokeOnCompletion {
            Log.d(Constants.TAG,"Data received.....")
        }

        ibUserViewModel.usersPost.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    hideProgressBar()
                    data.data?.let {
                        ibUserViewModel.saveDataToDb(it.toList()).invokeOnCompletion {
                            Log.d(Constants.TAG, "Data Saved to DB..........")
                        }
                        setRecyclerView(it)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }


    private fun setRecyclerView(usrData : ArrayList<UsersPostsModel>){
        val usersDataAdapter =  UsersDataAdapter(usrData)
        binding.rvUsersPosts.apply {
            adapter = usersDataAdapter
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        }

        usersDataAdapter.setOnClickListener {
            Log.d(Constants.TAG,"Clicked.......")
            val bundle = Bundle()
            bundle.putString("nextData" , Gson().toJson(it))
            findNavController().navigate(R.id.action_homeFragment_to_postDataFragment,bundle)
        }
    }

}
