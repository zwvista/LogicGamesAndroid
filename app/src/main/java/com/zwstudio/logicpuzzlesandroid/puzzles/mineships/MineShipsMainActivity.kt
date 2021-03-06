package com.zwstudio.logicpuzzlesandroid.puzzles.mineships

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameHelpActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameMainActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameOptionsActivity
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_main)
class MineShipsMainActivity : GameMainActivity<MineShipsGame, MineShipsDocument, MineShipsGameMove, MineShipsGameState>() {
    @Bean
    protected lateinit var document: MineShipsDocument
    override val doc get() = document

    @Click
    fun btnOptions() {
        MineShipsOptionsActivity_.intent(this).start()
    }

    protected override fun resumeGame() {
        doc.resumeGame()
        MineShipsGameActivity_.intent(this).start()
    }
}

@EActivity(R.layout.activity_game_options)
class MineShipsOptionsActivity : GameOptionsActivity<MineShipsGame, MineShipsDocument, MineShipsGameMove, MineShipsGameState>() {
    @Bean
    protected lateinit var document: MineShipsDocument
    override val doc get() = document
}

@EActivity(R.layout.activity_game_help)
class MineShipsHelpActivity : GameHelpActivity<MineShipsGame, MineShipsDocument, MineShipsGameMove, MineShipsGameState>() {
    @Bean
    protected lateinit var document: MineShipsDocument
    override val doc get() = document
}