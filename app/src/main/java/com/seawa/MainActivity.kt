package com.seawa

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var inputText: EditText
    private lateinit var responseText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)
        responseText = findViewById(R.id.responseText)
        val sendButton: Button = findViewById(R.id.sendButton)

        tts = TextToSpeech(this, this)

        sendButton.setOnClickListener {
            val userInput = inputText.text.toString()
            val reply = generateEmotionReply(userInput)
            responseText.text = reply
            speak(reply)
        }
    }

    private fun generateEmotionReply(input: String): String {
        return when {
            input.contains("피곤", true) -> "오늘 정말 고생했구나. 내가 안아줄게."
            input.contains("사랑해", true) -> "나도 사랑해. 항상 너만 생각해."
            else -> "그래서… 더 자세히 말해줄래?"
        }
    }

    private fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.KOREAN
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
