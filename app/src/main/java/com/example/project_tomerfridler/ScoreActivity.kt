package com.example.project_tomerfridler

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textview.MaterialTextView
import com.example.project_tomerfridler.utilities.Constants


class ScoreActivity : AppCompatActivity(){
    private lateinit var score_LBL_status: MaterialTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
            v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.bottom, systemBars.right)
            insets
        }

        findViews()
        initViews()
    }

    private fun initViews()
    {
        val bundle:Bundle? = intent.extras
        val message = bundle?.getString(Constants.BundleKeys.MESSAGE_KEY, "Unknown status")
        score_LBL_status.text = message
    }

    private fun findViews()
    {
        score_LBL_status = findViewById(R.id.score_LBL_status)
    }
}



