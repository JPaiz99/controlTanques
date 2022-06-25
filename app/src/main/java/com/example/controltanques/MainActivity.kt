package com.example.controltanques

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        escanear.setOnClickListener(){
            val intent:Intent = Intent(this,dispositivos_encontrados::class.java)
            startActivity(intent)
        }

    }
}