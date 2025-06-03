package com.example.project_tomerfridler.logic

class GameManager(
    private val rowsNum: Int = 6,
    private val colsNum: Int = 5,
    private val lifeCount: Int = 3,
) {

    private val theMatrix = Array(rowsNum) { IntArray(colsNum) { 0 } }

    var userMoved: Boolean = false

    private var firstMove = true

    var carCol: Int = colsNum / 2
        private set

    var wrongAnswers: Int = 0
        private set

    var score: Int = 0
        private set

    var collectedCoin: Boolean = false

    val isGameOver: Boolean
        get() = wrongAnswers >= lifeCount

    var distance: Int = 0
        private set

    fun increaseDistanceBy(units: Int) {
        distance += units
    }

    fun mRight() {
        if (carCol < colsNum - 1) carCol++
    }

    fun mLeft() {
        if (carCol > 0) carCol--
    }

    fun gameMove() {
        if (!firstMove) {
            if (!userMoved) {
                mDown()
            }
            checkIsStop()
        }
        firstMove = false
        updateCarInMatrix()
        userMoved = false
    }


    fun getMatrix(): Array<IntArray> = theMatrix

    private fun checkIsStop() {
        when (theMatrix[rowsNum - 1][carCol]) {
            1 -> {
                wrongAnswers++
                theMatrix[rowsNum - 1][carCol] = 3
            }
            4 -> {
                score += 10
                theMatrix[rowsNum - 1][carCol] = 0
                collectedCoin = true  //
            }
        }
    }

    private fun mDown() {
        for (row in rowsNum - 1 downTo 1) {
            for (col in 0 until colsNum) {
                theMatrix[row][col] = theMatrix[row - 1][col]
            }
        }

        for (col in 0 until colsNum) {
            theMatrix[0][col] = 0
        }

        val randomCol = (0 until colsNum).random()
        val objectType = if ((0..1).random() == 0) 1 else 4
        theMatrix[0][randomCol] = objectType
    }

    private fun updateCarInMatrix() {
        for (col in 0 until colsNum) {
            if (theMatrix[rowsNum - 1][col] == 2) {
                theMatrix[rowsNum - 1][col] = 0
            }
        }
        if (theMatrix[rowsNum - 1][carCol] == 0) {
            theMatrix[rowsNum - 1][carCol] = 2
        }
    }

    fun resetScore(){
        score = 0
    }
}
