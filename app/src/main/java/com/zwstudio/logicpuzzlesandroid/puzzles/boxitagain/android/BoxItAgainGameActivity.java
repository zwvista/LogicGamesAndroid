package com.zwstudio.logicpuzzlesandroid.puzzles.boxitagain.android;

import com.zwstudio.logicpuzzlesandroid.R;
import com.zwstudio.logicpuzzlesandroid.common.android.GameActivity;
import com.zwstudio.logicpuzzlesandroid.common.data.MoveProgress;
import com.zwstudio.logicpuzzlesandroid.puzzles.boxitagain.data.BoxItAgainDocument;
import com.zwstudio.logicpuzzlesandroid.puzzles.boxitagain.domain.BoxItAgainGame;
import com.zwstudio.logicpuzzlesandroid.puzzles.boxitagain.domain.BoxItAgainGameMove;
import com.zwstudio.logicpuzzlesandroid.puzzles.boxitagain.domain.BoxItAgainGameState;

import org.androidannotations.annotations.EActivity;

import java.util.List;

@EActivity(R.layout.activity_boxitagain_game)
public class BoxItAgainGameActivity extends GameActivity<BoxItAgainGame, BoxItAgainDocument, BoxItAgainGameMove, BoxItAgainGameState> {
    public BoxItAgainDocument doc() {return app.boxitagainDocument;}

    protected void startGame() {
        String selectedLevelID = doc().selectedLevelID;
        List<String> layout = doc().levels.get(selectedLevelID);
        tvLevel.setText(selectedLevelID);
        updateSolutionUI();

        levelInitilizing = true;
        game = new BoxItAgainGame(layout, this);
        try {
            // restore game state
            for (MoveProgress rec : doc().moveProgress()) {
                BoxItAgainGameMove move = doc().loadMove(rec);
                game.setObject(move);
            }
            int moveIndex = doc().levelProgress().moveIndex;
            if (!(moveIndex >= 0 && moveIndex < game.moveCount())) return;
            while (moveIndex != game.moveIndex())
                game.undo();
        } finally {
            levelInitilizing = false;
        }
    }
}