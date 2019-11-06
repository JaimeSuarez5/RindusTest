package es.jaimesuarez.rindustest

import android.app.Application
import es.jaimesuarez.data.di.networkModule
import es.jaimesuarez.domain.di.domainModule
import es.jaimesuarez.rindustest.di.appModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule, domainModule, networkModule))
        }
    }
}
