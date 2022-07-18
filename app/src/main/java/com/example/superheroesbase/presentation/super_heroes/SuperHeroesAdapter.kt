package com.example.superheroesbase.presentation.super_heroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.superheroesbase.R
import com.example.superheroesbase.databinding.ItemSuperheroBinding
import com.example.superheroesbase.domain.model.SuperHero

class SuperHeroesAdapter(private val superHeroesEvents: SuperHeroesEvents) :
    RecyclerView.Adapter<SuperHeroesAdapter.SuperHeroesViewHolder>() {
    inner class SuperHeroesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSuperheroBinding.bind(view)

    }

    private val superHeroesList: MutableList<SuperHero> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroesViewHolder {
        return SuperHeroesViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.item_superhero, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SuperHeroesViewHolder, position: Int) {
        val item = superHeroesList[position]


        val race =
            if (item.appearance.race.isNotEmpty() && item.appearance.race != "null")
                item.appearance.race
            else holder.binding.raceText.context.getString(R.string.unknown_value)

        holder.binding.nameText.text = item.name
        holder.binding.raceText.text = race
        holder.binding.publisherText.text = holder.binding.publisherText.context.getString(
            R.string.publisher,
            item.biography.publisher
        )

        Glide.with(holder.binding.superHeroImage.context)
            .load(item.image.url)
            .into(holder.binding.superHeroImage)

        holder.binding.root.setOnClickListener {
            superHeroesEvents.onClickSuperHero(item.id)
        }
    }

    override fun getItemCount(): Int {
        return superHeroesList.size
    }

    fun addSuperHeroes(superHeroes: List<SuperHero>) {
        superHeroesList.addAll(superHeroes)
        notifyDataSetChanged()
    }
}