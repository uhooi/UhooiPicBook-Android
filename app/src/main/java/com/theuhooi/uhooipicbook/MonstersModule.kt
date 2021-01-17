package com.theuhooi.uhooipicbook

import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.repository.monsters.firebase.MonstersFirestoreClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class MonstersModule {
    @Singleton
    @Binds
    abstract fun bindMonstersRepository(monstersFirestoreClient: MonstersFirestoreClient) : MonstersRepository
}
