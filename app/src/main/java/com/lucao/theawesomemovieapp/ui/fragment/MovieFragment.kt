package com.lucao.theawesomemovieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import com.lucao.theawesomemovieapp.ui.adapter.MovieItemListener
import com.lucao.theawesomemovieapp.ui.viewmodel.MovieViewModel
import com.lucao.theawesomemovieapp.ui.adapter.MyItemRecyclerViewAdapter
import com.lucao.theawesomemovieapp.R
import com.lucao.theawesomemovieapp.databinding.FragmentMovieItemBinding
import com.lucao.theawesomemovieapp.databinding.FragmentMovieListBinding
import com.lucao.theawesomemovieapp.util.DataState

class MovieFragment : Fragment(), MovieItemListener {

    private val viewModel by hiltNavGraphViewModels<MovieViewModel>(R.id.movie_graph)
    private lateinit var adapter: MyItemRecyclerViewAdapter
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_list,
            container,
            false
        )
        adapter = MyItemRecyclerViewAdapter(this@MovieFragment)

        setMenu()

        // Set the adapter
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MovieFragment.adapter
        }
        initObservers()
        return binding.root
    }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }

    private fun initObservers() {
        viewModel.listLiveData.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                adapter.updateData(list)
            } else {
                Snackbar.make(
                    binding.root, "Vish, Deu ruim em =(", Snackbar.LENGTH_LONG
                ).show()
            }
        }

        viewModel.navigationToDetailsLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                val action =
                    MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DataState.Loading -> {
                    binding.list.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                DataState.Error -> {
                    Snackbar.make(
                        binding.root, "Vish, Deu ruim em =(", Snackbar.LENGTH_LONG
                    ).show()
                }

                else -> {
                    binding.list.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setMenu() {
        val menuNavHost: MenuHost = requireActivity()

        menuNavHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}