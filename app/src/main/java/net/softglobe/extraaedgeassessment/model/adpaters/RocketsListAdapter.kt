package net.softglobe.extraaedgeassessment.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.softglobe.extraaedgeassessment.R
import net.softglobe.extraaedgeassessment.data.Rocket

class RocketsListAdapter(private val listener : (Rocket) -> Unit) : ListAdapter<Rocket, RocketsListAdapter.ViewHolder>(DiffRocketCallBack()) {
    inner class ViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView){
        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition))
            }
        }

        fun bind(rocket : Rocket){
            containerView.findViewById<TextView>(R.id.rocket_name).text = rocket.name
            containerView.findViewById<TextView>(R.id.rocket_country).text = rocket.country
            containerView.findViewById<TextView>(R.id.rocket_engines_count).text = rocket.engines.number.toString()
            Glide.with(containerView)
                .load(rocket.flickr_images[0])
                .centerCrop()
                .into(containerView.findViewById(R.id.rocket_image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.rocket_list_item, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffRocketCallBack : DiffUtil.ItemCallback<Rocket>(){
    override fun areItemsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
        return oldItem == newItem
    }

}