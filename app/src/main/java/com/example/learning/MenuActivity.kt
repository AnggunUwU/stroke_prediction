package com.example.learning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnTentang = findViewById<CardView>(R.id.btnAbout)
        btnTentang.setOnClickListener {
            val intent = Intent(this, TentangActivity::class.java)
            startActivity(intent)
        }

        val btnDataset = findViewById<CardView>(R.id.btnDataset)
        btnDataset.setOnClickListener {
            val intent = Intent(this, DatasetActivity::class.java)
            startActivity(intent)
        }

        val btnFitur = findViewById<CardView>(R.id.btnFitur)
        btnFitur.setOnClickListener {
            val intent = Intent(this, FiturActivity::class.java)
            startActivity(intent)
        }

        val btnModel = findViewById<CardView>(R.id.btnModel)
        btnModel.setOnClickListener {
            val intent = Intent(this, ModelActivity::class.java)
            startActivity(intent)
        }

        val btnSimulasiModel = findViewById<CardView>(R.id.btnSimulasi)
        btnSimulasiModel.setOnClickListener {
            val intent = Intent(this, SimulasiModelActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}