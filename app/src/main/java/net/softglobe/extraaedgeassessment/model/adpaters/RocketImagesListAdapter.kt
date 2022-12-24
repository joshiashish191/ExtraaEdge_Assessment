package net.softglobe.extraaedgeassessment.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.softglobe.extraaedgeassessment.R
import net.softglobe.extraaedgeassessment.model.repository.RocketImages

class RocketImagesListAdapter(private val listener : (RocketImages) -> Unit) :
    ListAdapter<RocketImages, RocketImagesListAdapter.ViewHolder>(DiffImagesCallBack()){
    val TAG = "RocketImagesListAdapter"
        inner class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
            init {
                itemView.setOnClickListener {
                    listener.invoke(getItem(adapterPosition))
                }
            }

            fun bind(rocketImage: RocketImages){
                try {
                    Glide.with(containerView)
                        .load(rocketImage.flickr_image)
                        .centerCrop()
                        .into(containerView.findViewById(R.id.rocket_image))
                } catch (e : Exception){
                    Log.e(TAG, "Error in bindView image: $e")
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.rocket_image_list_item, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffImagesCallBack : DiffUtil.ItemCallback<RocketImages>(){
    override fun areItemsTheSame(oldItem: RocketImages, newItem: RocketImages): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RocketImages, newItem: RocketImages): Boolean {
        return oldItem == newItem
    }

}