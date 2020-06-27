package com.example.greedygameassesment.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygameassesment.R
import com.example.greedygameassesment.model.ApiImages
import com.example.greedygameassesment.model.ImageUrls
import com.example.imageloader.ImageLoader
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerViewAdapter(
    val activity : MainActivity,
    var apiImagesSourceHashMap: MutableMap<Int, ApiImages>,
    private var mImageUrls: ArrayList<ImageUrls>
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder>() {

    fun setData(apiImagesSourceHashMap: MutableMap<Int, ApiImages>, imageUrls: ArrayList<ImageUrls>) {
        this.apiImagesSourceHashMap = apiImagesSourceHashMap
        this.mImageUrls = imageUrls
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return apiImagesSourceHashMap.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(apiImagesSourceHashMap[position], mImageUrls[position],activity)
        holder.itemView.setOnClickListener { v ->
            val intent :Intent = Intent(activity,FullImageActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("URLS",mImageUrls[position] as Parcelable)
            intent.putExtra("Bundle",bundle)
            activity.startActivity(intent)
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            apiImages: ApiImages?,
            urls: ImageUrls,
            activity: MainActivity
        ) {
            itemView.textView.visibility = View.GONE
//            ImageLoader.displayImage(url, itemView.imageView1)
            ImageLoader.with(activity).load(urls.thumbnailUrl,itemView.imageView1)
//            itemView.textView.text = apiImages?.source?.url
        }

    }
}
