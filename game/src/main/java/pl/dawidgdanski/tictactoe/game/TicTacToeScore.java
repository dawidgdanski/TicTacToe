package pl.dawidgdanski.tictactoe.game;


import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

final class TicTacToeScore implements Comparable<TicTacToeScore> {

    private final int score;

    private final TicTacToePoint point;

    private final int hashCode;

    public TicTacToeScore(int score, TicTacToePoint point) {
        this.score = score;
        this.point = point;
        this.hashCode = Objects.hashCode(score, point);
    }

    public int getScore() {
        return score;
    }

    public TicTacToePoint getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(o == null || !(o instanceof TicTacToeScore)) {
            return false;
        }

        TicTacToeScore obj = (TicTacToeScore) o;

        return Objects.equal(this.score, obj.score) &&
                Objects.equal(this.point, obj.point);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public int compareTo(TicTacToeScore another) {
        return ComparisonChain.start()
                .compare(this.score , another.score)
                .result();
    }
}
