package com.zwstudio.logicpuzzlesandroid.puzzles.kropki

import com.zwstudio.logicpuzzlesandroid.common.data.GameDocument
import com.zwstudio.logicpuzzlesandroid.common.data.MoveProgress
import com.zwstudio.logicpuzzlesandroid.common.domain.Position
import org.androidannotations.annotations.EBean

@EBean
class KropkiDocument : GameDocument<KropkiGameMove>() {
    override fun saveMove(move: KropkiGameMove, rec: MoveProgress) {
        rec.row = move.p.row
        rec.col = move.p.col
        rec.intValue1 = move.obj
    }

    override fun loadMove(rec: MoveProgress) =
        KropkiGameMove(Position(rec.row, rec.col), rec.intValue1)
}