package com.example.guesstheword

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.color
import com.example.wordguess.FourLetterWordList
import com.github.jinatonic.confetti.CommonConfetti;



class MainActivity : AppCompatActivity() {

    var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    var pos =0
    var reset = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val simpleEditText =  findViewById<EditText>(R.id.WordEnter)
        val button = findViewById<Button>(R.id.button)
        val guessTexts = arrayListOf<TextView>(findViewById(R.id.Guess1),findViewById(R.id.Guess2),
            findViewById(R.id.Guess3))
        val checkTexts = arrayListOf<TextView>(findViewById(R.id.Check1), findViewById(R.id.Check2),
            findViewById(R.id.Check3))
        val answer = findViewById<TextView>(R.id.textView)




        button.setOnClickListener {
            val strValue = simpleEditText.getText().toString().uppercase()
            simpleEditText.text.clear()

            if (reset){
                reset = false
                for (text in guessTexts) text.text = ""
                for (text in checkTexts) text.text = ""
                answer.visibility = View.INVISIBLE
                button.text = "SUBMIT"
                wordToGuess = FourLetterWordList.getRandomFourLetterWord()

            }
            else if (strValue.length <4){
                Toast.makeText(this, "Enter a 4-letter word", Toast.LENGTH_LONG).show()
            }
            else{

                it.hideKeyboard()
                guessTexts[pos].text = strValue
                val actual = checkGuess(strValue)
                checkTexts[pos].text = actual.first
                pos++

                if (actual.third){
                    answer.text = "CONGRATULATIONS"
                    button.text = "RESET"
                    answer.visibility = View.VISIBLE
                    reset = true
                    pos=0
                }
                else if (pos==3){

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
     *   guess : String - what the user entered as their guess
     *   return type: Triple with colored spannable string, number of correct
     *   letters and boolean whether the word was guessed correctly
     */
    private fun checkGuess(guess: String) : Triple<SpannableStringBuilder,Int,Boolean> {
        var result = SpannableStringBuilder()
        var correct = 0
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result.color(Color.GREEN, {append(guess[i])})
                correct++
            }
            else if (guess[i] in wordToGuess) {
                result.color(Color.YELLOW, {append(guess[i])})
            }
            else {
                result.append(guess[i].toString())
            }
        }
        return Triple(result, correct, correct==4)
    }

    /** hides the keyboard after submitting a word
    */
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}