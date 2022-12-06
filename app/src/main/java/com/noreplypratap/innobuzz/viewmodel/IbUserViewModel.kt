package com.noreplypratap.innobuzz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.innobuzz.model.UsersPostsModel
import com.noreplypratap.innobuzz.repository.Repository
import com.noreplypratap.innobuzz.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class IbUserViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val usersPost : MutableLiveData<Resource<ArrayList<UsersPostsModel>>> = MutableLiveData()
    var usersPostsResponse : ArrayList<UsersPostsModel>? = null

    fun getUsersPosts() = viewModelScope.launch {
        usersPost.postValue(Resource.Loading())
        val response = repository.getUserPosts()
        usersPost.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<ArrayList<UsersPostsModel>>): Resource<ArrayList<UsersPostsModel>> {
        if (response.isSuccessful) {
            response.body()?.let {
                if (usersPostsResponse == null) {
                    usersPostsResponse = it
                } else {
                    val oldUsrPosts = usersPostsResponse
                    val newUsrPosts = it
                    oldUsrPosts?.addAll(newUsrPosts)
                }
                return Resource.Success(usersPostsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    fun getSavedData() : LiveData<List<UsersPostsModel>> {
        return repository.getDataFromDB()
    }

    fun saveDataToDb(usrData : List<UsersPostsModel>) = viewModelScope.launch {
        repository.saveToDB(usrData)
    }

}