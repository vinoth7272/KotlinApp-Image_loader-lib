package com.example.greedygameassesment.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.greedygameassesment.R
import com.example.greedygameassesment.model.ImageUrls
import com.example.imageloader.ImageLoader
import kotlinx.android.synthetic.main.second_activity.*

class FullImageActivity : AppCompatActivity() {

    private lateinit var mUrls: ImageUrls

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val bundle = intent.getBundleExtra("Bundle")
        mUrls = bundle.getParcelable<ImageUrls>("URLS") as ImageUrls
        mUrls.let {
            ImageLoader.with(this).load(mUrls.largeImageUrl, imageView2, progressBar)
        }

        btnCancel.setOnClickListener { v ->
            finish()
        }

    }
}