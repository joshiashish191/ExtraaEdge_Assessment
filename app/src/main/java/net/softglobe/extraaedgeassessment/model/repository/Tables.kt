package net.softglobe.extraaedgeassessment.model.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rocket")
data class RocketDetails(
    @PrimaryKey(autoGenerate = false) val id: String,
    val active: Boolean,
    val cost_per_launch: Int,
    val country: String,
    val description: String,
    val diameter: Double,
    val engines: Int,
    val height: Double,
    val name: String,
    val success_rate_pct: Int,
    val wikipedia: String
)

@Entity(tableName = "images")
data class RocketImages(
    val id: String,
    val flickr_image: String
){
    @PrimaryKey(autoGenerate = true)
    var temp_id : Int = 0
}
