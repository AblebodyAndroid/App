package com.smilehunter.ablebody.data.di

import com.smilehunter.ablebody.data.repository.FCMSyncRepositoryImpl
import com.smilehunter.ablebody.data.repository.TokenRepositoryImpl
import com.smilehunter.ablebody.domain.repository.FCMSyncRepository
import com.smilehunter.ablebody.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSingletonModule {

    @Binds
    fun bindsFCMSyncRepository(
        fcmSyncRepositoryImpl: FCMSyncRepositoryImpl
    ): FCMSyncRepository

    @Binds
    fun bindsTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository
}