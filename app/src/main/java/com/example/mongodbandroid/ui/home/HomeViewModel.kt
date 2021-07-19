package com.example.mongodbandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.Plant
import com.example.other.Resource
import com.example.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.bson.types.ObjectId
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _getStatus = MutableLiveData<Resource<String>>()
    val getInfo: LiveData<Resource<String>> get() = _getStatus

    fun insertOneDocument(plant: Plant) {
        _getStatus.postValue(Resource.loading(null))
        repository.insertOne(plant).getAsync {
            if (it.isSuccess) {
                println("Item uploaded Successfully")
                _getStatus.postValue(Resource.success("Item uploaded Successfully"))
            } else {
                println("error${it.error}")
                _getStatus.postValue(Resource.error("Something went wrong. Please try again", null))
            }
        }
    }

    fun insertMultipleDocuments(plantList: List<Plant>) {
        _getStatus.postValue(Resource.loading(null))
        repository.insertMany(plantList).getAsync {
            if (it.isSuccess) {
                println("Item list uploaded Successfully")
                _getStatus.postValue(Resource.success("Item list uploaded Successfully"))
            } else {
                println("error${it.error}")
                _getStatus.postValue(Resource.error("Something went wrong. Please try again", null))
            }
        }
    }
}