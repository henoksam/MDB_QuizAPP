package edu.miu.quizapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Question::class],
    version = 1,
    exportSchema = true
)
abstract class QuestionDB : RoomDatabase() {
    abstract fun getQuizDao(): QuestionDAO

    companion object {
        @Volatile
        private var instance: QuestionDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: populateQuestions(context).also {
                instance = it
            }
        }

        private fun populateQuestions(context: Context): QuestionDB {
            return Room.databaseBuilder(
                context.applicationContext,
                QuestionDB::class.java,
                "quizdatabase"
            ).build()
        }
    }

}