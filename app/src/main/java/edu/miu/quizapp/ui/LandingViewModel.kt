package edu.miu.quizapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LandingViewModel : ViewModel() {
    private  var score = 0
    private var scoreLiveData= MutableLiveData<Int>()

    fun getFinalScore(): MutableLiveData<Int>{
        return scoreLiveData
    }
    fun getCurrentScore(){
        score+=1
        scoreLiveData.value= score
    }
    fun getInitialScore(): MutableLiveData<Int> {
        scoreLiveData.value = score
        return  scoreLiveData
    }

}
