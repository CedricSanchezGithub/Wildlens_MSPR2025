package com.wildlens.mspr_2025.data.repository


import com.wildlens.mspr_2025.data.api.WildlensETLApiService
import com.wildlens.mspr_2025.data.models.TriggerResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class WildlensETLRepositoryImplTest {

    private lateinit var apiService: WildlensETLApiService
    private lateinit var repository: WildlensETLRepositoryImpl

    @Before
    fun setup() {
        apiService = mock(WildlensETLApiService::class.java)
        repository = WildlensETLRepositoryImpl(apiService)
    }

    @Test
    fun `triggerETL should return success response when api call succeeds`() = runTest {
        // Arrange
        val expectedResponse = TriggerResponse(status = "OK", message = "ETL process triggered successfully")
        `when`(apiService.triggerETL()).thenReturn(expectedResponse)

        // Act
        val result = repository.triggerETL()

        // Assert
        verify(apiService).triggerETL()
        assertEquals(expectedResponse, result)
        assertEquals(true, result.status == "OK")
        assertEquals("ETL process triggered successfully", result.message)
    }

    @Test
    fun `triggerETL should handle HTTP error`() = runTest {
        // Arrange
        val httpException = mock(HttpException::class.java)
        `when`(apiService.triggerETL()).thenThrow(httpException)

        // Act & Assert
        try {
            repository.triggerETL()
            assert(false) { "Exception attendue mais non lancée" }
        } catch (e: HttpException) {
            // L'exception est bien propagée
        }
    }

    @Test
    fun `triggerETL should handle network error`() = runTest {
        // Arrange
        val ioException = IOException("Network error")
        `when`(apiService.triggerETL()).thenThrow(ioException)

        // Act & Assert
        try {
            repository.triggerETL()
            assert(false) { "Exception attendue mais non lancée" }
        } catch (e: IOException) {
            assertEquals("Network error", e.message)
        }
    }
}