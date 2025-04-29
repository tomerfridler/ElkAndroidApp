package com.example.project_tomerfridler.logic

class GameManager(
    private val numRows: Int = 4,
    private val numCols: Int = 3,
    private val lifeCount:Int = 3
){

    private val matrix = Array(numRows) { IntArray(numCols) { 0 } }

    private var theFirstMove = true

    var elkCol: Int = numCols / 2
        private set

    var wrongAnswers: Int = 0
        private set

    val isGameOver: Boolean
        get() = wrongAnswers >= lifeCount

    fun moveLeft() {
        if(elkCol > 0) elkCol--
        }

    fun moveRight() {
        if(elkCol < numCols - 1) elkCol++
    }

    fun move() {
        if (!theFirstMove) {
            down()
            checkIfCrash()
        }
        theFirstMove = false
        updateElkMat()
    }

    private fun down()
    {
        for(row in numRows - 1 downTo 1)
        {
            for(col in 0 until  numCols)
            {
                matrix[row][col] = matrix[row - 1][col]
            }
        }
        for(col in 0 until numCols)
        {
            matrix[0][col] = 0
        }

        val randomC = (0 until numCols).random()
        matrix[0][randomC] = 1
    }


    private fun checkIfCrash() {
        if (matrix[numRows - 1][elkCol] == 1) {
            wrongAnswers++
            matrix[numRows - 1][elkCol] = 3
        }
    }

    private fun updateElkMat(){
        for (col in 0 until numCols)
        {
            if(matrix[numRows - 1][col] == 2)
                matrix[numRows - 1][col] = 0
        }
        if(matrix[numRows - 1][elkCol] == 0)
            matrix[numRows - 1][elkCol] = 2
    }


    fun getMatrix(): Array<IntArray> = matrix

}
