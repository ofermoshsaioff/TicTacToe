package example.com.houzz.ticktacktoe.ui.main

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var boardPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        boardPaint.strokeWidth = 10f
        boardPaint.strokeCap = Paint.Cap.SQUARE
        boardPaint.color = Color.BLACK
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBoard(canvas)
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
    }

    private fun addMove(i: Int, j: Int) {
        Toast.makeText(context, "pressed on tile i=$i j=$j", Toast.LENGTH_SHORT).show()
    }

    private fun drawBoard(canvas: Canvas) {

        canvas.save()

        canvas.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(), boardPaint)
        canvas.drawLine((width * 2 / 3).toFloat(), 0f, (width * 2 / 3).toFloat(), height.toFloat(), boardPaint)

        canvas.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(), boardPaint)
        canvas.drawLine(0f, (height * 2 / 3).toFloat(), width.toFloat(), (height * 2 / 3).toFloat(), boardPaint)

        canvas.restore()
    }
}