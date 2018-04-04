package com.udacity.popularmovies.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.*;

import org.chalup.microorm.annotations.Column;

/**
 * Created by tomas on 01.03.2018.
 */

public abstract class Pojo implements Parcelable {

    @Column("_id")
    private int _id;

    @Column("id")
    @Expose
    @SerializedName("id")
    private String serverId;

    Pojo(String serverId) {
        this.serverId = serverId;
    }

    Pojo(Parcel in) {
        this._id = in.readInt();
        this.serverId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(serverId);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
