package dev.xnative.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import dev.xnative.cleanrmapi.data.local.objects.EpisodeObject

@Dao
interface EpisodeDAO {

    @Query("select * from ${RMDatabase.EPISODE_TABLE}")
    fun getEpisodes(): Flow<List<EpisodeObject>>

    @Query("select * from ${RMDatabase.EPISODE_TABLE} where id = :id")
    suspend fun getEpisode(id: Int): EpisodeObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEpisodes(episodes: List<EpisodeObject>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: EpisodeObject)

}