package pl.dawidgdanski.tictactoe.game.view;

import android.graphics.Point;

import java.util.Map;

import pl.dawidgdanski.tictactoe.game.TicTacToePoint;

final class PointFinder {

    private PointFinder () { }

    static TicTacToePoint findPointIfWithinRadiusFromTouhcPoint(
            final Map<TicTacToePoint, Point> pointsMap,
            final Point touchPoint,
            final int radius) {

        TicTacToePoint choice = null;

        final int touchX = touchPoint.x;
        final int touchY = touchPoint.y;

        for(Map.Entry<TicTacToePoint, Point> pointEntry : pointsMap.entrySet()) {
            Point point = pointEntry.getValue();

            double distance = Math.sqrt(Math.pow(touchX - point.x, 2) + Math.pow(touchY - point.y, 2));
            if(distance < radius) {
                choice = pointEntry.getKey();
            }
        }

        return choice;
    }

}
