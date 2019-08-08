package com.nlx.ggstreams.di.modules

import com.nlx.ggstreams.di.PerApp
import com.nlx.ggstreams.list.data.StreamListRepository
import com.nlx.ggstreams.list.data.StreamRepository
import com.nlx.ggstreams.rest.GGApi
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    @PerApp
    fun provideStreamRepository(api: GGApi): StreamRepository {
        return StreamListRepository(api)
    }

}