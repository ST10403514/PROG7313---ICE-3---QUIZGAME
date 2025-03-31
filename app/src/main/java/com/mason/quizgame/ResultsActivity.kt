package com.mason.quizgame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mason.quizgame.databinding.ActivityResultsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.mason.quizgame.models.Score

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from Intent
        val username = intent.getStringExtra("USERNAME") ?: "Player1"
        val category = intent.getStringExtra("CATEGORY") ?: "Unknown"
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)
        val timeTaken = intent.getLongExtra("TIME_TAKEN", 0L)

        // Create Score object
        val score = Score(username, category, correctAnswers, timeTaken)

        // Display results
        binding.usernameText.text = "Username: $username"
        binding.categoryText.text = "Category: $category"
        binding.correctAnswersText.text = "Correct Answers: $correctAnswers/10"
        binding.timeTakenText.text = "Time Taken: ${timeTaken / 1000}s"
        binding.totalScoreText.text = "Total Score: ${score.totalScore}"

        // Save to Firestore on button click
        binding.saveButton.setOnClickListener {
            db.collection("scores")
                .add(score)
                .addOnSuccessListener {
                    Toast.makeText(this, "Score saved successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Return to CategoryActivity or MainActivity
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving score: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}