package es.jaimesuarez.rindustest.di

import es.jaimesuarez.rindustest.album.viewmodel.AlbumDetailViewModel
import es.jaimesuarez.rindustest.common.viewmodel.ItemListViewModel
import es.jaimesuarez.rindustest.post.viewmodel.PostDetailViewModel
import es.jaimesuarez.rindustest.todo.viewmodel.CreateTodoViewModel
import es.jaimesuarez.rindustest.todo.viewmodel.TodoListViewModel
import es.jaimesuarez.rindustest.user.viewmodel.UserDetailViewModel
import es.jaimesuarez.rindustest.user.viewmodel.UsersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // ViewModels
    viewModel { UsersViewModel(get()) }
    viewModel { UserDetailViewModel(get(), get(), get()) }
    viewModel { ItemListViewModel(get(), get()) }
    viewModel { PostDetailViewModel(get()) }
    viewModel { AlbumDetailViewModel(get()) }
    viewModel { CreateTodoViewModel(get()) }
    viewModel { TodoListViewModel(get(), get()) }
}