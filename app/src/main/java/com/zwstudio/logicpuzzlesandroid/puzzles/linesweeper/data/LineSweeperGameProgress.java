package com.zwstudio.logicpuzzlesandroid.puzzles.linesweeper.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zwstudio.logicpuzzlesandroid.common.data.GameProgress;

/**
 * Created by zwvista on 2016/09/29.
 */

@DatabaseTable
public class LineSweeperGameProgress extends GameProgress {
    @DatabaseField
    public int markerOption;
}
