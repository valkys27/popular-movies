package com.udacity.popularmovies.pojo;

import com.google.gson.annotations.*;

import org.chalup.microorm.annotations.Column;

/**
 * Created by tomas on 01.03.2018.
 */

public abstract class Pojo {

    @Column("_id")
    protected int _id;

    @Column("id")
    @Expose
    @SerializedName("id")
    protected int serverId;

    public Pojo(int serverId) {
        this.serverId = serverId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }
}
