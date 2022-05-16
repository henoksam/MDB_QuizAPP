package edu.miu.quizapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import edu.miu.quizapp.R
import edu.miu.quizapp.data.Question
import edu.miu.quizapp.data.QuestionDB
import edu.miu.quizapp.common.ParentFragment
import kotlinx.coroutines.launch


class ScoreFragment : ParentFragment() {

    private lateinit var questions: List<Question>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_answer, container, false)
        val listView = view.findViewById<ListView>(R.id.list_view)
        val answers = ResultFragmentArgs.fromBundle(requireArguments()).answers
        launch {
            context?.let {
                questions = QuestionDB(it).getQuizDao().getAllQuizzes()
                questions.forEach{ q ->
                    q.answer
                }
                listView.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
                    analyzeResults(questions, answers.toList()))
            }
        }

        return view
    }

    private fun analyzeResults(questions: List<Question>, answers: List<String>): List<String> {
        val items = mutableListOf<String>()
        questions.forEachIndexed { index, quiz ->
            val listItem = String.format("%s\nYour answer: %s\nCorrect answer: %s",quiz.question,answers[index],quiz.explanation)
            items.add(listItem)
        }
        return items
    }

}