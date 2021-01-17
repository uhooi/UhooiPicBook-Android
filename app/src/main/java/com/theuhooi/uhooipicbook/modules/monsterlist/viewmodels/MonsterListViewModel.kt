package com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.repository.monsters.firebase.MonstersFirestoreClient

class MonsterListViewModel @ViewModelInject constructor(
    private val repository: MonstersRepository
) : ViewModel() {

    // region Stored Instance Properties

    private val _monsters = MutableLiveData<List<MonsterItem>>()
    val monsters: LiveData<List<MonsterItem>>
        get() = _monsters

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

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

}
