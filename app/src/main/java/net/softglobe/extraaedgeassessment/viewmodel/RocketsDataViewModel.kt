package net.softglobe.extraaedgeassessment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import net.softglobe.extraaedgeassessment.data.Diameter
import net.softglobe.extraaedgeassessment.data.Engines
import net.softglobe.extraaedgeassessment.data.Height
import net.softglobe.extraaedgeassessment.data.Rocket
import net.softglobe.extraaedgeassessment.model.repository.DataSource
import net.softglobe.extraaedgeassessment.model.repository.RocketDetails
import net.softglobe.extraaedgeassessment.model.repository.RocketImages

class RocketsDataViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "RocketsDataViewModel"
    private val dataSource by lazy { DataSource(application) }

    private suspend fun addToDatabase(rocketsList: List<Rocket>?) {
        if (rocketsList != null) {
            dataSource.addRocketDetailsToDatabase(rocketsList)
        }

        rocketsList?.forEach { rocket ->
            rocket.flickr_images.forEach { rocketImage ->
                dataSource.insertRocketImages(RocketImages(rocket.id, rocketImage))
            }
        }
    }

    //Function to get rockets list either from database or from network
    suspend fun getRocketsList(): List<Rocket>? {
        val rocketDetailsList = dataSource.getRocketsList()
        return if (rocketDetailsList.isNotEmpty()) {
            Log.i(TAG, "List retrieved from database")
            convertRocketDetailsToRocket(rocketDetailsList)
        } else {
            val rocketsList = dataSource.getRocketsListFromNetwork().body()
            addToDatabase(rocketsList)
            Log.i(TAG, "List retrieved from network")
            rocketsList
        }
    }

    suspend fun getRocketsDataById(id: String): RocketDetails {
        return dataSource.getRocketDetailsById(id)
    }

    suspend fun getRocketImagesById(id: String): List<RocketImages> {
        return dataSource.getRocketImages(id)
    }

    //Convert object RocketDetails to Rocket
    private suspend fun convertRocketDetailsToRocket(rocketDetailsList: List<RocketDetails>): List<Rocket> {
        val rocketsList = mutableListOf<Rocket>()
        rocketDetailsList.forEach { rocketDetails ->
            val rocketImagesList = dataSource.getRocketImages(rocketDetails.id)
            val mutableRocketImagesList = mutableListOf<String>()
            mutableRocketImagesList.add(rocketImagesList[0].flickr_image)

            rocketsList.add(
                Rocket(
                    rocketDetails.active,
                    rocketDetails.cost_per_launch,
                    rocketDetails.country,
                    rocketDetails.description,
                    Diameter(rocketDetails.diameter),
                    Engines(rocketDetails.engines),
                    mutableRocketImagesList,
                    Height(rocketDetails.height),
                    rocketDetails.id,
                    rocketDetails.name,
                    rocketDetails.success_rate_pct,
                    rocketDetails.wikipedia
                )
            )
        }
        return rocketsList
    }
}