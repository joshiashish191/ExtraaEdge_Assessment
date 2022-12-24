package net.softglobe.extraaedgeassessment.model.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RocketsListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRocketDetails(rocketDetails: RocketDetails)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRocketImages(rocketImages: RocketImages)

    @Query("SELECT * FROM rocket")
    suspend fun getRocketsList() : List<RocketDetails>

    @Query("SELECT * FROM images WHERE id=:id")
    suspend fun getRocketImages(id : String) : List<RocketImages>

    @Query("SELECT * FROM rocket WHERE id=:id")
    suspend fun getRocketDetailsById(id : String) : RocketDetails

}