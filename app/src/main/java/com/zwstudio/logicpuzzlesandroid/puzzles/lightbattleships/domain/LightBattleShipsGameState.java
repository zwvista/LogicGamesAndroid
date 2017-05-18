package com.zwstudio.logicpuzzlesandroid.puzzles.lightbattleships.domain;

import com.zwstudio.logicpuzzlesandroid.common.domain.CellsGameState;
import com.zwstudio.logicpuzzlesandroid.common.domain.Graph;
import com.zwstudio.logicpuzzlesandroid.common.domain.MarkerOptions;
import com.zwstudio.logicpuzzlesandroid.common.domain.Node;
import com.zwstudio.logicpuzzlesandroid.common.domain.Position;
import com.zwstudio.logicpuzzlesandroid.home.domain.HintState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fj.F;
import fj.data.Array;

import static fj.data.Array.arrayArray;
import static fj.data.HashMap.fromMap;
import static fj.data.List.iterableList;
import static fj.function.Integers.add;

/**
 * Created by zwvista on 2016/09/29.
 */

public class LightBattleShipsGameState extends CellsGameState<LightBattleShipsGame, LightBattleShipsGameMove, LightBattleShipsGameState> {
    public LightBattleShipsObject[] objArray;
    public Map<Position, HintState> pos2state = new HashMap<>();

    public LightBattleShipsGameState(LightBattleShipsGame game) {
        super(game);
        objArray = new LightBattleShipsObject[rows() * cols()];
        Arrays.fill(objArray, new LightBattleShipsEmptyObject());
        for (Map.Entry<Position, Integer> entry : game.pos2hint.entrySet()) {
            Position p = entry.getKey();
            LightBattleShipsHintObject o = new LightBattleShipsHintObject();
            set(p, o);
        }
        for (Map.Entry<Position, LightBattleShipsObject> entry : game.pos2obj.entrySet()) {
            Position p = entry.getKey();
            LightBattleShipsObject o = entry.getValue();
            set(p, o);
        }
        updateIsSolved();
    }

    public LightBattleShipsObject get(int row, int col) {
        return objArray[row * cols() + col];
    }
    public LightBattleShipsObject get(Position p) {
        return get(p.row, p.col);
    }
    public void set(int row, int col, LightBattleShipsObject obj) {
        objArray[row * cols() + col] = obj;
    }
    public void set(Position p, LightBattleShipsObject obj) {
        set(p.row, p.col, obj);
    }

    public boolean setObject(LightBattleShipsGameMove move) {
        Position p = move.p;
        if (!isValid(p) || game.pos2obj.containsKey(p) || get(p) == move.obj) return false;
        set(p, move.obj);
        updateIsSolved();
        return true;
    }

    public boolean switchObject(LightBattleShipsGameMove move) {
        MarkerOptions markerOption = MarkerOptions.values()[game.gdi.getMarkerOption()];
        F<LightBattleShipsObject, LightBattleShipsObject> f = obj -> {
            if(obj instanceof LightBattleShipsEmptyObject)
                return markerOption == MarkerOptions.MarkerFirst ?
                        new LightBattleShipsMarkerObject() : new LightBattleShipsBattleShipUnitObject();
            else if(obj instanceof LightBattleShipsBattleShipUnitObject)
                return new LightBattleShipsBattleShipMiddleObject();
            else if(obj instanceof LightBattleShipsBattleShipMiddleObject)
                return new LightBattleShipsBattleShipLeftObject();
            else if(obj instanceof LightBattleShipsBattleShipLeftObject)
                return new LightBattleShipsBattleShipTopObject();
            else if(obj instanceof LightBattleShipsBattleShipTopObject)
                return new LightBattleShipsBattleShipRightObject();
            else if(obj instanceof LightBattleShipsBattleShipRightObject)
                return new LightBattleShipsBattleShipBottomObject();
            else if(obj instanceof LightBattleShipsBattleShipBottomObject)
                return markerOption == MarkerOptions.MarkerLast ?
                        new LightBattleShipsMarkerObject() : new LightBattleShipsEmptyObject();
            else if(obj instanceof LightBattleShipsMarkerObject)
                return markerOption == MarkerOptions.MarkerFirst ?
                        new LightBattleShipsBattleShipUnitObject() : new LightBattleShipsEmptyObject();
            return obj;
        };
        Position p = move.p;
        if (!isValid(p)) return false;
        move.obj = f.f(get(p));
        return setObject(move);
    }

    /*
        iOS Game: Logic Games/Puzzle Set 1/Battle Ships

        Summary
        Play solo Battleships, with the help of the numbers on the border.

        Description
        1. Standard rules of Battleships apply, but you are guessing the other
           player ships disposition, by using the numbers on the borders.
        2. Each number tells you how many ship or ship pieces you're seeing in
           that row or column.
        3. Standard rules apply: a ship or piece of ship can't touch another,
           not even diagonally.
        4. In each puzzle there are
           1 Aircraft Carrier (4 squares)
           2 Destroyers (3 squares)
           3 Submarines (2 squares)
           4 Patrol boats (1 square)

        Variant
        5. Some puzzle can also have a:
           1 Supertanker (5 squares)
    */
    private void updateIsSolved() {
        boolean allowedObjectsOnly = game.gdi.isAllowedObjectsOnly();
        isSolved = true;
        for (int r = 0; r < rows(); r++)
            for (int c = 0; c < cols(); c++)
                if (get(r, c) instanceof LightBattleShipsForbiddenObject)
                    set(r, c, new LightBattleShipsEmptyObject());
        for (int r = 0; r < rows(); r++)
            for (int c = 0; c < cols(); c++) {
                Position p = new Position(r, c);
                F<Boolean, Boolean> hasNeighbor = isHint -> {
                    for (Position os : LightBattleShipsGame.offset) {
                        Position p2 = p.add(os);
                        if (!isValid(p2)) continue;
                        LightBattleShipsObject o = get(p2);
                        if (o instanceof LightBattleShipsHintObject) {
                            if (!isHint) return true;
                        } else if (o instanceof LightBattleShipsBattleShipTopObject || o instanceof LightBattleShipsBattleShipBottomObject ||
                                o instanceof LightBattleShipsBattleShipLeftObject || o instanceof LightBattleShipsBattleShipRightObject ||
                                o instanceof LightBattleShipsBattleShipMiddleObject || o instanceof LightBattleShipsBattleShipUnitObject) {
                            if (isHint) return true;
                        }
                    }
                    return false;
                };
                LightBattleShipsObject o = get(r, c);
                if (o instanceof LightBattleShipsHintObject) {
                    LightBattleShipsHintObject o2 = (LightBattleShipsHintObject)o;
                    o2.state = !hasNeighbor.f(true) ? HintState.Normal : HintState.Error;
                } else if ((o instanceof LightBattleShipsEmptyObject || o instanceof LightBattleShipsMarkerObject) && allowedObjectsOnly && hasNeighbor.f(false))
                    set(r, c, new LightBattleShipsForbiddenObject());
            }
        for (Map.Entry<Position, Integer> entry : game.pos2hint.entrySet()) {
            Position p = entry.getKey();
            int n2 = entry.getValue();
            Integer nums[] = {0, 0, 0, 0};
            List<Position> rng = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Position os = LightBattleShipsGame.offset[i * 2];
                for (Position p2 = p.add(os); game.isValid(p2); p2.addBy(os)) {
                    LightBattleShipsObject o = get(p2);
                    if (o instanceof LightBattleShipsEmptyObject)
                        rng.add(p2.plus());
                    else if (o instanceof LightBattleShipsBattleShipTopObject || o instanceof LightBattleShipsBattleShipBottomObject ||
                            o instanceof LightBattleShipsBattleShipLeftObject || o instanceof LightBattleShipsBattleShipRightObject ||
                            o instanceof LightBattleShipsBattleShipMiddleObject || o instanceof LightBattleShipsBattleShipUnitObject)
                        nums[i]++;
                }
            }
            int n1 = arrayArray(nums).foldLeft(add, 0);
            pos2state.put(p, n1 < n2 ? HintState.Normal : n1 == n2 ? HintState.Complete : HintState.Error);
            if (n1 != n2) isSolved = false;
            else if(allowedObjectsOnly)
                for (Position p2 : rng)
                    set(p2, new LightBattleShipsForbiddenObject());
        }
        if (!isSolved) return;
        Graph g = new Graph();
        Map<Position, Node> pos2node = new HashMap<>();
        for (int r = 0; r < rows(); r++)
            for (int c = 0; c < cols(); c++) {
                Position p = new Position(r, c);
                LightBattleShipsObject o = get(p);
                if (o instanceof LightBattleShipsBattleShipTopObject || o instanceof LightBattleShipsBattleShipBottomObject ||
                        o instanceof LightBattleShipsBattleShipLeftObject || o instanceof LightBattleShipsBattleShipRightObject ||
                        o instanceof LightBattleShipsBattleShipMiddleObject || o instanceof LightBattleShipsBattleShipUnitObject) {
                    Node node = new Node(p.toString());
                    g.addNode(node);
                    pos2node.put(p, node);
                }
            }
        for (Map.Entry<Position, Node> entry : pos2node.entrySet()) {
            Position p = entry.getKey();
            Node node = entry.getValue();
            for (Position os : LightBattleShipsGame.offset) {
                Position p2 = p.add(os);
                if (pos2node.containsKey(p2))
                    g.connectNode(node, pos2node.get(p2));
            }
        }
        Integer[] shipNumbers = {0, 0, 0, 0, 0};
        Integer[] shipNumbers2 = {0, 4, 3, 2, 1};
        while (!pos2node.isEmpty()) {
            g.setRootNode(iterableList(pos2node.values()).head());
            List<Node> nodeList = g.bfs();
            List<Position> area = fromMap(pos2node).toStream().filter(e -> nodeList.contains(e._2())).map(e -> e._1()).toJavaList();
            for (Position p : area)
                pos2node.remove(p);
            area.sort(Position::compareTo);
            if (!(area.size() == 1 && get(area.get(0)) instanceof LightBattleShipsBattleShipUnitObject ||
                    area.size() > 1 && area.size() < 5 && ((
                    iterableList(area).forall(p -> p.row == area.get(0).row) &&
                    get(area.get(0)) instanceof LightBattleShipsBattleShipLeftObject &&
                    get(area.get(area.size() - 1)) instanceof LightBattleShipsBattleShipRightObject ||
                    iterableList(area).forall(p -> p.col == area.get(0).col) &&
                    get(area.get(0)) instanceof LightBattleShipsBattleShipTopObject &&
                    get(area.get(area.size() - 1)) instanceof LightBattleShipsBattleShipBottomObject) &&
                    Array.range(1, area.size() - 2).forall(i -> get(area.get(i)) instanceof LightBattleShipsBattleShipMiddleObject)) &&
                    arrayArray(LightBattleShipsGame.offset2).forall(os -> iterableList(area).forall(p -> {
                        Position p2 = p.add(os);
                        if (!isValid(p2)) return true;
                        LightBattleShipsObject o = get(p2);
                        return o instanceof LightBattleShipsEmptyObject || o instanceof LightBattleShipsForbiddenObject || o instanceof LightBattleShipsMarkerObject || o instanceof LightBattleShipsHintObject;
                    })))) {isSolved = false; return;}
            shipNumbers[area.size()]++;
        }
        if (!Arrays.equals(shipNumbers, shipNumbers2)) isSolved = false;
    }
}
