package pl.dawidgdanski.tictactoe.assertions;

import android.os.Parcel;
import android.os.Parcelable;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

abstract class AbstractTestAssert<S extends GenericAssert, A> extends GenericAssert<S, A> {

    protected AbstractTestAssert(A actual, Class<S> selfType) {
        super(selfType, actual);
    }

    protected Parcelable.Creator<A> getCreator() {
        throw new UnsupportedOperationException("System under test does not have Creator implementation");
    }

    private S doesImplementsParcelable() {
        isNotNull();

        Assertions.assertThat(actual).overridingErrorMessage(String.format("%s does not implement Parcelable interface", actual.getClass())).isInstanceOf(Parcelable.class);

        return myself;
    }

    public S canBeParceled() {
        isNotNull();
        doesImplementsParcelable();

        Parcel parcel = Parcel.obtain();

        Parcelable SUT = (Parcelable) actual;

        SUT.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        S recreatedSUT = (S) getCreator().createFromParcel(parcel);

        Assertions.assertThat(actual).isEqualTo(recreatedSUT);

        return myself;
    }





}
