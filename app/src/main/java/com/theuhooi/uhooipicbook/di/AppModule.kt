package com.theuhooi.uhooipicbook.di

import com.theuhooi.uhooipicbook.data.monsters.MonstersRepository
import com.theuhooi.uhooipicbook.data.monsters.impl.MonstersFirestoreClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMonstersRepository(): MonstersRepository = MonstersFirestoreClient()
}
