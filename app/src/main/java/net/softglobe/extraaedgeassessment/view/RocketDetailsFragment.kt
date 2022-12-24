package net.softglobe.extraaedgeassessment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.launch
import net.softglobe.extraaedgeassessment.R
import net.softglobe.extraaedgeassessment.model.RocketImagesListAdapter
import net.softglobe.extraaedgeassessment.viewmodel.RocketsDataViewModel

class RocketDetailsFragment : Fragment() {
    var TAG = "RocketDetailsFragment"
    var rocketId: String? = null
    lateinit var viewmodel : RocketsDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider(this).get(RocketsDataViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val id = bundle?.getString("ID")
        if (id != null) {
            rocketId = id
        }
        lifecycleScope.launch {
            val rocketDetails = rocketId?.let { viewmodel.getRocketsDataById(it) }
            rocketDetails?.let {
                view.findViewById<TextView>(R.id.rocket_name).text = it.name
                if (it.active)
                    view.findViewById<TextView>(R.id.active).text = "Status: Active"
                else
                    view.findViewById<TextView>(R.id.active).text = "Status: InActive"
                view.findViewById<TextView>(R.id.cost_per_launch).text = "Cost Per Launch: ${it.cost_per_launch.toString()}"
                view.findViewById<TextView>(R.id.success_rate).text = "Success Rate: ${it.success_rate_pct}%"
                view.findViewById<TextView>(R.id.description).text = "Description: ${it.description}"
                view.findViewById<TextView>(R.id.wiki_link).text = "Wikipedia: ${it.wikipedia}"
                view.findViewById<TextView>(R.id.height).text = "Height: ${it.height} feet"
                view.findViewById<TextView>(R.id.diameter).text = "Diameter: ${it.diameter} feet"
                val rocketImagesRecyclerView = view.findViewById<RecyclerView>(R.id.rocket_images_recyclerview)?.apply {
                    layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL)
                    adapter = RocketImagesListAdapter {}
                    setHasFixedSize(true)
                }
                if (rocketId != null) {
                    val rocketImagesList = viewmodel.getRocketImagesById(rocketId.toString())
                    Log.d(TAG, "rocketImagesList: $rocketImagesList")
                    (rocketImagesRecyclerView?.adapter as RocketImagesListAdapter).submitList(rocketImagesList)
                }
            }
        }
    }
}