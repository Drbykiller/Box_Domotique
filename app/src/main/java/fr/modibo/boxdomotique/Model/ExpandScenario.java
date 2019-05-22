package fr.modibo.boxdomotique.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ExpandScenario implements Parcelable {

    public final String device;

    public ExpandScenario(String device) {
        this.device = device;
    }


    protected ExpandScenario(Parcel in) {
        device = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(device);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExpandScenario> CREATOR = new Creator<ExpandScenario>() {
        @Override
        public ExpandScenario createFromParcel(Parcel in) {
            return new ExpandScenario(in);
        }

        @Override
        public ExpandScenario[] newArray(int size) {
            return new ExpandScenario[size];
        }
    };
}
