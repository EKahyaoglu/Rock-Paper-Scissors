package com.example.rockpaperscissors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Declaring UI Elements
    lateinit var tvResult: TextView
    lateinit var tvScore: TextView
    lateinit var tvRounds: TextView
    lateinit var btnReset: Button

    // Declaring Game State Variables
    private var playerScore = 0
    private var computerScore = 0
    private var roundsPlayed = 0
    private var scoreLimit = 5  // First to five wins

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI Elements
        tvResult = findViewById(R.id.tvResult)
        tvScore = findViewById(R.id.tvScore)
        tvRounds = findViewById(R.id.tvRounds)
        btnReset = findViewById(R.id.btnReset)

        // Initialize button choices
        val btnRock: ImageButton = findViewById(R.id.btnRock)
        val btnPaper: ImageButton = findViewById(R.id.btnPaper)
        val btnScissors: ImageButton = findViewById(R.id.btnScissors)

        // Click listeners for player choices
        btnRock.setOnClickListener { playRound("rock") }
        btnPaper.setOnClickListener { playRound("paper") }
        btnScissors.setOnClickListener { playRound("scissors") }

        // Click listener for reset button
        btnReset.setOnClickListener { resetGame() }
    }

    // Handles the game logic for a single round when the player selects rock, paper, or scissors.
    private fun playRound(playerChoice: String) {

        // Check if game is over
        if (playerScore >= scoreLimit || computerScore >= scoreLimit) {
            tvResult.text = "Game Over! Reset the game to play again."
            return
        }

        // Select random move for COM
        val choices = arrayOf("rock", "paper", "scissors")
        val computerChoice = choices[Random.nextInt(choices.size)]

        // Set and determine outcomes of the round
        val result = when {
            playerChoice == computerChoice -> "It's a Draw!"
            (playerChoice == "rock" && computerChoice == "scissors") ||
                    (playerChoice == "scissors" && computerChoice == "paper") ||
                    (playerChoice == "paper" && computerChoice == "rock") -> {
                playerScore++   // Player wins, increase player score
                "You Win!"
            }
            else -> {
                computerScore++ // Computer wins, increase COM score
                "You Lose!"
            }
        }

        // Move onto the next round
        roundsPlayed++

        // Update result text with round outcome
        tvResult.text = "Computer chose $computerChoice. $result"
        updateScore()

        // Check if game has ended through scoreLimit
        if (playerScore == scoreLimit || computerScore == scoreLimit) {
            tvResult.text =
                if (playerScore == scoreLimit) "You reached $scoreLimit points and won the game!"
                else "Computer reached $scoreLimit points and won the game!"
        }
    }

    // Updates the displayed score and rounds played
    private fun updateScore() {
        tvScore.text = "Score - You: $playerScore | Computer: $computerScore"
        tvRounds.text = "Rounds Played: $roundsPlayed"
    }

    // Resets the game to its initial state
    private fun resetGame() {
        // Reset game variables
        playerScore = 0
        computerScore = 0
        roundsPlayed = 0

        // Reset UI elements
        tvResult.text = "Choose Rock, Paper, or Scissors"
        updateScore()
    }
}