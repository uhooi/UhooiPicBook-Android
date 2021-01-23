package com.theuhooi.uhooipicbook.di

import com.theuhooi.uhooipicbook.modules.monsterlist.MonstersRepository
import com.theuhooi.uhooipicbook.repository.monsters.firebase.MonstersFirestoreClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindMonstersRepository(monstersFirestoreClient: MonstersFirestoreClient): MonstersRepository
}
