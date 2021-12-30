package com.theuhooi.uhooipicbook.ui.monsterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theuhooi.uhooipicbook.data.Result
import com.theuhooi.uhooipicbook.data.monsters.MonstersRepository
import com.theuhooi.uhooipicbook.domain.models.MonsterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MonsterUiState(
    val isLoading: Boolean = false,
    val monsterItems: List<MonsterItem> = listOf()
)

@HiltViewModel
class MonsterViewModel @Inject constructor(
    private val repository: MonstersRepository
) : ViewModel() {

    // region Stored Instance Properties

    private val _uiState = MutableStateFlow(MonsterUiState(isLoading = true))
    val uiState: StateFlow<MonsterUiState> = _uiState.asStateFlow()

    // endregion

    // region Initializers

    init {
        refreshMonsters()
    }

    // endregion

    // region Other Public Methods

    fun findMonster(order: Int): MonsterItem =
        requireNotNull(uiState.value.monsterItems.find { it.order == order })

    // endregion

    // region Other Private Methods

    private fun refreshMonsters() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.fetchMonsters()
            _uiState.update {
                when (result) {
                    is Result.Success -> it.copy(
                        monsterItems = result.data.map { dto -> MonsterItem.create(dto) },
                        isLoading = false
                    )
                    is Result.Error -> it.copy(isLoading = false) // TODO: エラーハンドリング
                }
            }
        }
    }

    // endregion

}
