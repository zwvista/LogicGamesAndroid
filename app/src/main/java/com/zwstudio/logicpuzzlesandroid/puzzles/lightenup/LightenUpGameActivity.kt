package com.zwstudio.logicpuzzlesandroid.puzzles.lightenup

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameGameActivity
import com.zwstudio.logicpuzzlesandroid.common.data.GameLevel
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_game)
class LightenUpGameActivity : GameGameActivity<LightenUpGame, LightenUpDocument, LightenUpGameMove, LightenUpGameState>() {
    @Bean
    protected lateinit var document: LightenUpDocument
    override val doc get() = document

    @AfterViews
    override fun init() {
        gameView = LightenUpGameView(this)
        super.init()
    }

    override fun newGame(level: GameLevel) =
        LightenUpGame(level.layout, this, doc)

    @Click
    protected fun btnHelp() {
        LightenUpHelpActivity_.intent(this).start()
    }
}