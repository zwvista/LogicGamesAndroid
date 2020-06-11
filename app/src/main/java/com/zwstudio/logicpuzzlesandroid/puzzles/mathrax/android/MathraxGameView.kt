package com.zwstudio.logicpuzzlesandroid.puzzles.mathrax.android

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.zwstudio.logicpuzzlesandroid.common.domain.Position

_
class MathraxGameView : CellsGameView {
    private fun activity() = getContext() as MathraxGameActivity

    private fun game() = activity().game

    private fun rows() = if (isInEditMode()) 5 else game().rows()

    private fun cols() = if (isInEditMode()) 5 else game().cols()

    protected override fun rowsInView() = rows()

    protected override fun colsInView() = cols()

    private val gridPaint = Paint()
    private val textPaint: TextPaint = TextPaint()
    private val hintPaint = Paint()
    private val mathPaint1 = Paint()
    private val mathPaint2 = Paint()

    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        gridPaint.color = Color.GRAY
        gridPaint.style = Paint.Style.STROKE
        textPaint.setAntiAlias(true)
        hintPaint.style = Paint.Style.FILL
        hintPaint.strokeWidth = 5f
        mathPaint1.style = Paint.Style.STROKE
        mathPaint1.color = Color.WHITE
        mathPaint2.style = Paint.Style.FILL
        mathPaint2.color = Color.BLACK
    }

    protected override fun onDraw(canvas: Canvas) {
//        canvas.drawColor(Color.BLACK);
        for (r in 0 until rows()) for (c in 0 until cols()) {
            canvas.drawRect(cwc(c).toFloat(), chr(r).toFloat(), cwc(c + 1).toFloat(), chr(r + 1).toFloat(), gridPaint)
            if (isInEditMode()) continue
            val n: Int = game().getObject(r, c)
            if (n == 0) continue
            val text = n.toString()
            textPaint.setColor(if (game().get(r, c) == n) Color.GRAY else Color.WHITE)
            drawTextCentered(text, cwc(c), chr(r), canvas, textPaint)
        }
        if (isInEditMode()) return
        for (r in 0 until rows()) {
            val s: HintState = game().getRowState(r)
            if (s == HintState.Normal) continue
            hintPaint.color = if (s == HintState.Complete) Color.GREEN else Color.RED
            val c = cols() - 1
            canvas.drawArc(cwc(c + 1) - 20.toFloat(), chr2(r) - 20.toFloat(), cwc(c + 1) + 20.toFloat(), chr2(r) + 20.toFloat(), 0f, 360f, true, hintPaint)
        }
        for (c in 0 until cols()) {
            val s: HintState = game().getColState(c)
            if (s == HintState.Normal) continue
            hintPaint.color = if (s == HintState.Complete) Color.GREEN else Color.RED
            val r = rows() - 1
            canvas.drawArc(cwc2(c) - 20.toFloat(), chr(r + 1) - 20.toFloat(), cwc2(c) + 20.toFloat(), chr(r + 1) + 20.toFloat(), 0f, 360f, true, hintPaint)
        }
        for ((p, value) in game().pos2hint.entries) {
            val r = p.row + 1
            val c = p.col + 1
            canvas.drawArc(cwc(c) - cellWidth / 4.toFloat(), chr(r) - cellHeight / 4.toFloat(), cwc(c) + cellWidth / 4.toFloat(), chr(r) + cellHeight / 4.toFloat(), 0f, 360f, true, mathPaint1)
            canvas.drawArc(cwc(c) - cellWidth / 4.toFloat(), chr(r) - cellHeight / 4.toFloat(), cwc(c) + cellWidth / 4.toFloat(), chr(r) + cellHeight / 4.toFloat(), 0f, 360f, true, mathPaint2)
            val text: String = value.toString()
            val s: HintState = game().getPosState(p)
            textPaint.setColor(
                if (s == HintState.Complete) Color.GREEN else if (s == HintState.Error) Color.RED else Color.WHITE
            )
            drawTextCentered(text, cwc(c) - cellWidth / 4, chr(r) - cellHeight / 4, cellWidth / 2, cellHeight / 2, canvas, textPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !game().isSolved()) {
            val col = (event.getX() / cellWidth) as Int
            val row = (event.getY() / cellHeight) as Int
            if (col >= cols() || row >= rows()) return true
            val move: MathraxGameMove = object : MathraxGameMove() {
                init {
                    p = Position(row, col)
                    obj = 0
                }
            }
            if (game().switchObject(move)) activity().app.soundManager.playSoundTap()
        }
        return true
    }
}