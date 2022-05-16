package edu.miu.quizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import edu.miu.quizapp.R
import edu.miu.quizapp.common.ParentFragment

class ResultFragment : ParentFragment() {

    private lateinit var btnResult: Button
    private lateinit var btnTryAgain: Button
    private lateinit var viewScore: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_result, container, false)
        viewScore = view.findViewById(R.id.tv_score)
        val score = ResultFragmentArgs.fromBundle(requireArguments()).score
        val answers = ResultFragmentArgs.fromBundle(requireArguments()).answers
        val wrongAnswers = 15 - score
        val finalScore = "$score/15"
        val scoreResult = String.format(
            "Total Number of Questions: 15\n\nNumber of Correct Answers: %d\n\nNumber of Wrong Answer: %d\n\nFinal Score: %s",
            score, wrongAnswers, finalScore
        )
        viewScore.text = scoreResult
        btnResult = view.findViewById(R.id.btn_result_analysis)
        btnTryAgain = view.findViewById(R.id.btn_try_again)
        btnTryAgain.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_resultFragment_to_homeFragment)
        }
        btnResult.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToAnswerFragment(answers)
            Navigation.findNavController(requireView()).navigate(action)
        }
        return view
    }


}