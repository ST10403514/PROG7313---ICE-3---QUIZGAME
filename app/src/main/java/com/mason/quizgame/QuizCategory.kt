package com.mason.quizgame.models

class QuizCategory(val name: String) {
    private val questions: MutableList<Question> = mutableListOf()

    fun getQuestions(): List<Question> = questions
    fun addQuestion(question: Question) {
        questions.add(question)
    }
}