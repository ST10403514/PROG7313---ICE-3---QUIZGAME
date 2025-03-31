package com.mason.quizgame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mason.quizgame.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.historyButton.setOnClickListener { startQuiz("History") }
        binding.scienceButton.setOnClickListener { startQuiz("Science") }
        binding.mathButton.setOnClickListener { startQuiz("Math") }
        binding.geographyButton.setOnClickListener { startQuiz("Geography") }
    }

    private fun startQuiz(category: String) {
        val intent = Intent(this, QuizActivity::class.java).apply {
            putExtra("CATEGORY", category)
        }
        startActivity(intent)
    }
}