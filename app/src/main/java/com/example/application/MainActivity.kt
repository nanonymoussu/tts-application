package com.example.application

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSoundButtons()
    }

    private fun setupSoundButtons() {
        val soundButtonIds = listOf(
            R.id.btn1 to R.raw.sound1,
            R.id.btn2 to R.raw.sound2,
            R.id.btn3 to R.raw.sound3,
            R.id.btn4 to R.raw.sound4,
            R.id.btn5 to R.raw.sound5
        )

        soundButtonIds.forEach { (buttonId, soundId) ->
            findViewById<Button>(buttonId).setOnClickListener {
                playSound(soundId)
            }
        }
    }

    private fun playSound(soundResourceId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, soundResourceId).apply {
            setOnCompletionListener { release() }
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}