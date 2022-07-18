package com.example.superheroesbase.presentation.super_heroes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superheroesbase.commons.Constants.ERROR_DEFAULT_MESSAGE
import com.example.superheroesbase.commons.Constants.ERROR_TITLE
import com.example.superheroesbase.commons.Constants.PAGE_SIZE
import com.example.superheroesbase.data.remote.ApiResponseStatus
import com.example.superheroesbase.data.repository.SuperHeroesRepositoryImpl
import com.example.superheroesbase.domain.model.SuperHero
import kotlinx.coroutines.launch

class SuperHeroesViewModel : ViewModel() {

    var currentPage = 0

    private val _superHeroesList = MutableLiveData<List<SuperHero>>()
    val superHeroesList: LiveData<List<SuperHero>> = _superHeroesList

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus> = _status

    private val superHeroesRepository = SuperHeroesRepositoryImpl()

    fun getSuperHeroes() {
        viewModelScope.launch {
            currentPage++
            _status.value = ApiResponseStatus.LOADING
            try {
                _superHeroesList.value = superHeroesRepository.getSuperHeroes(
                    getFromRange(),
                    getToRange()
                )
                _status.value = ApiResponseStatus.SUCCESS

            } catch (e: Exception) {
                Log.d(ERROR_TITLE, e.message ?: ERROR_DEFAULT_MESSAGE)
                _status.value = ApiResponseStatus.ERROR
            }
        }
    }


    private fun getFromRange(): Int = (currentPage * PAGE_SIZE) - (PAGE_SIZE - 1)

    private fun getToRange(): Int = currentPage * PAGE_SIZE

}