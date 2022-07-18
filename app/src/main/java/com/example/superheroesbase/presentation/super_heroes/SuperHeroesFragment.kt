package com.example.superheroesbase.presentation.super_heroes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroesbase.R
import com.example.superheroesbase.data.remote.ApiResponseStatus
import com.example.superheroesbase.databinding.FragmentSuperHeroesBinding
import com.example.superheroesbase.domain.model.SuperHero


class SuperHeroesFragment : Fragment(), SuperHeroesEvents {

    lateinit var superHeroesLayoutManager: LinearLayoutManager
    lateinit var binding: FragmentSuperHeroesBinding
    lateinit var superHeroesAdapter: SuperHeroesAdapter
    val superHeroViewModel = SuperHeroesViewModel()
    private lateinit var superHeroActivity: SuperHeroesActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initViewBinding(inflater, container)

    private fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewGroup {
        binding = FragmentSuperHeroesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { context ->
            superHeroActivity = context as SuperHeroesActivity
            superHeroActivity.toolbarTitle?.text = ""
        }
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        superHeroActivity.toolbarTitle?.text = getString(R.string.app_name)
        superHeroActivity.toolbarBackButton?.visibility = View.GONE
    }

    private fun initRecycler(superHeroes: List<SuperHero>) {
        superHeroesAdapter = SuperHeroesAdapter(this)
        superHeroesLayoutManager = LinearLayoutManager(activity)
        binding.rvSuperHeroes.apply {
            layoutManager = superHeroesLayoutManager
            adapter = superHeroesAdapter
            setHasFixedSize(true)
        }
        superHeroesAdapter.addSuperHeroes(superHeroes)
        initScroll()
    }

    private fun initViewModel() {
        superHeroViewModel.getSuperHeroes()
        superHeroViewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                ApiResponseStatus.ERROR -> {
                    binding.progressCircular.visibility = View.GONE
                }
                ApiResponseStatus.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    superHeroViewModel.superHeroesList.value?.let { superHeroes ->
                        if (superHeroViewModel.currentPage == 1)
                            initRecycler(superHeroes)
                        else {
                            addSuperHeroes(superHeroes)
                        }
                    }
                }
                ApiResponseStatus.LOADING -> binding.progressCircular.visibility = View.VISIBLE
                else -> binding.progressCircular.visibility = View.GONE
            }
        }
    }

    private fun addSuperHeroes(superHeroes: List<SuperHero>) {
        superHeroesAdapter.addSuperHeroes(superHeroes)
    }

    private fun initScroll() {
        binding.rvSuperHeroes.layoutManager = superHeroesLayoutManager
        binding.rvSuperHeroes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItems: Int = superHeroesLayoutManager.childCount
                    val pastItems =
                        superHeroesLayoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = superHeroesAdapter.itemCount
                    if (superHeroViewModel.status.value != ApiResponseStatus.LOADING &&
                        (visibleItems + pastItems) > total
                    ) {
                        binding.progressCircular.visibility = View.VISIBLE
                        superHeroViewModel.getSuperHeroes()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onClickSuperHero(superHeroId: String) {
        superHeroViewModel.currentPage = 0
        findNavController().navigate(R.id.action_superHeroesFragment_to_superHeroDetailsFragment,
            Bundle().apply {
                putString("superHeroId", superHeroId)
            }
        )
    }

}