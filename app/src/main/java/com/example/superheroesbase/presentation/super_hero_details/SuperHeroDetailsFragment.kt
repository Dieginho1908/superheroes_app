package com.example.superheroesbase.presentation.super_hero_details

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.superheroesbase.R
import com.example.superheroesbase.data.remote.ApiResponseStatus
import com.example.superheroesbase.data.remote.dto.Appearance
import com.example.superheroesbase.data.remote.dto.Biography
import com.example.superheroesbase.data.remote.dto.Powerstats
import com.example.superheroesbase.databinding.FragmentSuperHeroDetailsBinding
import com.example.superheroesbase.domain.model.SuperHero
import com.example.superheroesbase.presentation.super_heroes.SuperHeroesActivity

class SuperHeroDetailsFragment : Fragment() {


    private var superHeroId: String? = null
    private lateinit var binding: FragmentSuperHeroDetailsBinding
    private val superHeroDetailsViewModel = SuperHeroDetailsViewModel()
    private lateinit var superHeroActivity: SuperHeroesActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        superHeroId = arguments?.getString("superHeroId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initViewBinding(inflater, container)

    private fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewGroup {
        binding = FragmentSuperHeroDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { context ->
            superHeroActivity = context as SuperHeroesActivity
            superHeroActivity.toolbarTitle?.text = ""
            superHeroActivity.toolbarBackButton?.visibility = View.VISIBLE
        }
        initSuperHero()
        initExpandableItems()
    }

    private fun initExpandableItems() {
        initBiographyExpand()
        initPowerStatsExpand()
        initAppearanceExpand()
    }

    private fun initSuperHero() {
        superHeroId?.let { id ->
            superHeroDetailsViewModel.getSuperHeroById(id)
            superHeroDetailsViewModel.status.observe(viewLifecycleOwner) { status ->
                when (status) {
                    ApiResponseStatus.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                    ApiResponseStatus.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.materialCard.visibility = View.VISIBLE

                        superHeroDetailsViewModel.superHero.value?.let { superHero ->
                            Toast.makeText(
                                this.context,
                                "Super héroe: ${superHero.name}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Glide.with(requireContext())
                                .load(superHero.image.url)
                                .into(binding.superHeroImage)

                            initExpandableBinding(superHero)

                            superHeroActivity.toolbarTitle?.text = superHero.name
                        }
                    }
                    ApiResponseStatus.LOADING -> binding.progressCircular.visibility = View.VISIBLE
                    else -> binding.progressCircular.visibility = View.GONE
                }
            }
        }

    }

    // Init expandable Items
    private fun initBiographyExpand() {
        binding.layoutBiography.clickToExpand.setOnClickListener {
            if (binding.layoutBiography.expandableArea.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.materialCard, AutoTransition())
                binding.layoutBiography.expandableArea.visibility = View.VISIBLE
                binding.layoutBiography.expandableIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
            } else {
                TransitionManager.beginDelayedTransition(binding.materialCard, AutoTransition())
                binding.layoutBiography.expandableArea.visibility = View.GONE
                binding.layoutBiography.expandableIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
            }
        }
    }

    private fun initPowerStatsExpand() {
        binding.layoutPowerStats.clickToExpand.setOnClickListener {
            if (binding.layoutPowerStats.expandableArea.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.materialCard, AutoTransition())
                binding.layoutPowerStats.expandableArea.visibility = View.VISIBLE
                binding.layoutPowerStats.expandableIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
            } else {
                TransitionManager.beginDelayedTransition(binding.materialCard, AutoTransition())
                binding.layoutPowerStats.expandableArea.visibility = View.GONE
                binding.layoutPowerStats.expandableIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
            }
        }
    }

    private fun initAppearanceExpand() {
        binding.layoutAppearance.clickToExpand.setOnClickListener {
            if (binding.layoutAppearance.expandableArea.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.materialCard, AutoTransition())
                binding.layoutAppearance.expandableArea.visibility = View.VISIBLE
                binding.layoutAppearance.expandableIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down)
            } else {
                TransitionManager.beginDelayedTransition(binding.materialCard, AutoTransition())
                binding.layoutAppearance.expandableArea.visibility = View.GONE
                binding.layoutAppearance.expandableIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up)
            }
        }
    }

    private fun initExpandableBinding(superHero: SuperHero) {
        initBiographyBinding(superHero.biography)
        initPowerStatsBinding(superHero.powerStats)
        initAppearanceBinding(superHero.appearance)
    }

    private fun initBiographyBinding(biography: Biography) {
        binding.layoutBiography.apply {
            expandableArea.visibility = View.VISIBLE
            expandableTitle.text = getString(R.string.biography_title)

            attributeOne.visibility = View.VISIBLE
            attributeOneTitle.text = getString(R.string.biography_publisher_title)
            attributeOneContent.text = biography.publisher

            attributeTwo.visibility = View.VISIBLE
            attributeTwoTitle.text = getString(R.string.biography_full_name_title)
            attributeTwoContent.text = biography.fullName

            attributeThree.visibility = View.VISIBLE
            attributeThreeTitle.text = getString(R.string.biography_alignment_title)
            attributeThreeContent.text = biography.alignment

            attributeFour.visibility = View.VISIBLE
            attributeFourTitle.text = getString(R.string.biography_place_of_birth_title)
            attributeFourContent.text = biography.placeOfBirth

            attributeFive.visibility = View.VISIBLE
            attributeFiveTitle.text = getString(R.string.biography_alter_egos_title)
            attributeFiveContent.text = biography.alterEgos

            attributeSix.visibility = View.VISIBLE
            attributeSixTitle.text = getString(R.string.power_stats_combat_title)
            attributeSixContent.text = biography.firstAppearance

        }
    }

    private fun initPowerStatsBinding(powerStats: Powerstats) {
        binding.layoutPowerStats.apply {
            expandableTitle.text = getString(R.string.power_stats_title)

            attributeOne.visibility = View.VISIBLE
            attributeOneTitle.text = getString(R.string.power_stats_power_title)
            attributeOneContent.text = powerStats.power

            attributeTwo.visibility = View.VISIBLE
            attributeTwoTitle.text = getString(R.string.power_stats_intelligence_title)
            attributeTwoContent.text = powerStats.intelligence

            attributeThree.visibility = View.VISIBLE
            attributeThreeTitle.text = getString(R.string.power_stats_speed_title)
            attributeThreeContent.text = powerStats.speed

            attributeFour.visibility = View.VISIBLE
            attributeFourTitle.text = getString(R.string.power_stats_strength_title)
            attributeFourContent.text = powerStats.strength

            attributeFive.visibility = View.VISIBLE
            attributeFiveTitle.text = getString(R.string.power_stats_durability_title)
            attributeFiveContent.text = powerStats.durability

            attributeSix.visibility = View.VISIBLE
            attributeSixTitle.text = getString(R.string.power_stats_combat_title)
            attributeSixContent.text = powerStats.combat

        }
    }

    private fun initAppearanceBinding(appearance: Appearance) {
        binding.layoutAppearance.apply {
            expandableTitle.text = getString(R.string.appearance_title)

            attributeOne.visibility = View.VISIBLE
            attributeOneTitle.text = getString(R.string.appearance_race_title)
            attributeOneContent.text = appearance.race

            attributeTwo.visibility = View.VISIBLE
            attributeTwoTitle.text = getString(R.string.appearance_eye_color_title)
            attributeTwoContent.text = appearance.eyeColor

            attributeThree.visibility = View.VISIBLE
            attributeThreeTitle.text = getString(R.string.appearance_gender_title)
            attributeThreeContent.text = appearance.gender

            attributeFour.visibility = View.VISIBLE
            attributeFourTitle.text = getString(R.string.appearance_hair_color_title)
            attributeFourContent.text = appearance.hairColor

        }
    }

}