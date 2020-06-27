package com.example.greedygameassesment.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygameassesment.R
import com.example.greedygameassesment.model.ApiImages
import com.example.greedygameassesment.model.BaseResponse
import com.example.greedygameassesment.model.BaseResult
import com.example.greedygameassesment.model.ImageUrls
import com.example.greedygameassesment.network.NetworkApi
import com.example.greedygameassesment.utils.AppUtils.Companion.getInstance
import com.example.greedygameassesment.viewmodel.ActivityViewModel
import com.example.greedygameassesment.viewmodel.DataRepository
import com.example.greedygameassesment.viewmodel.ViewModelFactory
import com.example.imageloader.ImageLoader
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mActivityViewModel: ActivityViewModel
    private val mNetworkApi: NetworkApi by inject()
    private var mApiImagesSourceHashMap = mutableMapOf<Int, ApiImages>()
    private var mImageUrlsList = arrayListOf<ImageUrls>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mActivityViewModel =
            ViewModelProviders.of(this, ViewModelFactory(DataRepository(mNetworkApi)))
                .get(ActivityViewModel::class.java)

        setAdapter()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImageLoader.with(applicationContext).clearCache()
    }

    private fun setAdapter() {
        mRecyclerViewAdapter = RecyclerViewAdapter(this,mApiImagesSourceHashMap,mImageUrlsList)
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = mRecyclerViewAdapter
    }

    private fun loadData() {
        progressBar.visibility = View.VISIBLE
        checkInternetAndCallApi()

        mActivityViewModel.baseResult.observe(this, Observer {
            when (it) {
                is BaseResult.Success -> {
                    loadDataIntoList(it.baseResponse)
                }
                is BaseResult.Failure -> {
                    showSnackMessage(it.error)
                }
            }
        })
    }

    /**
     * Method used to check the internet connection 1st then it call the api method
     */
    private fun checkInternetAndCallApi() {
        if (getInstance().isConnectedToNetwork(this))
            mActivityViewModel.getApiImages() // call view model api call method
        else {
            showSnackMessage(getString(R.string.network_error))
        }
    }

    /**
     * used to show snack bar with message
     * @param message to show in snack bar
     */
    private fun showSnackMessage(message: String) {
        Snackbar.make(
            parent_view.rootView, message,
            Snackbar.LENGTH_SHORT
        ).show()
        progressBar.visibility = View.GONE
    }


    /**
     * call when API response success
     */
    private fun loadDataIntoList(baseResponse: BaseResponse) {
        val childrenList = baseResponse.data.children
        childrenList.forEachIndexed { index, children ->
            mApiImagesSourceHashMap.put(index, children.childData.preview.images[0])
//            imageList.add(children.childData.thumnailUrl)
            mImageUrlsList.add(ImageUrls(children.childData.thumnailUrl,children.childData.largeUrl))
        }

        mRecyclerViewAdapter.setData(mApiImagesSourceHashMap,mImageUrlsList)
        mRecyclerViewAdapter.notifyDataSetChanged()
        showSnackMessage(getString(R.string.success_string))
    }
}
