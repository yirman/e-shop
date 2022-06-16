package com.german.eshop.customer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.german.eshop.customer.databinding.ActivityLoginBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val modelManager = RemoteModelManager.getInstance()

// Get translation models stored on the device.
        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                models.forEach {
                    Log.e("modelo", it.language)
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.SPANISH)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        val englishTranslator = Translation.getClient(options)
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        englishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                englishTranslator.translate("Crema para afeitar")
                    .addOnSuccessListener { translatedText ->
                        Log.e("jeje", translatedText)
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}