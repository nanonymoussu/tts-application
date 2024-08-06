package com.example.application

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var isEnglish = true

    private lateinit var buttons: List<Button>
    private lateinit var buttonTexts: List<Pair<String, String>>
    private lateinit var soundResources: List<Pair<Int, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSoundButtons()
        setupLanguageToggle()
    }

    private fun initializeViews() {
        buttons = listOf(
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5
        ).map { findViewById(it) }

        buttonTexts = resources.getStringArray(R.array.button_texts)
            .map { it.split("|") }
            .map { it[0] to it[1] }

        soundResources = listOf(
            R.raw.sound1_en to R.raw.sound1_th,
            R.raw.sound2_en to R.raw.sound2_th,
            R.raw.sound3_en to R.raw.sound3_th,
            R.raw.sound4_en to R.raw.sound4_th,
            R.raw.sound5_en to R.raw.sound5_th
        )
    }

    private fun setupSoundButtons() {
        buttons.zip(soundResources).forEach { (button, soundPair) ->
            button.setOnClickListener {
                playSound(if (isEnglish) soundPair.first else soundPair.second)
            }
        }
    }

    private fun setupLanguageToggle() {
        findViewById<Button>(R.id.buttonToggleLanguage).apply {
            text = getString(R.string.button_toggle_language)
            setOnClickListener {
                isEnglish = !isEnglish
                updateButtonTexts()
            }
        }
        updateButtonTexts()
    }

    private fun updateButtonTexts() {
        buttons.zip(buttonTexts).forEach { (button, texts) ->
            button.text = if (isEnglish) texts.first else texts.second
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