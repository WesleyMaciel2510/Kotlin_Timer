package com.example.kotlin_timer

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var countdownTimer: CountDownTimer? = null
    private var initialMilliseconds: Long = 0
    private var timeRemaining: Long = 0
    private var timerRunning = false
    private var timerStopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Search Components by ID
        val editText: EditText = findViewById(R.id.input_value)
        val buttonStart: Button = findViewById(R.id.btn_start_counter)
        val buttonStop: Button = findViewById(R.id.btn_stop_counter)
        val buttonReset: Button = findViewById(R.id.btn_reset_counter)
        val resultTextView: TextView = findViewById(R.id.txt_counter_display)

        // Set click listeners
        buttonStart.setOnClickListener {
            val input = editText.text.toString().toIntOrNull()
            if (input != null) {
                println("timerStopped = " + timerStopped);

                if (timerStopped) {

                    resumeCountdown(resultTextView)
                    println("@ RESUME CALLED! ");
                    println("@ Passing the Time = " + resultTextView);

                    timerStopped = false
                } else {
                    startCountdown(input, resultTextView)
                    println("@ START CALLED! ");
                    timerStopped = false

                }
            } else {
                Toast.makeText(this, "Please enter a value.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonStop.setOnClickListener {
            stopCountdown()
            println("@ STOP CALLED! ")
            timerStopped = true
        }

        buttonReset.setOnClickListener {
            resetCountdown(resultTextView)
            println("@ RESET CALLED! ");
            timerStopped = true
        }
    }

    private fun startCountdown(minutes: Int, resultTextView: TextView) {
        val milliseconds = minutes * 60 * 1000L

        countdownTimer?.cancel()

        countdownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                resultTextView.text = formattedTime
            }

            override fun onFinish() {
                resultTextView.text = "00:00:00"
                timerRunning = false
                Toast.makeText(this@MainActivity, "Time's up!", Toast.LENGTH_SHORT).show()
            }
        }.start()

        timerRunning = true
    }

    private fun resumeCountdown(resultTextView: TextView) {
        if (timeRemaining > 0 && !timerRunning) {
            countdownTimer?.cancel()

            countdownTimer = object : CountDownTimer(timeRemaining, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemaining = millisUntilFinished
                    val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
                    val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

                    val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    resultTextView.text = formattedTime
                }

                override fun onFinish() {
                    resultTextView.text = "00:00:00"
                    timerRunning = false
                    Toast.makeText(this@MainActivity, "Time's up!", Toast.LENGTH_SHORT).show()
                }
            }.start()

            timerRunning = true
        }
    }


    private fun stopCountdown() {
        countdownTimer?.cancel()
        timerRunning = false
        Toast.makeText(this, "Countdown stopped", Toast.LENGTH_SHORT).show()
    }

    private fun resetCountdown(resultTextView: TextView) {
        countdownTimer?.cancel()
        resultTextView.text = "00:00:00"
        timerRunning = false
        Toast.makeText(this, "Countdown reset", Toast.LENGTH_SHORT).show()
    }
}
