package es.jaimesuarez.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import es.jaimesuarez.data.BuildConfig
import es.jaimesuarez.data.datasource.local.LocalCacheDatasource
import es.jaimesuarez.data.datasource.remote.AlbumRemoteDatasource
import es.jaimesuarez.data.datasource.remote.PostRemoteDatasource
import es.jaimesuarez.data.datasource.remote.TodoRemoteDatasource
import es.jaimesuarez.data.datasource.remote.UserRemoteDatasource
import es.jaimesuarez.data.datasource.remote.service.AlbumService
import es.jaimesuarez.data.datasource.remote.service.PostService
import es.jaimesuarez.data.datasource.remote.service.TodoService
import es.jaimesuarez.data.datasource.remote.service.UserService
import es.jaimesuarez.data.repository.UserRepositoryImpl
import es.jaimesuarez.domain.model.Album
import es.jaimesuarez.domain.model.Photo
import es.jaimesuarez.domain.model.Post
import es.jaimesuarez.domain.model.Todo
import es.jaimesuarez.domain.repository.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    // Repositories
    single {
        UserRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(named("AlbumCache")),
            get(named("TodoCache")),
            get(named("PostCache")),
            get(named("PhotoCache"))
        ) as UserRepository
    }

    // Datasources
    single { UserRemoteDatasource(get()) }
    single { PostRemoteDatasource(get()) }
    single { AlbumRemoteDatasource(get()) }
    single { TodoRemoteDatasource(get()) }

    single(named("AlbumCache")) { LocalCacheDatasource<List<Album>>() }
    single(named("TodoCache")) { LocalCacheDatasource<List<Todo>>() }
    single(named("PostCache")) { LocalCacheDatasource<List<Post>>() }
    single(named("PhotoCache")) { LocalCacheDatasource<List<Photo>>() }

    // Services
    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(PostService::class.java) }
    single { get<Retrofit>().create(AlbumService::class.java) }
    single { get<Retrofit>().create(TodoService::class.java) }

    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}