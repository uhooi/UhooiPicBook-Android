package com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MonsterViewModel @Inject constructor(
    private val repository: MonstersRepository
) : ViewModel() {

    // region Stored Instance Properties

    private val _monsters = MutableLiveData<List<MonsterItem>>()
    val monsters: LiveData<List<MonsterItem>>
        get() = _monsters

    private val _selectedMonster = MutableLiveData<MonsterItem>()
    val selectedMonster: LiveData<MonsterItem>
        get() = _selectedMonster

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // endregion

    // region Initializers

    init {
        loadMonsters()
    }

    // endregion


    // region Other Internal Methods

    fun selectMonster(monster: MonsterItem) {
        _selectedMonster.value = monster
    }

    // region Other Private Methods

    private fun loadMonsters() {
        _isLoading.value = true
        this.repository.loadMonsters(
            onSuccess = { monsters ->
                _monsters.value = monsters
                _isLoading.value = false
            },
            onFailure = {
                // TODO: エラーハンドリング
            }
        )
    }

    // endregion

}
