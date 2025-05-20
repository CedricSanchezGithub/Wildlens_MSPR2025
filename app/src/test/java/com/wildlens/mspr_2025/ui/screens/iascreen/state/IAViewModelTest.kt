package com.wildlens.mspr_2025.ui.screens.iascreen.state

import com.wildlens.mspr_2025.data.repository.WildlensETLRepository
import com.wildlens.mspr_2025.data.repository.WildlensMetaDataRepository
import com.wildlens.mspr_2025.data.models.TriggerResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IAViewModelTest {

    private val fakeETLRepository = mockk<WildlensETLRepository>()
    private val fakeMetaRepository = mockk<WildlensMetaDataRepository>()
    private lateinit var viewModel: IAViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = IAViewModel(fakeETLRepository, fakeMetaRepository)
    }

    @Test
    fun `triggerETL - success - updates to Success state`() = runTest {
        val response = TriggerResponse(message = "ETL trigger", status = "OK")
        coEvery { fakeETLRepository.triggerETL() } returns response

        viewModel.triggerETL()

        advanceUntilIdle()

        assertEquals(
            IAState.Success("ETL trigger"),
            viewModel.uiState.value
        )
    }

    @Test
    fun `triggerETL - failure - updates to Error state`() = runTest {
        coEvery { fakeETLRepository.triggerETL() } throws RuntimeException("Erreur ETL")

        viewModel.triggerETL()

        advanceUntilIdle()

        val result = viewModel.uiState.value
        assertTrue(result is IAState.Error)
        assertTrue((result as IAState.Error).message.contains("Erreur ETL"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `triggermetadata - success - updates to Success state`() = runTest {
        val response = TriggerResponse(message = "Metadata chargée", status = "OK")
        coEvery { fakeMetaRepository.triggermetadata() } returns response

        viewModel.triggermetadata()

        advanceUntilIdle()

        assertEquals(
            IAState.Success("Metadata chargée"),
            viewModel.uiState.value
        )
    }

    @Test
    fun `triggermetadata - failure - updates to Error state`() = runTest {
        coEvery { fakeMetaRepository.triggermetadata() } throws RuntimeException("Erreur metadata")

        viewModel.triggermetadata()

        advanceUntilIdle()

        val result = viewModel.uiState.value
        assertTrue(result is IAState.Error)
        val error = result as IAState.Error
        assertTrue(error.message.contains("Erreur metadata"))
    }


}


