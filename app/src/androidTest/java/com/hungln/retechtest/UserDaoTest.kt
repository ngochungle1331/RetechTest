package com.hungln.retechtest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hungln.retechtest.data.network.response.User
import com.hungln.retechtest.data.source.local.AppLocalDatabase
import com.hungln.retechtest.data.source.local.UserDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var database: AppLocalDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppLocalDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testTotalAmountOfUsers() = runBlocking {
        // Given
        val user1 = User(login = "user1")
        val user2 = User(login = "user2")
        userDao.insertAllUsers(listOf(user1, user2))

        // When
        val users = userDao.getAllUsers()

        // Then
        assertEquals(2, users.count())
    }

    @Test
    fun testUserFollowerCount() = runBlocking {
        // Given
        val user = User(login = "user1", followers = 100)
        userDao.insertUser(user)

        // When
        val retrievedUser = userDao.getUserByLogin("user1")

        // Then
        assertEquals(100, retrievedUser?.followers)
    }

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

}
