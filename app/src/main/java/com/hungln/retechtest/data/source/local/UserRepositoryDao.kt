package com.hungln.retechtest.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hungln.retechtest.data.network.response.RepositoryItem
import com.hungln.retechtest.data.network.response.RepositoryWithOwner
import kotlinx.coroutines.flow.Flow

@Dao
interface UserRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repositories: List<RepositoryItem>): List<Long>

    @Transaction
    @Query("""
        SELECT * FROM repositories 
        WHERE owner_id IN (
            SELECT id FROM owners WHERE login = :login
        )
    """)
    fun getRepositoriesWithOwner(login: String): Flow<List<RepositoryWithOwner>>

}