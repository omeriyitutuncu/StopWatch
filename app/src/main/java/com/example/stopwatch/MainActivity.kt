package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer // the stopwatch
    var running = false
    var offset: Long = 0

    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatch = findViewById(R.id.stopwatch)
        Toast.makeText(this, stopwatch.base.toString(),Toast.LENGTH_SHORT).show()

        val startButton = findViewById<Button>(R.id.start_button)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)

            if (running) {
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            } else setBaseTime()
        }



        startButton.setOnClickListener {

            Toast.makeText(this, stopwatch.base.toString(),Toast.LENGTH_SHORT).show()
            if (!running) {
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
            stopwatch.stop()
            running = false
        }
    }

    override fun onPause() {
        super.onPause()
        if (running){
            saveOffset()
            stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
    if(running){
        setBaseTime()
        stopwatch.start()
        offset=0
    }


    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY,offset)
        savedInstanceState.putBoolean(RUNNING_KEY,running)
        savedInstanceState.putLong(BASE_KEY,stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }



    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset

        Toast.makeText(this,
            "ofset is : $offset " +
                    "systemclock was : ${SystemClock.elapsedRealtime()}"
            ,Toast.LENGTH_SHORT).show()

    }
}