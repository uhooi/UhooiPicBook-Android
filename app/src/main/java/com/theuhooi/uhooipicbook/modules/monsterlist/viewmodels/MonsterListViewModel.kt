package com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.repository.monsters.firebase.MonstersFirestoreClient

class MonsterListViewModel : ViewModel() {

    // region Stored Instance Properties

    private val repository: MonstersRepository = MonstersFirestoreClient() // TODO: DIする

    private val _monsters = MutableLiveData<List<MonsterItem>>()
    val monsters: LiveData<List<MonsterItem>>
        get() = _monsters

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _selectedMonsterItemPosition = MutableLiveData<Int>()
    val selectedMonsterItemPosition: LiveData<Int>
        get() = _selectedMonsterItemPosition

    // endregion

    // region Initializers



    init {
        loadMonsters()
    }

    // endregion

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

    // region Other Public Methods

    fun setSelectedItemPosition(position: Int) {
        _selectedMonsterItemPosition.value = position
    }

    // endregion
}
