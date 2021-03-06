package com.zwstudio.logicpuzzlesandroid.puzzles.masyu

import com.zwstudio.logicpuzzlesandroid.R
import com.zwstudio.logicpuzzlesandroid.common.android.GameHelpActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameMainActivity
import com.zwstudio.logicpuzzlesandroid.common.android.GameOptionsActivity
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.Click
import org.androidannotations.annotations.EActivity

@EActivity(R.layout.activity_game_main)
class MasyuMainActivity : GameMainActivity<MasyuGame, MasyuDocument, MasyuGameMove, MasyuGameState>() {
    @Bean
    protected lateinit var document: MasyuDocument
    override val doc get() = document

    @Click
    fun btnOptions() {
        MasyuOptionsActivity_.intent(this).start()
    }

    protected override fun resumeGame() {
        doc.resumeGame()
        MasyuGameActivity_.intent(this).start()
    }
}

@EActivity(R.layout.activity_game_options)
class MasyuOptionsActivity : GameOptionsActivity<MasyuGame, MasyuDocument, MasyuGameMove, MasyuGameState>() {
    @Bean
    protected lateinit var document: MasyuDocument
    override val doc get() = document

    protected fun onDefault() {}
}

@EActivity(R.layout.activity_game_help)
class MasyuHelpActivity : GameHelpActivity<MasyuGame, MasyuDocument, MasyuGameMove, MasyuGameState>() {
    @Bean
    protected lateinit var document: MasyuDocument
    override val doc get() = document
}