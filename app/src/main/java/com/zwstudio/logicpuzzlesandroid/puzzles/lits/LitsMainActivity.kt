package com.zwstudio.logicpuzzlesandroid.puzzles.lits

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameHelpActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameMainActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameOptionsActivity
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_main)
class LitsMainActivity : GameMainActivity<LitsGame, LitsDocument, LitsGameMove, LitsGameState>() {
    @Bean
    protected lateinit var document: LitsDocument
    override val doc get() = document

    @Click
    fun btnOptions() {
        LitsOptionsActivity_.intent(this).start()
    }

    override fun resumeGame() {
        doc.resumeGame()
        LitsGameActivity_.intent(this).start()
    }
}

@EActivity(R.layout.activity_game_options)
class LitsOptionsActivity : GameOptionsActivity<LitsGame, LitsDocument, LitsGameMove, LitsGameState>() {
    @Bean
    protected lateinit var document: LitsDocument
    override val doc get() = document
}

@EActivity(R.layout.activity_game_help)
class LitsHelpActivity : GameHelpActivity<LitsGame, LitsDocument, LitsGameMove, LitsGameState>() {
    @Bean
    protected lateinit var document: LitsDocument
    override val doc get() = document
}