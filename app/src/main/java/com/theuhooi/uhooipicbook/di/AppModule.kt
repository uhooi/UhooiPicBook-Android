package com.theuhooi.uhooipicbook.di

import com.theuhooi.uhooipicbook.data.monsters.MonstersRepository
import com.theuhooi.uhooipicbook.data.monsters.impl.MonstersFirestoreClient
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
