package com.example.mongodbandroid.ui.home

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.model.Plant
import com.example.mongodbandroid.R
import com.example.mongodbandroid.databinding.ActivityMainBinding
import com.example.other.Status
import dagger.hilt.android.AndroidEntryPoint
import org.bson.types.ObjectId

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val plantList = mutableListOf<Plant>()

        activityMainBinding.insertManySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activityMainBinding.addDoc.visibility = VISIBLE
                activityMainBinding.addDoc.text =
                    resources.getString(R.string.insert_mul_text, plantList.size)
            } else {
                activityMainBinding.addDoc.visibility = GONE
                if (plantList.isNotEmpty()) {
                    plantList.clear()
                    Toast.makeText(this, "Item list cleared", Toast.LENGTH_SHORT).show()
                }
            }
        }

        activityMainBinding.uploadDoc.setOnClickListener {
            if (plantList.isEmpty())
                homeViewModel.insertOneDocument(getPlantDetailsInput())
            else {
                homeViewModel.insertMultipleDocuments(plantList)
                plantList.clear()
                activityMainBinding.addDoc.text =
                    resources.getString(R.string.insert_mul_text, plantList.size)
            }
        }

        activityMainBinding.addDoc.setOnClickListener {
            val plant = getPlantDetailsInput()
            plantList.add(plant)
            activityMainBinding.addDoc.text =
                resources.getString(R.string.insert_mul_text, plantList.size)
            clearFields()
        }

        homeViewModel.getInfo.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    /* no operation */
                }
                Status.SUCCESS -> {
                    Toast.makeText(this, it.data, Toast.LENGTH_LONG).show()
                    clearFields()
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun clearFields() {
        activityMainBinding.plantNameEditText.text?.clear()
        activityMainBinding.plantSunLightEditText.text?.clear()
        activityMainBinding.plantColorEditText.text?.clear()
        activityMainBinding.plantTypeEditText.text?.clear()
        activityMainBinding.plantPartitionEditText.text?.clear()
    }

    private fun getPlantDetailsInput() = Plant(
        ObjectId(),
        activityMainBinding.plantNameEditText.text.toString(),
        activityMainBinding.plantSunLightEditText.text.toString(),
        activityMainBinding.plantColorEditText.text.toString(),
        activityMainBinding.plantTypeEditText.text.toString(),
        activityMainBinding.plantPartitionEditText.text.toString()
    )
}