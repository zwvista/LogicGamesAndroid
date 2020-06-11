package com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.android

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameHelpActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameMainActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameOptionsActivity
import com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.android.NoughtsAndCrossesGameActivity_
import com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.android.NoughtsAndCrossesOptionsActivity_
import com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.data.NoughtsAndCrossesDocument
import com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.domain.NoughtsAndCrossesGame
import com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.domain.NoughtsAndCrossesGameMove
import com.zwstudio.logicpuzzlesandroid.puzzles.noughtsandcrosses.domain.NoughtsAndCrossesGameState
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_main)
class NoughtsAndCrossesMainActivity : GameMainActivity<NoughtsAndCrossesGame, NoughtsAndCrossesDocument, NoughtsAndCrossesGameMove, NoughtsAndCrossesGameState>() {
    @Bean
    protected lateinit var document: NoughtsAndCrossesDocument
    override fun doc() = document

    @Click
    fun btnOptions() {
        NoughtsAndCrossesOptionsActivity_.intent(this).start()
    }

    protected override fun resumeGame() {
        doc().resumeGame()
        NoughtsAndCrossesGameActivity_.intent(this).start()
    }
}

@EActivity(R.layout.activity_game_options)
class NoughtsAndCrossesOptionsActivity : GameOptionsActivity<NoughtsAndCrossesGame, NoughtsAndCrossesDocument, NoughtsAndCrossesGameMove, NoughtsAndCrossesGameState>() {
    @Bean
    protected lateinit var document: NoughtsAndCrossesDocument
    override fun doc() = document
}

@EActivity(R.layout.activity_game_help)
class NoughtsAndCrossesHelpActivity : GameHelpActivity<NoughtsAndCrossesGame, NoughtsAndCrossesDocument, NoughtsAndCrossesGameMove, NoughtsAndCrossesGameState>() {
    @Bean
    protected lateinit var document: NoughtsAndCrossesDocument
    override fun doc() = document
}