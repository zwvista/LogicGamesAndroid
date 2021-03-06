package com.zwstudio.logicpuzzlesandroid.puzzles.skyscrapers

import com.zwstudio.logicpuzzlesandroid.common.data.GameDocumentInterface
import com.zwstudio.logicpuzzlesandroid.common.domain.CellsGame
import com.zwstudio.logicpuzzlesandroid.common.domain.GameInterface
import com.zwstudio.logicpuzzlesandroid.common.domain.Position

class SkyscrapersGame(layout: List<String>, gi: GameInterface<SkyscrapersGame, SkyscrapersGameMove, SkyscrapersGameState>, gdi: GameDocumentInterface) : CellsGame<SkyscrapersGame, SkyscrapersGameMove, SkyscrapersGameState>(gi, gdi) {
    companion object {
        var offset = arrayOf(
            Position(-1, 0),
            Position(0, 1),
            Position(1, 0),
            Position(0, -1)
        )
    }

    var objArray: IntArray
    fun intMax() = rows - 2

    operator fun get(row: Int, col: Int) = objArray[row * cols + col]
    operator fun get(p: Position) = this[p.row, p.col]
    operator fun set(row: Int, col: Int, obj: Int) {objArray[row * cols + col] = obj}
    operator fun set(p: Position, obj: Int) {this[p.row, p.col] = obj}
    override fun isValid(row: Int, col: Int) = row >= 1 && col >= 1 && row < size.row - 1 && col < size.col - 1

    init {
        size = Position(layout.size, layout[0].length)
        objArray = IntArray(rows * cols)
        for (r in 0 until rows) {
            val str = layout[r]
            for (c in 0 until cols) {
                val ch = str[c]
                val n = if (ch == ' ') 0 else ch - '0'
                this[r, c] = n
            }
        }
        val state = SkyscrapersGameState(this)
        levelInitilized(state)
    }

    fun getObject(p: Position) = currentState[p]
    fun getObject(row: Int, col: Int) = currentState[row, col]
    fun getState(row: Int, col: Int) = currentState.getState(row, col)
}
