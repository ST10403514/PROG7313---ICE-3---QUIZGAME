package com.mason.quizgame

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mason.quizgame.databinding.ActivityQuizBinding
import com.mason.quizgame.models.Question
import com.mason.quizgame.models.QuizCategory
import com.mason.quizgame.models.Score

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private val categories = mutableListOf<QuizCategory>()
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private var startTime = 0L
    private lateinit var category: String
    private val username = "Player1" // Replace with dynamic input later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("CATEGORY") ?: "History"
        initializeCategories()
        startTime = System.currentTimeMillis()

        binding.progressSeekbar.max = 10
        binding.progressSeekbar.isEnabled = false
        loadQuestion()

        binding.submitButton.setOnClickListener { checkAnswer() }
    }
    private fun initializeCategories() {
        val history = QuizCategory("History")
        val science = QuizCategory("Science")
        val math = QuizCategory("Math")
        val geography = QuizCategory("Geography")

        // History Questions
        history.apply {
            addQuestion(Question("Who was the first President of the United States?", arrayOf("George Washington", "Thomas Jefferson", "Abraham Lincoln", "John Adams"), 0))
            addQuestion(Question("In which year did World War II end?", arrayOf("1942", "1945", "1939", "1948"), 1))
            addQuestion(Question("What ancient civilization built the pyramids?", arrayOf("Romans", "Greeks", "Egyptians", "Mayans"), 2))
            addQuestion(Question("Who discovered America in 1492?", arrayOf("Vasco da Gama", "Christopher Columbus", "Ferdinand Magellan", "Marco Polo"), 1))
            addQuestion(Question("Which empire was ruled by Julius Caesar?", arrayOf("Ottoman", "Roman", "Byzantine", "Persian"), 1))
            addQuestion(Question("What year did the Titanic sink?", arrayOf("1912", "1905", "1920", "1918"), 0))
            addQuestion(Question("Who was the first man on the moon?", arrayOf("Buzz Aldrin", "Yuri Gagarin", "Neil Armstrong", "Alan Shepard"), 2))
            addQuestion(Question("Which war was fought between the North and South USA?", arrayOf("World War I", "Civil War", "Revolutionary War", "Vietnam War"), 1))
            addQuestion(Question("What was the Magna Carta signed in?", arrayOf("1066", "1215", "1492", "1776"), 1))
            addQuestion(Question("Who painted the Mona Lisa?", arrayOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"), 2))
        }

        // Science Questions
        science.apply {
            addQuestion(Question("What gas do plants use for photosynthesis?", arrayOf("Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"), 1))
            addQuestion(Question("What planet is known as the Red Planet?", arrayOf("Venus", "Mars", "Jupiter", "Saturn"), 1))
            addQuestion(Question("What is the chemical symbol for water?", arrayOf("H2O", "CO2", "O2", "NaCl"), 0))
            addQuestion(Question("Who developed the theory of relativity?", arrayOf("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Stephen Hawking"), 1))
            addQuestion(Question("What is the hardest natural substance?", arrayOf("Gold", "Iron", "Diamond", "Quartz"), 2))
            addQuestion(Question("What organ pumps blood in the human body?", arrayOf("Lungs", "Brain", "Heart", "Liver"), 2))
            addQuestion(Question("What is the primary source of Earth’s energy?", arrayOf("Moon", "Sun", "Wind", "Geothermal"), 1))
            addQuestion(Question("What gas makes up most of Earth’s atmosphere?", arrayOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Argon"), 1))
            addQuestion(Question("What is the speed of light in a vacuum (km/s)?", arrayOf("300", "3,000", "300,000", "30,000"), 2))
            addQuestion(Question("What element is a diamond made of?", arrayOf("Carbon", "Silicon", "Gold", "Sulfur"), 0))
        }

        // Math Questions
        math.apply {
            addQuestion(Question("What is 2 + 2?", arrayOf("3", "4", "5", "6"), 1))
            addQuestion(Question("What is the square root of 16?", arrayOf("2", "4", "6", "8"), 1))
            addQuestion(Question("What is 5 x 6?", arrayOf("25", "30", "35", "40"), 1))
            addQuestion(Question("What is 100 divided by 4?", arrayOf("20", "25", "30", "40"), 1))
            addQuestion(Question("What is the value of π (pi) to 2 decimals?", arrayOf("3.12", "3.14", "3.16", "3.18"), 1))
            addQuestion(Question("What is 7 squared?", arrayOf("42", "49", "56", "64"), 1))
            addQuestion(Question("What is 10% of 200?", arrayOf("10", "20", "30", "40"), 1))
            addQuestion(Question("What is the next prime number after 7?", arrayOf("9", "11", "13", "15"), 1))
            addQuestion(Question("What is 3/4 as a decimal?", arrayOf("0.5", "0.75", "0.25", "1.0"), 1))
            addQuestion(Question("What is the sum of angles in a triangle?", arrayOf("90°", "180°", "270°", "360°"), 1))
        }

        // Geography Questions
        geography.apply {
            addQuestion(Question("What is the capital of France?", arrayOf("Paris", "London", "Berlin", "Madrid"), 0))
            addQuestion(Question("Which continent is the largest by land area?", arrayOf("Africa", "Asia", "Australia", "Europe"), 1))
            addQuestion(Question("What river is the longest in the world?", arrayOf("Amazon", "Nile", "Yangtze", "Mississippi"), 1))
            addQuestion(Question("What mountain is the tallest?", arrayOf("K2", "Kangchenjunga", "Everest", "Makalu"), 2))
            addQuestion(Question("Which country has the most deserts?", arrayOf("Australia", "Antarctica", "Saudi Arabia", "Egypt"), 1))
            addQuestion(Question("What ocean lies between America and Europe?", arrayOf("Pacific", "Atlantic", "Indian", "Arctic"), 1))
            addQuestion(Question("What is the smallest country by land area?", arrayOf("Monaco", "Vatican City", "San Marino", "Liechtenstein"), 1))
            addQuestion(Question("Which country has the most population?", arrayOf("India", "China", "USA", "Russia"), 1))
            addQuestion(Question("What is the capital of Brazil?", arrayOf("Rio de Janeiro", "São Paulo", "Brasília", "Salvador"), 2))
            addQuestion(Question("Which continent is known as the 'Dark Continent'?", arrayOf("Asia", "Africa", "South America", "Australia"), 1))
        }

        categories.addAll(listOf(history, science, math, geography))
    }

    private fun loadQuestion() {
        val selectedCategory = categories.find { it.name == category } ?: return
        val question = selectedCategory.getQuestions()[currentQuestionIndex]
        binding.questionText.text = question.questionText
        binding.optionsGroup.removeAllViews()
        question.options.forEachIndexed { index, option ->
            val rb = RadioButton(this).apply {
                text = option
                id = index
                textSize = 16f
                setPadding(8, 8, 8, 8)
            }
            binding.optionsGroup.addView(rb)
        }
        binding.progressSeekbar.progress = currentQuestionIndex + 1
        binding.progressLabel.text = "Question ${currentQuestionIndex + 1} of 10"
    }

    private fun checkAnswer() {
        val selectedId = binding.optionsGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedCategory = categories.find { it.name == category } ?: return
        val question = selectedCategory.getQuestions()[currentQuestionIndex]
        if (selectedId == question.correctAnswerIndex) {
            correctAnswers++
        }

        currentQuestionIndex++
        if (currentQuestionIndex < 10) {
            loadQuestion()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        val timeTaken = System.currentTimeMillis() - startTime
        val intent = Intent(this, ResultsActivity::class.java).apply {
            putExtra("USERNAME", username)
            putExtra("CATEGORY", category)
            putExtra("CORRECT_ANSWERS", correctAnswers)
            putExtra("TIME_TAKEN", timeTaken)
        }
        startActivity(intent)
        finish() // Exit QuizActivity
    }
}