package pl.dawidgdanski.tictactoe.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public final class TicTacToePoint implements Parcelable {


    public static final Creator<TicTacToePoint> CREATOR = new Creator<TicTacToePoint>() {
        @Override
        public TicTacToePoint createFromParcel(Parcel source) {
            return new TicTacToePoint(source.readInt(), source.readInt());
        }

        @Override
        public TicTacToePoint[] newArray(int size) {
            return new TicTacToePoint[size];
        }
    };

    private final int x;

    private final int y;

    private final int hashCode;

    private final String toString;

    public TicTacToePoint(int x, int y) {
        Preconditions.checkArgument(x >= 0 && x < TicTacToeGame.DIMENSION, "x index is incorrect");
        Preconditions.checkArgument(y >= 0 && y < TicTacToeGame.DIMENSION, "y index is incorrect");
        this.x = x;
        this.y = y;
        this.hashCode = Objects.hashCode(x, y);
        this.toString = Joiner.on(" ").join("[", String.valueOf(x), ",", String.valueOf(y), "]");
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(o == null || !(o instanceof TicTacToePoint)) {
            return false;
        }

        TicTacToePoint point = (TicTacToePoint) o;

        return Objects.equal(this.x, point.x) &&
                Objects.equal(this.y, point.y);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return toString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
    }
}
