package com.theuhooi.uhooipicbook.modules.monsterlist.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import com.theuhooi.uhooipicbook.repository.monsters.firebase.MonstersFirestoreClient

class MonsterListViewModel : ViewModel() {

    // region Stored Instance Properties

    private val repository: MonstersRepository = MonstersFirestoreClient()

    private val _monsters = MutableLiveData<List<MonsterItem>>()
    val monsters: LiveData<List<MonsterItem>>
        get() = _monsters

    val isLoading = MutableLiveData(false)

    // endregion

    // region Initializers

    init {
        loadMonsters()
    }

    // endregion

    // region Other Private Methods

    private fun loadMonsters() {
        isLoading.value = true
        repository.loadMonsters(
            onSuccess = { monsters ->
                _monsters.value = monsters
                isLoading.value = false
            },
            onFailure = {
                // TODO: エラーハンドリング
            }
        )
    }

    // endregion

}
