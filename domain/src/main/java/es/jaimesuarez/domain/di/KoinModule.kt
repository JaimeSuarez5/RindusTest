package es.jaimesuarez.domain.di

import es.jaimesuarez.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    // Use cases
    single { GetUserCase(get()) }
    single { GetAlbumsByUserCase(get()) }
    single { GetPostsByUserCase(get()) }
    single { GetTodosByUserCase(get()) }
    single { GetCommentsByPostCase(get()) }
    single { GetPhotosByAlbumCase(get()) }
    single { CreateTodoCase(get()) }
    single { DeleteTodoCase(get()) }
}