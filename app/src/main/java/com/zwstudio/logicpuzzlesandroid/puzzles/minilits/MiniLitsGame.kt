package com.zwstudio.logicpuzzlesandroid.puzzles.minilits

import com.zwstudio.logicpuzzlesandroid.common.data.GameDocumentInterface
import com.zwstudio.logicpuzzlesandroid.common.domain.*

class MiniLitsGame(layout: List<String>, gi: GameInterface<MiniLitsGame, MiniLitsGameMove, MiniLitsGameState>, gdi: GameDocumentInterface) : CellsGame<MiniLitsGame, MiniLitsGameMove, MiniLitsGameState>(gi, gdi) {
    companion object {
        var offset = arrayOf(
            Position(-1, 0),
            Position(0, 1),
            Position(1, 0),
            Position(0, -1))
        var offset2 = arrayOf(
            Position(0, 0),
            Position(1, 1),
            Position(1, 1),
            Position(0, 0))
        var dirs = intArrayOf(1, 0, 3, 2)
        var offset3 = arrayOf(
            Position(0, 0),
            Position(0, 1),
            Position(1, 0),
            Position(1, 1))
        var triominos = arrayOf(
            arrayOf(Position(0, 0), Position(0, 1), Position(1, 0)),
            arrayOf(Position(0, 0), Position(0, 1), Position(1, 1)),
            arrayOf(Position(0, 0), Position(1, 0), Position(1, 1)),
            arrayOf(Position(0, 1), Position(1, 0), Position(1, 1)),
            arrayOf(Position(0, 0), Position(0, 1), Position(0, 2)),
            arrayOf(Position(0, 0), Position(1, 0), Position(2, 0))
        )
    }

    var areas = mutableListOf<List<Position>>()
    var pos2area = mutableMapOf<Position, Int>()
    var dots: GridDots
    var treesInEachArea = 1

    init {
        size = Position(layout.size / 2, layout[0].length / 2)
        dots = GridDots(rows + 1, cols + 1)
        for (r in 0 until rows + 1) {
            var str = layout[r * 2]
            for (c in 0 until cols) {
                val ch = str[c * 2 + 1]
                if (ch == '-') {
                    dots[r, c, 1] = GridLineObject.Line
                    dots[r, c + 1, 3] = GridLineObject.Line
                }
            }
            if (r == rows) break
            str = layout[r * 2 + 1]
            for (c in 0 until cols + 1) {
                val ch = str[c * 2]
                if (ch == '|') {
                    dots[r, c, 2] = GridLineObject.Line
                    dots[r + 1, c, 0] = GridLineObject.Line
                }
            }
        }
        val rng = mutableSetOf<Position>()
        val g = Graph()
        val pos2node = mutableMapOf<Position, Node>()
        for (r in 0 until rows)
            for (c in 0 until cols) {
                val p = Position(r, c)
                rng.add(+p)
                val node = Node(p.toString())
                g.addNode(node)
                pos2node[p] = node
            }
        for (r in 0 until rows)
            for (c in 0 until cols) {
                val p = Position(r, c)
                for (i in 0 until 4)
                    if (dots[p + offset2[i], dirs[i]] != GridLineObject.Line)
                        g.connectNode(pos2node[p]!!, pos2node[p + offset[i]]!!)
            }
        while (rng.isNotEmpty()) {
            g.rootNode = pos2node[rng.first()]!!
            val nodeList = g.bfs()
            val area = rng.filter { nodeList.contains(pos2node[it]) }
            val n = areas.size
            for (p in area)
                pos2area[p] = n
            areas.add(area)
            rng.removeAll(area)
        }
        val state = MiniLitsGameState(this)
        levelInitilized(state)
    }

    fun getObject(p: Position) = currentState[p]
    fun getObject(row: Int, col: Int) = currentState[row, col]
    fun pos2State(p: Position) = currentState.pos2state[p]
}
