package com.zwstudio.logicpuzzlesandroid.puzzles.tatamino.android

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameHelpActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameMainActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameOptionsActivity
import com.zwstudio.logicpuzzlesandroid.puzzles.tatamino.data.TataminoDocument
import com.zwstudio.logicpuzzlesandroid.puzzles.tatamino.domain.TataminoGame
import com.zwstudio.logicpuzzlesandroid.puzzles.tatamino.domain.TataminoGameMove
import com.zwstudio.logicpuzzlesandroid.puzzles.tatamino.domain.TataminoGameState
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_main)
class TataminoMainActivity : GameMainActivity<TataminoGame, TataminoDocument, TataminoGameMove, TataminoGameState>() {
    @Bean
    protected lateinit var document: TataminoDocument
    override fun doc() = document

    @Click
    fun btnOptions() {
        TataminoOptionsActivity_.intent(this).start()
    }

    protected override fun resumeGame() {
        doc().resumeGame()
        TataminoGameActivity_.intent(this).start()
    }
}

@EActivity(R.layout.activity_game_options)
class TataminoOptionsActivity : GameOptionsActivity<TataminoGame, TataminoDocument, TataminoGameMove, TataminoGameState>() {
    @Bean
    protected lateinit var document: TataminoDocument
    override fun doc() = document
}

@EActivity(R.layout.activity_game_help)
class TataminoHelpActivity : GameHelpActivity<TataminoGame, TataminoDocument, TataminoGameMove, TataminoGameState>() {
    @Bean
    protected lateinit var document: TataminoDocument
    override fun doc() = document
}