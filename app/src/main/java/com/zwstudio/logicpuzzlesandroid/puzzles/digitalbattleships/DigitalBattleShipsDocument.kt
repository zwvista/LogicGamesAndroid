package com.zwstudio.logicpuzzlesandroid.puzzles.digitalbattleships

import com.zwstudio.logicpuzzlesandroid.common.data.GameDocument
import com.zwstudio.logicpuzzlesandroid.common.data.MoveProgress
import com.zwstudio.logicpuzzlesandroid.common.domain.Position
import org.androidannotations.annotations.EBean

@EBean
class DigitalBattleShipsDocument : GameDocument<DigitalBattleShipsGameMove>() {
    override fun saveMove(move: DigitalBattleShipsGameMove, rec: MoveProgress) {
        rec.row = move.p.row
        rec.col = move.p.col
        rec.intValue1 = move.obj.ordinal
    }

    override fun loadMove(rec: MoveProgress) =
        DigitalBattleShipsGameMove(Position(rec.row, rec.col), DigitalBattleShipsObject.values()[rec.intValue1])
}