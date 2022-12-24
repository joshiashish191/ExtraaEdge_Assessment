package net.softglobe.extraaedgeassessment.model.repository

import android.app.Application
import android.util.Log
import net.softglobe.extraaedgeassessment.data.Rocket
import net.softglobe.extraaedgeassessment.data.RocketsDatabase
import net.softglobe.extraaedgeassessment.model.network.RetrofitInterface
import retrofit2.Response

class DataSource(context: Application) {
    val TAG = "DataSource"
    private val rocketsListDao : RocketsListDao by lazy { RocketsDatabase.getDatabase(context).rocketsListDao() }

    /* region Data operations from Room Database*/
    suspend fun getRocketsList() : List<RocketDetails> {
        return rocketsListDao.getRocketsList()
    }

    suspend fun getRocketImages(id : String) : List<RocketImages> {
        val temp = rocketsListDao.getRocketImages(id)
        Log.d(TAG, "getRocketImages: $temp")
        return temp
    }

    suspend fun getRocketDetailsById(id : String) : RocketDetails {
        return rocketsListDao.getRocketDetailsById(id)
    }

    suspend fun insertRocketDetails(rocketDetails: RocketDetails) {
        rocketsListDao.insertRocketDetails(rocketDetails)
    }

    suspend fun insertRocketImages(rocketImages: RocketImages) {
        rocketsListDao.insertRocketImages(rocketImages)
    }

    suspend fun addRocketDetailsToDatabase(rocketsList: List<Rocket>) {
        rocketsList.forEach { rocket ->
            val rocketDetails = RocketDetails(
                rocket.id,
                rocket.active,
                rocket.cost_per_launch,
                rocket.country,
                rocket.description,
                rocket.diameter.feet,
                rocket.engines.number,
                rocket.height.feet,
                rocket.name,
                rocket.success_rate_pct,
                rocket.wikipedia
            )
            insertRocketDetails(rocketDetails)
        }
    }
    // endregion

    // region Data operations from Network
    suspend fun getRocketsListFromNetwork() : Response<List<Rocket>> {
        return RetrofitInterface.api.getRocketsList()
    }
    //endregion
}