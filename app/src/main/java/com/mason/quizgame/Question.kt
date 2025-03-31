package com.mason.quizgame.models

data class Question(
    val questionText: String,
    val options: Array<String>,
    val correctAnswerIndex: Int
)