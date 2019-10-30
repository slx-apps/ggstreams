package com.nlx.ggstreams.di.modules

import androidx.lifecycle.ViewModel
import com.nlx.ggstreams.di.ViewModelKey
import com.nlx.ggstreams.list.StreamListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StreamListViewModel::class)
    abstract fun bindStreamListViewModel(model: StreamListViewModel) : ViewModel


}