package com.example.testassessment

import com.example.testassessment.roomdb.AlbumEntity
import com.example.testassessment.roomdb.PhotosEntity
import com.example.testassessment.roomdb.RoomDao
import com.example.testassessment.roomdb.UserEntity
import com.example.testassessment.ui.home.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.mockito.Mockito
import org.junit.After
import org.junit.Assert.*

class HomeViewModelTest {

    @Mock
    lateinit var roomDao: RoomDao
    private lateinit var homeViewModel: HomeViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        homeViewModel = HomeViewModel(roomDao)
    }

    @Test
    fun testFetchPhotosSuccess() = runBlocking {

        val photosList = listOf(
            PhotosEntity(1,
                1,
                "accusamus beatae ad facilis cum similique qui sunt",
                "https://via.placeholder.com/600/92c952",
                "https://via.placeholder.com/150/92c952")
        )
        Mockito.`when`(roomDao.getAllPhotos()).thenReturn(flowOf(photosList))
        homeViewModel.fetchPhotosFromRoom()
        assertEquals(photosList, homeViewModel.photos.value)
    }

    @Test
    fun testFetchPhotosError() = runBlocking {

        val exception = RuntimeException("Database error")
        Mockito.`when`(roomDao.getAllPhotos()).thenThrow(exception)


        homeViewModel.fetchPhotosFromRoom()
        assertEquals(emptyList<PhotosEntity>(), homeViewModel.photos.value)
    }

    @Test
    fun testFetchAlbumsSuccess() = runBlocking {

        val albumsList = listOf(
            AlbumEntity(1,
                1,
                "quidem molestiae enim")
        )
        Mockito.`when`(roomDao.getAllAlbums()).thenReturn(flowOf(albumsList))
        homeViewModel.fetchAlbumsFromRoom()
        assertEquals(albumsList, homeViewModel.albums.value)
    }

    @Test
    fun testFetchAlbumsError() = runBlocking {

        val exception = RuntimeException("Database error")
        Mockito.`when`(roomDao.getAllAlbums()).thenThrow(exception)


        homeViewModel.fetchAlbumsFromRoom()
        assertEquals(emptyList<AlbumEntity>(), homeViewModel.albums.value)
    }

    @Test
    fun testFetchUsersSuccess() = runBlocking {

        val userList = listOf(
            UserEntity(1,
                "Leanne Graham")
        )

        Mockito.`when`(roomDao.getAllUsers()).thenReturn(flowOf(userList))
        homeViewModel.fetchUsersFromRoom()
        assertEquals(userList, homeViewModel.users.value)
    }

    @Test
    fun testFetchUsersError() = runBlocking {

        val exception = RuntimeException("Database error")
        Mockito.`when`(roomDao.getAllUsers()).thenThrow(exception)
        homeViewModel.fetchUsersFromRoom()
        assertEquals(emptyList<UserEntity>(), homeViewModel.users.value)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
