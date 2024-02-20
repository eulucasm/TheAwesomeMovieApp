package com.lucao.theawesomemovieapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.bumptech.glide.Glide
import com.lucao.theawesomemovieapp.ui.viewmodel.MovieViewModel
import com.lucao.theawesomemovieapp.R
import com.lucao.theawesomemovieapp.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel by hiltNavGraphViewModels<MovieViewModel>(R.id.movie_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movie_details,
                container,
                false
            )

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        initObservers()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        viewModel.postersLiveData.observe(viewLifecycleOwner) { posterList ->
            if (posterList == null) {
                binding.labelPosters.visibility = View.GONE
                binding.postersList.visibility = View.GONE
                return@observe
            }

            with(binding.postersList) {
                registerLifecycle(lifecycle)
                imageScaleType = ImageView.ScaleType.CENTER_CROP
                setData(posterList)
            }
        }

        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(this)
                    .load(it.image)
                    .into(binding.movieImageDetails)

                binding.movieName.text = "${it.title} (${it.dateRelease})"
                viewModel.getMoviePosters(it)
            }
        }
    }

}