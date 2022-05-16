package edu.miu.quizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import edu.miu.quizapp.R
import edu.miu.quizapp.data.Question
import edu.miu.quizapp.data.QuestionDB
import edu.miu.quizapp.common.AppUtils.*
import edu.miu.quizapp.common.ParentFragment
import edu.miu.quizapp.common.toast
import kotlinx.coroutines.launch

class LandingPageFragment : ParentFragment() {

    private var landingViewModel: LandingViewModel? = null
    private var selectedChoice: String? = null
    private var answers: MutableList<String> = mutableListOf()
    private lateinit var textViewQuestion: TextView
    private lateinit var textViewScore: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var questions: List<Question>
    private var currentIndex = 0

    private lateinit var currentQuestion: Question
    private var isFirstTime = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_landing_page, container, false)
        val skipBtn = view.findViewById<Button>(R.id.btn_qstn_skip)
        val nextBtn = view.findViewById<Button>(R.id.btn_qstn_next)
        textViewQuestion = view.findViewById(R.id.tv_question)
        textViewScore = view.findViewById(R.id.tv_score)
        landingViewModel = ViewModelProvider(this).get(LandingViewModel::class.java)
        val scoreLiveData: MutableLiveData<Int> = landingViewModel!!.getInitialScore()
        scoreLiveData.observe(viewLifecycleOwner) {
            textViewScore.text = String.format("score: %d/15", it)
        }
        launch {
            context?.let {
                questions = QuestionDB(it).getQuizDao().getAllQuizzes()
                changeQuestion(view)
            }
        }
        skipBtn.setOnClickListener {
            changeQuestion(view)
        }
        nextBtn.setOnClickListener {
            if (selectedChoice != null){
                checkAnswer(selectedChoice!!)
                changeQuestion(view)
            } else context?.toast(getString(R.string.please_provide_answer_toast_message))

        }
        radioGroup = view.findViewById(R.id.question_radio)
        radioGroup.setOnCheckedChangeListener(this::radioButtonExecute)
        return view
    }



    private fun checkAnswer(ans: String) {
        if (currentQuestion.answer == ans) {
            landingViewModel!!.getCurrentScore()
        }
    }

    private fun changeQuestion(view:View) {
        if(!isFirstTime){
            val selectedAns = if(selectedChoice!=null) selectedChoice else ""
            answers.add(selectedAns!!)
        }
        isFirstTime = false
        if (currentIndex == 15) {
            val action = LandingPageFragmentDirections.actionHomeFragmentToResultFragment(
                score = landingViewModel?.getFinalScore()?.value!!, answers = answers.toTypedArray()
            )
            Navigation.findNavController(requireView()).navigate(action)
            return
        }
        currentQuestion = questions[currentIndex]
        textViewQuestion.text = currentQuestion.question
        val radioGroup = view.findViewById(R.id.question_radio) as RadioGroup
        val questionChoices = listOf(currentQuestion.a, currentQuestion.b, currentQuestion.c, currentQuestion.d)
        for (i in 0 until radioGroup.childCount) {
            (radioGroup.getChildAt(i) as RadioButton).text = questionChoices[i]
        }
        currentIndex++
        selectedChoice = null
        radioGroup.clearCheck()
    }

    private fun radioButtonExecute(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.radio_q1_a -> selectedChoice = AnswerChoice.A.value
            R.id.radio_q1_b -> selectedChoice = AnswerChoice.B.value
            R.id.radio_q1_c -> selectedChoice = AnswerChoice.C.value
            R.id.radio_q1_d -> selectedChoice = AnswerChoice.D.value
        }
    }


}