package com.theuhooi.uhooipicbook.data.monsters.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.theuhooi.uhooipicbook.data.monsters.MonstersRepository
import javax.inject.Inject

class MonstersFirestoreClient @Inject constructor() : MonstersRepository {

    // region Stored Instance Properties

    private val firestore = Firebase.firestore

    // endregion

    // region MonstersRepository

    override fun loadMonsters(
        onSuccess: (monsters: List<MonsterDto>) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) {
        firestore.collection("monsters")
            .orderBy(MonsterDto::order.name)
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
