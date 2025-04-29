package com.example.project_tomerfridler

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.indices
import androidx.core.view.setMargins
import com.example.project_tomerfridler.logic.GameManager
import com.google.android.material.button.MaterialButton
import com.example.project_tomerfridler.utilities.Constants
import com.example.project_tomerfridler.utilities.SignalManager

class MainActivity : AppCompatActivity() {

    private lateinit var main_GRID_Matrix: GridLayout

    private lateinit var main_IMG_TigerMatrix: Array<Array<AppCompatImageView>>

    val handler: Handler = Handler(Looper.getMainLooper())

    private lateinit var main_BTN_left: MaterialButton

    private lateinit var main_BTN_right: MaterialButton

    private lateinit var main_IMG_hearts: Array<AppCompatImageView>

    private lateinit var gameManager: GameManager


    private val gameTickRunnable = object : Runnable {
        override fun run() {
            val beforeWrong = gameManager.wrongAnswers

            gameManager.move()
            refreshMatrixUi()
            refreshUI()

            if (gameManager.wrongAnswers > beforeWrong) {
                SignalManager.getInstance().vibrate()
                SignalManager.getInstance().toast("Crash")
            }

            if (gameManager.isGameOver) {
                changeActivity("Game over!")
            } else {
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()

        val numCol = 3
        val numRow = 4

        gameManager = GameManager(numRow, numCol,3)
        main_GRID_Matrix.removeAllViews()

        initMatrixUi()
        initViews()
        gameManager.move()
        refreshMatrixUi()
        handler.postDelayed(gameTickRunnable, 1000)
    }

    private fun initMatrixUi()
    {
        val numRow = gameManager.getMatrix().size
        val numCol = gameManager.getMatrix()[0].size

        main_IMG_TigerMatrix = Array(numRow) { row ->
            Array(numCol) { col ->
                val img = AppCompatImageView(this)
                img.layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    columnSpec = GridLayout.spec(col, 1f)
                    rowSpec = GridLayout.spec(row, 1f)
                    setMargins(4, 4, 4, 4)
                }
                img.scaleType = ImageView.ScaleType.FIT_CENTER
                img.adjustViewBounds = true
                img.setImageResource(0)
                main_GRID_Matrix.addView(img)
                img
            }
        }
    }

    private fun refreshMatrixUi() {
        val matrix = gameManager.getMatrix()

        for (row in matrix.indices) {
            for (col in matrix[0].indices) {
                val image = main_IMG_TigerMatrix[row][col]
                when (matrix[row][col]) {
                    0 -> {
                        image.setImageResource(0)
                        image.visibility = View.INVISIBLE
                    }
                    1 -> {
                        image.setImageResource(R.drawable.tiger_svgrepo_com)
                        image.visibility = View.VISIBLE
                    }
                    2 -> {
                        image.setImageResource(R.drawable.elk_svgrepo_com)
                        image.visibility = View.VISIBLE
                    }
                    3 -> {
                        image.setImageResource(R.drawable.elk_svgrepo_com)
                        image.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun findViews() {
        main_BTN_right = findViewById(R.id.main_BTN_right)
        main_BTN_left = findViewById(R.id.main_BTN_left)
        main_GRID_Matrix = findViewById(R.id.main_GRID_Matrix)
        main_IMG_hearts = arrayOf(
            findViewById(R.id.main_IMG_heart0),
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2)
        )
    }


    private fun refreshUI() {
       for(i in main_IMG_hearts.indices)
       {
           main_IMG_hearts[i].visibility = if( i < 3 - gameManager.wrongAnswers) View.VISIBLE else View.INVISIBLE
       }
    }

    private fun changeActivity(message: String) {
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra(Constants.BundleKeys.MESSAGE_KEY, message)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        main_BTN_right.setOnClickListener {
            gameManager.moveRight()
            refreshMatrixUi()
        }
        main_BTN_left.setOnClickListener {
            gameManager.moveLeft()
            refreshMatrixUi()
        }
        refreshMatrixUi()
    }


    override fun onDestroy() {
        handler.removeCallbacks(gameTickRunnable)
        super.onDestroy()
    }

}