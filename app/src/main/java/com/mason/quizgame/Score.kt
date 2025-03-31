package com.mason.quizgame.models

data class Score(
    val username: String,
    val category: String,
    val correctAnswers: Int,
    val timeTaken: Long // in milliseconds
) {
    val totalScore: Int
        get() = calculateScore()

    private fun calculateScore(): Int {
        return (correctAnswers * 100) - (timeTaken / 1000).toInt() // 100 points per correct answer, minus seconds taken
    }

    constructor() : this("", "", 0, 0L) // For Firestore
}