package es.jaimesuarez.rindustest.album.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.jaimesuarez.domain.usecase.GetPhotosByAlbumCase
import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.rindustest.album.fragment.AlbumDetailFragmentArgs
import es.jaimesuarez.rindustest.album.model.AlbumDisplay
import es.jaimesuarez.rindustest.album.model.PhotoDisplay
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val getPhotosByAlbumCase: GetPhotosByAlbumCase
) : BaseViewModel(), CoroutineScope by MainScope() {

    private var album: AlbumDisplay? = null

    private val photos = MutableLiveData<List<PhotoDisplay>>()
    fun observePhotos(): LiveData<List<PhotoDisplay>> = photos

    private val photosLoader = MutableLiveData<Visibility>(Visibility.Visible)
    fun observePhotosLoader(): LiveData<Visibility> = photosLoader

    init {
        launch {
            readArguments()
            loadItems()
        }
    }

    private suspend fun loadItems() {
        album?.id?.let {
            when (val result = getPhotosByAlbumCase.get(it)) {
                is Result.Success -> photos.value = result.data.map { PhotoDisplay(it) }
                is Result.Failure -> notifyError(result.error)
            }
            photosLoader.value = Visibility.Hidden
        }
    }

    private fun readArguments() = arguments?.let {
        album = AlbumDetailFragmentArgs.fromBundle(it).album
    }
}
