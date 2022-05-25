package dev.sergiobelda.foundry

import android.app.Application
import dev.sergiobelda.foundry.di.databaseModule
import dev.sergiobelda.foundry.di.localDataSourceModule
import dev.sergiobelda.foundry.di.networkModule
import dev.sergiobelda.foundry.di.remoteDataSourceModule
import dev.sergiobelda.foundry.di.repositoryModule
import dev.sergiobelda.foundry.di.useCaseModule
import dev.sergiobelda.foundry.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FoundryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FoundryApplication)
            modules(
                databaseModule,
                localDataSourceModule,
                networkModule,
                remoteDataSourceModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
