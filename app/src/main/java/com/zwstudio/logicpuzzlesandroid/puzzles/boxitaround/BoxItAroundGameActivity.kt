package com.zwstudio.logicpuzzlesandroid.puzzles.boxitaround

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameGameActivity
import com.zwstudio.logicpuzzlesandroid.common.data.GameLevel
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_game)
class BoxItAroundGameActivity : GameGameActivity<BoxItAroundGame, BoxItAroundDocument, BoxItAroundGameMove, BoxItAroundGameState>() {
    @Bean
    protected lateinit var document: BoxItAroundDocument
    override val doc get() = document

    @AfterViews
    override fun init() {
        gameView = BoxItAroundGameView(this)
        super.init()
    }

    override fun newGame(level: GameLevel) =
        BoxItAroundGame(level.layout, this, doc)

    @Click
    protected fun btnHelp() {
        BoxItAroundHelpActivity_.intent(this).start()
    }
}