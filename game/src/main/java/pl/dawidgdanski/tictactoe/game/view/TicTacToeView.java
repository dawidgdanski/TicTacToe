package pl.dawidgdanski.tictactoe.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import pl.dawidgdanski.tictactoe.game.TicTacToeGame;
import pl.dawidgdanski.tictactoe.game.TicTacToeGameContract;
import pl.dawidgdanski.tictactoe.game.TicTacToePoint;
import pl.dawidgdanski.tictactoe.game.interfaces.OnGameStateChangeListener;

public final class TicTacToeView extends AbstractView implements View.OnTouchListener,
        TicTacToeGameContract {

    private final int RADIUS = 100;

    private Paint boardPaint;

    private Paint crossPaint;

    private Paint circlePaint;

    private Paint gameWonPaint;

    private int radius = -1;

    private OnGameStateChangeListener onGameStateChangeListener = NULL_LISTENER;

    private Map<TicTacToePoint, Point> pointsMap;

    private TicTacToeGame game = new TicTacToeGame();

    private TicTacToeGame.Player turn;

    public TicTacToeView(Context context) {
        super(context);
        initializeView();
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    public void setOnGameStateChangeListener(final OnGameStateChangeListener listener) {
        this.onGameStateChangeListener = listener == null ? NULL_LISTENER : listener;
    }

    private void initializeView() {
        setMinimumHeight(RADIUS);
        setMinimumWidth(RADIUS);
        setOnTouchListener(this);
        setSaveEnabled(true);

        boardPaint = new Paint();
        boardPaint.setColor(Color.BLACK);
        boardPaint.setStrokeWidth(20);

        crossPaint = new Paint();
        crossPaint.setColor(Color.BLACK);
        crossPaint.setStrokeWidth(15);

        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStrokeWidth(15);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);

        gameWonPaint = new Paint();
        gameWonPaint.setColor(Color.GREEN);
        gameWonPaint.setStrokeWidth(10);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return onSaveInstanceStateStandard();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        onRestoreInstanceStateStandard(state);
    }

    @Override
    protected int getMaximumViewWidth() {
        return RADIUS;
    }

    @Override
    protected int getMinimumViewWidth() {
        return getSuggestedMinimumWidth();
    }

    @Override
    protected int getMaximumViewHeight() {
        return RADIUS;
    }

    @Override
    protected int getMinimumViewHeight() {
        return getSuggestedMinimumHeight();
    }

    public void setTurn(TicTacToeGame.Player player) {
        this.turn = player;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int width = getWidth() - getPaddingRight() - getPaddingLeft();

        final int height = getHeight() - getPaddingBottom() - getPaddingTop();

        final int minEdge = Math.min(width, height);

        final int edge = minEdge / TicTacToeGame.DIMENSION;

        final int spaceX = (width - 3 * edge) / 2;

        radius = edge / 3;

        final int x = (int) getX() + getPaddingLeft() + spaceX;

        final int y = (int) getY() + getPaddingTop();

        drawBoard(edge, x, y, canvas);

        final Map<TicTacToePoint, Point> pointsMap = getPointsMap(x, y, edge);

        for (TicTacToePoint point : getXStates()) {
            drawCross(pointsMap.get(point), radius, canvas);
        }

        for (TicTacToePoint point : getOStates()) {
            drawCircle(pointsMap.get(point), radius, canvas);
        }

        final boolean hasOWon = hasOWon();

        final boolean hasXWon = hasXWon();

        final boolean isDraw = isDraw();

        if (isDraw) {
            onGameStateChangeListener.onDraw();
            return;
        }

        if (hasOWon || hasXWon) {
            setClickable(false);

            List<TicTacToePoint> points = game.getWinningPoints();

            Point point1 = pointsMap.get(points.get(0));
            Point point2 = pointsMap.get(points.get(1));
            Point point3 = pointsMap.get(points.get(2));

            canvas.drawLine(point1.x, point1.y, point2.x, point2.y, gameWonPaint);
            canvas.drawLine(point2.x, point2.y, point3.x, point3.y, gameWonPaint);

            if (hasOWon) {
                onGameStateChangeListener.onOWin();
            } else {
                onGameStateChangeListener.onXWin();
            }
        }
    }

    private void drawBoard(final int edge,
                           final int x,
                           final int y,
                           final Canvas canvas) {

        final int verticalEndY = y + 3 * edge;

        final int rightVerticalLineX = x + 2 * edge;

        final int leftVerticalLineX = x + edge;

        //vertical lines
        canvas.drawLine(leftVerticalLineX, y, leftVerticalLineX, verticalEndY, boardPaint);
        canvas.drawLine(rightVerticalLineX, y, rightVerticalLineX, verticalEndY, boardPaint);

        final int horizontalEndX = x + 3 * edge;

        final int horizontalUpperLineY = edge;

        final int horizontalLowerLineY = 2 * edge;

        //horizontal lines
        canvas.drawLine(x, horizontalUpperLineY, horizontalEndX, horizontalUpperLineY, boardPaint);
        canvas.drawLine(x, horizontalLowerLineY, horizontalEndX, horizontalLowerLineY, boardPaint);
    }

    private Map<TicTacToePoint, Point> getPointsMap() {
        return pointsMap;
    }

    private Map<TicTacToePoint, Point> getPointsMap(final int x, final int y, final int edge) {

        if (pointsMap == null) {
            Point point00 = new Point(x + edge / 2, y + edge / 2);
            Point point01 = new Point(point00.x + edge, point00.y);
            Point point02 = new Point(point00.x + 2 * edge, point00.y);

            Point point10 = new Point(x + edge / 2, y + edge * 3 / 2);
            Point point11 = new Point(point10.x + edge, point10.y);
            Point point12 = new Point(point10.x + 2 * edge, point10.y);

            Point point20 = new Point(x + edge / 2, edge * 5 / 2);
            Point point21 = new Point(point20.x + edge, point20.y);
            Point point22 = new Point(point20.x + 2 * edge, point20.y);

            pointsMap = ImmutableMap.<TicTacToePoint, Point>builder()
                    .put(new TicTacToePoint(0, 0), point00)
                    .put(new TicTacToePoint(0, 1), point01)
                    .put(new TicTacToePoint(0, 2), point02)
                    .put(new TicTacToePoint(1, 0), point10)
                    .put(new TicTacToePoint(1, 1), point11)
                    .put(new TicTacToePoint(1, 2), point12)
                    .put(new TicTacToePoint(2, 0), point20)
                    .put(new TicTacToePoint(2, 1), point21)
                    .put(new TicTacToePoint(2, 2), point22)
                    .build();
        }

        return pointsMap;
    }

    private void drawCross(final Point point, final int radius, Canvas canvas) {

        final float[] points = new float[]{
                point.x - radius, point.y - radius,
                point.x + radius, point.y + radius,
                point.x - radius, point.y + radius,
                point.x + radius, point.y - radius};

        canvas.drawLines(points, crossPaint);
    }

    private void drawCircle(final Point point, final int radius, Canvas canvas) {

        canvas.drawCircle(point.x, point.y, radius, circlePaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        Map<TicTacToePoint, Point> pointsMap = getPointsMap();
        if (pointsMap == null) {
            return true;
        }

        if (isGameOver()) {
            return true;
        }

        final int x = (int) event.getX();
        final int y = (int) event.getY();

        Point point = new Point(x, y);

        TicTacToePoint pointFound = PointFinder.findPointIfWithinRadiusFromTouhcPoint(getPointsMap(), point, radius);

        if (pointFound != null) {
            performMove(pointFound, turn);
        }

        return true;
    }

    @Override
    public List<TicTacToePoint> getAvailableStates() {
        return game.getAvailableStates();
    }

    @Override
    public List<TicTacToePoint> getXStates() {
        return game.getXStates();
    }

    @Override
    public List<TicTacToePoint> getOStates() {
        return game.getOStates();
    }

    @Override
    public boolean isGameOver() {
        return game.isGameOver();
    }

    @Override
    public boolean isDraw() {
        return game.isDraw();
    }

    @Override
    public boolean hasXWon() {
        return game.hasXWon();
    }

    @Override
    public boolean hasOWon() {
        return game.hasOWon();
    }

    @Override
    public void performMove(TicTacToePoint point, TicTacToeGame.Player player) {
        game.performMove(point, player);
        turn = getOpponentForPlayer(turn);
        if (!isGameOver()) {
            onGameStateChangeListener.onPlayerTurn(turn);
        }
        invalidate();
    }

    @Override
    public TicTacToeGame.Player getOpponentForPlayer(TicTacToeGame.Player player) {
        return game.getOpponentForPlayer(player);
    }

    @Override
    public TicTacToePoint getBestMove(TicTacToeGame.Player player) {
        return game.getBestMove(player);
    }

    @Override
    public List<TicTacToePoint> getWinningPoints() {
        return game.getWinningPoints();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onGameStateChangeListener = NULL_LISTENER;
    }

    private void onRestoreInstanceStateStandard(final Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        this.game = savedState.game;
    }

    private Parcelable onSaveInstanceStateStandard() {
        final Parcelable parcelable = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parcelable);
        savedState.game = game;

        return savedState;
    }

    public void performCpuMove() {
        if (!isGameOver()) {
            performMove(getBestMove(turn), turn);
        }
    }

    public void performFirstRandomMove(TicTacToePoint point) {
        Preconditions.checkNotNull(point);

        if (!isGameOver()) {
            performMove(point, turn);
        }
    }

    private static class SavedState extends BaseSavedState {

        private TicTacToeGame game;

        private SavedState(Parcel source) {
            super(source);
            game = source.readParcelable(null);
        }

        private SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(game, 0);
        }
    }

    public static Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

        @Override
        public SavedState createFromParcel(Parcel source) {
            return new SavedState(source);
        }

        @Override
        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };

    private static OnGameStateChangeListener NULL_LISTENER = new OnGameStateChangeListener() {
        @Override
        public void onPlayerTurn(TicTacToeGame.Player player) {

        }

        @Override
        public void onXWin() {

        }

        @Override
        public void onOWin() {

        }

        @Override
        public void onDraw() {

        }
    };
}
