package net.softglobe.extraaedgeassessment.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.softglobe.extraaedgeassessment.R
import net.softglobe.extraaedgeassessment.model.RocketsListAdapter
import net.softglobe.extraaedgeassessment.viewmodel.RocketsDataViewModel

class RocketsListFragment : Fragment() {
    val TAG = "RocketsListFragment"
    lateinit var viewModel: RocketsDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =ViewModelProvider(this).get(RocketsDataViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rockets_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading : ProgressBar? = view.findViewById(R.id.progress_bar)
        loading?.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val rocketsList = viewModel.getRocketsList()
                val rocketsRecyclerView = view.findViewById<RecyclerView>(R.id.rockets_list_recyclerview)?.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = RocketsListAdapter(){
                        val bundle = Bundle()
                        bundle.putString("ID", it.id)
                        findNavController().navigate(R.id.action_rocketsListFragment_to_rocketDetailsFragment, bundle)
                    }
                    setHasFixedSize(true)
                }
                (rocketsRecyclerView?.adapter as RocketsListAdapter).submitList(rocketsList)

//                val rocketImagesList = mutableListOf<RocketImages>()
//                rocketsList?.forEach { rocket ->
//                    rocket.flickr_images.forEach { rocketImage ->
//                        rocketImagesList.add(RocketImages(rocket.id, rocketImage))
//                    }
//                }
            } catch (e : Exception){
                Toast.makeText(activity, "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Error in retrieving rockets list: $e")
            } finally {
                loading?.visibility = View.GONE
            }
        }
    }

}