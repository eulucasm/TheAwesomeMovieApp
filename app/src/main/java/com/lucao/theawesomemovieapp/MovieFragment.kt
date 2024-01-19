package com.lucao.theawesomemovieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.lucao.theawesomemovieapp.databinding.FragmentMovieItemBinding
import com.lucao.theawesomemovieapp.placeholder.PlaceholderContent

class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) { defaultViewModelProviderFactory }
    private lateinit var binding : FragmentMovieItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemBinding.inflate(inflater, container, false)
        val view = binding.root as RecyclerView
        adapter = MyItemRecyclerViewAdapter(this)
        initObservers(view)

        return view
    }

    private fun initObservers(view: RecyclerView) {
        viewModel.viewDataStateLiveData.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                DataState.Loading -> showLoadingState()
                DataState.Success -> showSuccessState(view)
                DataState.Error -> showErrorState()
            }
        })

        viewModel.navigationToMovieDetailsLiveData.observe(viewLifecycleOwner, Observer {
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment()
            findNavController().navigate(action)
        })
    }

    private fun showLoadingState() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun showSuccessState(view: RecyclerView) {
        setupview(view)
        binding.progressbar.visibility = View.GONE
    }
    private fun showErrorState() {
        Toast.makeText(context, "Erro na captura dos dados", Toast.LENGTH_SHORT).show()
    }

    private fun setupview(view: RecyclerView) {
        view.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }



    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }
}