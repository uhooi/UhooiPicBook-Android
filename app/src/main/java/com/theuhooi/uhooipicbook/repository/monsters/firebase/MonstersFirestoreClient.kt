package com.theuhooi.uhooipicbook.repository.monsters.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.modules.monsterlist.entities.MonsterItem
import javax.inject.Inject

class MonstersFirestoreClient @Inject constructor() : MonstersRepository {

    // region Stored Instance Properties

    private val firestore = Firebase.firestore

    // endregion

    // region MonstersRepository

    override fun loadMonsters(
        onSuccess: (monsters: List<MonsterItem>) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) {
        this.firestore.collection("monsters")
            .orderBy(MonsterItem::order.name)
            .get()
            .addOnSuccessListener { result ->
                onSuccess(result.toObjects())
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // endregion

}
