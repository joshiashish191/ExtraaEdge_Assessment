package net.softglobe.extraaedgeassessment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.softglobe.extraaedgeassessment.model.repository.RocketDetails
import net.softglobe.extraaedgeassessment.model.repository.RocketImages
import net.softglobe.extraaedgeassessment.model.repository.RocketsListDao

@Database(entities = [RocketDetails::class, RocketImages::class], version = 1)
abstract class RocketsDatabase : RoomDatabase() {
    abstract fun rocketsListDao() :RocketsListDao

    companion object {
        @Volatile
        private var instance :RocketsDatabase? = null
        fun getDatabase(context: Context) = instance ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, RocketsDatabase::class.java, "rockets_db.db").build()
                .also { instance = it }
        }
    }
}