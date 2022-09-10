package com.example.guesstheword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wordguess.FourLetterWordList
import com.github.jinatonic.confetti.CommonConfetti

class MainActivity : AppCompatActivity() {

    var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    var pos =0
    var reset = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val simpleEditText =  findViewById<EditText>(R.id.WordEnter)
        val button = findViewById<Button>(R.id.button)
        val guessTexts = arrayListOf<TextView>(findViewById<TextView>(R.id.Guess1),findViewById<TextView>(R.id.Guess2),
            findViewById<TextView>(R.id.Guess3))
        val checkTexts = arrayListOf<TextView>(findViewById<TextView>(R.id.Check1), findViewById<TextView>(R.id.Check2),
            findViewById<TextView>(R.id.Check3))
        val answer = findViewById<TextView>(R.id.textView)




        button.setOnClickListener {
            val strValue = simpleEditText.getText().toString().uppercase()
            simpleEditText.text.clear()

            if (strValue.length <4){
                Toast.makeText(this, "Enter a 4-letter word", Toast.LENGTH_LONG).show()
            }
            else if (reset){
                reset = false
                for (text in guessTexts) text.text = ""
                for (text in checkTexts) text.text = ""
                answer.visibility = View.INVISIBLE
                button.text = "SUBMIT"
                wordToGuess = FourLetterWordList.getRandomFourLetterWord()

            }
            else{

                guessTexts[pos].text = strValue
                val actual = checkGuess(strValue) == "OOOO"
                checkTexts[pos].text = checkGuess(strValue)
                pos++


                if (actual or (pos==3)){

                    button.text = "RESET"
                    answer.text = "Answer:" + wordToGuess
                    answer.visibility = View.VISIBLE
                    reset = true
                    pos=0
                }
            }
        }
    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}