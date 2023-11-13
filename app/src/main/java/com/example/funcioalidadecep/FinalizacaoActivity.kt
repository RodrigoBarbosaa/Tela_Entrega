package com.example.funcioalidadecep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FinalizacaoActivity : AppCompatActivity() {

    lateinit var btnFinalizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalizacao2)

        btnFinalizar = findViewById(R.id.button_finalizar)
        btnFinalizar.setOnClickListener {
            finishAffinity()
        }
    }
}