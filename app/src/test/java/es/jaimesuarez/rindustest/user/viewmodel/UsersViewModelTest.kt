package es.jaimesuarez.rindustest.user.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.jaimesuarez.domain.model.AddressLocation
import es.jaimesuarez.domain.model.User
import es.jaimesuarez.domain.usecase.GetUserCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.user.model.UserDisplay
import es.jaimesuarez.rindustest.util.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class UsersViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val getUserCase: GetUserCase = mockk()

    @After
    fun after() = unmockkAll()

    @Test
    fun onLoadUsers() {
        // Given
        val result = Result.Success(listOf(User(id = 0, name = "", username = "", website = "", email = "", company = "", addressLocation = AddressLocation(lat = "", lng = ""))))
        val expected = listOf(UserDisplay(id = 0, name = "", username = "", website = "", email = "", company = ""))
        coEvery { getUserCase.get() } returns result

        // When
        val usersViewModel = UsersViewModel(getUserCase)

        // Then
        assertEquals(expected, usersViewModel.observeUsers().value)
    }
}