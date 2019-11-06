package es.jaimesuarez.rindustest.album.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import es.jaimesuarez.rindustest.R
import es.jaimesuarez.rindustest.album.adapter.PhotoAdapter
import es.jaimesuarez.rindustest.album.model.PhotoDisplay
import es.jaimesuarez.rindustest.album.viewmodel.AlbumDetailViewModel
import es.jaimesuarez.rindustest.common.fragment.BaseFragment
import es.jaimesuarez.rindustest.common.util.Visibility
import es.jaimesuarez.rindustest.common.util.show
import es.jaimesuarez.rindustest.common.util.showToast
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class AlbumDetailFragment : BaseFragment<AlbumDetailViewModel>() {

    override val viewModel: AlbumDetailViewModel by viewModel()

    private val photosAdapter = PhotoAdapter {
        showToast(getString(R.string.feature_not_implemented))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_album_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }

    private fun setUi() {
        rv_albumDetail_photos.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = photosAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.observePhotos().observe(viewLifecycleOwner, Observer { showPhotos(it) })
        viewModel.observePhotosLoader().observe(viewLifecycleOwner, Observer {
            pb_albumDetail_loader.show = it == Visibility.Visible
        })
    }

    private fun showPhotos(photos: List<PhotoDisplay>) = photosAdapter.setPhotos(photos)
}
