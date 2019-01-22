package example.com.houzz.ticktacktoe.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var board  = Array(3) {IntArray(3)}

    private val moves = ArrayDeque<Move>(9)
    private val undoStack = ArrayDeque<Move>(9)

    private var turn = 1 // player1, player2

    private var boardPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var statePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        boardPaint.strokeWidth = 10f
        boardPaint.strokeCap = Paint.Cap.SQUARE
        boardPaint.color = Color.BLACK

        statePaint.strokeWidth = 20f
        statePaint.style = Paint.Style.STROKE
        statePaint.strokeCap = Paint.Cap.BUTT
        statePaint.color = Color.RED
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBoard(canvas)
        drawState(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        makeAMove(event?.x!!, event.y)
        return super.onTouchEvent(event)
    }

    private fun makeAMove(x: Float, y: Float) {
        val thirdH = height / 3
        val thirdW = width / 3
        val i: Int
        val j: Int

        j = when {
            x <= thirdW -> 0
            x <= thirdW * 2 -> 1
            else -> 2
        }
        i = when {
            y <= thirdH -> 0
            y <= thirdH * 2 -> 1
            else -> 2
        }
        addMove(i, j)
        logBoard()
    }

    private fun logBoard() {
        Log.d("Board", " ------ ")
        Log.d("Board", "|${board[0][0]} ${board[0][1]} ${board[0][2]}|")
        Log.d("Board", "|${board[1][0]} ${board[1][1]} ${board[1][2]}|")
        Log.d("Board", "|${board[2][0]} ${board[2][1]} ${board[2][2]}|")
        Log.d("Board", " ------ ")
        Log.d("Board", "${isWinner()}")
    }

    private fun addMove(i: Int, j: Int) {
       val move = Move(turn, i, j)
        if (!moves.contains(move)) {
            moves.push(move)
            board[i][j] = turn
            toggleTurn()
        }
        invalidate()
    }

    private fun drawBoard(canvas: Canvas) {

        canvas.save()

        canvas.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(), boardPaint)
        canvas.drawLine((width * 2 / 3).toFloat(), 0f, (width * 2 / 3).toFloat(), height.toFloat(), boardPaint)

        canvas.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(), boardPaint)
        canvas.drawLine(0f, (height * 2 / 3).toFloat(), width.toFloat(), (height * 2 / 3).toFloat(), boardPaint)

        canvas.restore()
    }

    private fun drawState(canvas: Canvas) {
        moves.forEach { move ->
            if (move.turn == 1) {
                drawX(canvas, move.i, move.j)
            } else {
                drawO(canvas, move.i, move.j)
            }
        }
    }

    private fun drawO(canvas: Canvas, i: Int, j: Int) {
        val cx = when (j) {
            0 -> {width / 6}
            1 -> {width / 2}
            2 -> {width * 5 / 6}
            else -> {width / 6}
        }
        val cy = when (i) {
            0 -> {height / 6}
            1 -> {height / 2}
            2 -> {height * 5 / 6}
            else -> {height / 6}
        }
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), (height / 12).toFloat(), statePaint)
    }


    private fun drawX(canvas: Canvas, i: Int, j: Int) {

        val path = Path()

        val x = when(j) {
            0 -> 0
            1 -> width / 3
            2 -> width * 2 / 3
            else -> 0
        }
        var y = when(i) {
            0 -> 0
            1 -> height / 3
            2 -> height * 2 / 3
            else -> 0
        }
        path.moveTo(x.toFloat(), y.toFloat())
        val toX = when(j) {
            0 -> width / 3
            1 -> width * 2 / 3
            2 -> width
            else -> width / 3
        }
        var toY = when(i) {
            0 -> height / 3
            1 -> height * 2 / 3
            2 -> height
            else -> height / 3
        }
        path.lineTo(toX.toFloat(), toY.toFloat())

        y = when(i) {
            0 -> height / 3
            1 -> height * 2 / 3
            2 -> height
            else -> 0
        }
        path.moveTo(x.toFloat(), y.toFloat())
        toY = when(i) {
            0 -> 0
            1 -> height / 3
            2 -> height * 2 /3
            else -> 0
        }
        path.lineTo(toX.toFloat(), toY.toFloat())
        canvas.drawPath(path, statePaint)
    }

    private fun isWinner():Boolean {
        if (board[0][0] > 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true
        }
        if (board[1][1] > 0 && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
            return true
        }

        board.forEachIndexed { i, ints ->
            board[i].forEachIndexed { j, item ->
                if (board[0][j] > 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                    return true
                }
            }
            if (board[i][0] > 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true
            }
        }
        return false
    }

    public fun storeState() {

    }

    public fun restoreState() {

    }

    private fun toggleTurn() {
        turn = if (turn == 1) 2 else 1
    }

    fun undo() {
        if (moves.size > 0) {
            val move = moves.pop()
            board[move.i][move.j] = 0
            undoStack.push(move)
            toggleTurn()
            invalidate()
        }
    }

    fun redo() {
        if (undoStack.size > 0) {
            val move = undoStack.pop()
            moves.push(move)
            toggleTurn()
            invalidate()
        }
    }

    fun reset() {
        moves.clear()
        board = Array(3) {IntArray(3)}
        turn  = 1
        invalidate()
    }


}