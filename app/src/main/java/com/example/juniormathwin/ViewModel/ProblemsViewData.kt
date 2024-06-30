package com.example.juniormathwin.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.juniormathwin.dataBase.GameScore

class ProblemsViewData : ViewModel() {
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    fun setScore(newScore: Int){
        _score.value = newScore
    }
}