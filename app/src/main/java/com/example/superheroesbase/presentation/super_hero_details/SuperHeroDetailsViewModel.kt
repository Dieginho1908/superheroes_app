package com.example.superheroesbase.presentation.super_hero_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superheroesbase.data.remote.ApiResponseStatus
import com.example.superheroesbase.data.repository.SuperHeroesRepositoryImpl
import com.example.superheroesbase.domain.model.SuperHero
import kotlinx.coroutines.launch

class SuperHeroDetailsViewModel: ViewModel() {
    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus> = _status


    private val _superHero = MutableLiveData<SuperHero>()
    val superHero: LiveData<SuperHero> = _superHero

    private val superHeroesRepository = SuperHeroesRepositoryImpl()

    fun getSuperHeroById(superHeroId: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.LOADING
            try {
                _superHero.value = superHeroesRepository.getSuperHeroById(superHeroId)
                _status.value = ApiResponseStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("Get Heroes Error", e.message ?: "Something went wrong")
                _status.value = ApiResponseStatus.ERROR
            }
        }
    }
}