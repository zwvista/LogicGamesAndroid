package com.zwstudio.logicpuzzlesandroid.puzzles.fenceitup.data

import com.zwstudio.logicpuzzlesandroid.common.data.GameDocument
import com.zwstudio.logicpuzzlesandroid.common.data.MoveProgress
import com.zwstudio.logicpuzzlesandroid.common.domain.GridLineObject
import com.zwstudio.logicpuzzlesandroid.common.domain.Position
import com.zwstudio.logicpuzzlesandroid.puzzles.fenceitup.domain.FenceItUpGame
import com.zwstudio.logicpuzzlesandroid.puzzles.fenceitup.domain.FenceItUpGameMove
import org.androidannotations.annotations.EBean

@EBean
class FenceItUpDocument : GameDocument<FenceItUpGame, FenceItUpGameMove>() {
    override fun saveMove(move: FenceItUpGameMove, rec: MoveProgress) {
        rec.row = move.p.row
        rec.col = move.p.col
        rec.intValue1 = move.dir
        rec.intValue2 = move.obj.ordinal
    }

    override fun loadMove(rec: MoveProgress) =
        FenceItUpGameMove(Position(rec.row, rec.col), rec.intValue1, GridLineObject.values()[rec.intValue2])
}