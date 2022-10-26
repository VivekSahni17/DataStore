package com.viveksahani.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.coroutineScope
import com.viveksahani.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var model: UserDetailsModel
    lateinit var mBinding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        model = UserDetailsModel(this)

        mBinding.saveData.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                model.storeUser(
                    mBinding.enterName.text.toString().trim(),
                    mBinding.enterAge.text.toString().trim().toInt()
                )
            }
        }

        lifecycle.coroutineScope.launchWhenCreated {
            model.getUserAge().collect {
                mBinding.age.text = it.toString()
            }

        }

        lifecycle.coroutineScope.launchWhenCreated {
            model.getUserName().collect {
                mBinding.name.text = it.toString()
            }

        }

        mBinding.clearText.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                model.clear()
            }
        }


    }
}