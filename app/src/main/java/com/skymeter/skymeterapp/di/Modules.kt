package com.skymeter.skymeterapp.di

import androidx.room.Room
import com.mmnaguib.skymeterapp.utils.Constants
import com.skymeter.skymeterapp.data.room.RoomManager
import com.skymeter.skymeterapp.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module


@KoinApiExtension
object Modules {


    val modules = module {


        single {
            Room.databaseBuilder(
                get(),
                RoomManager::class.java,
                Constants.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        viewModel { HomeViewModel(get()) }


    }


}