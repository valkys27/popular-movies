package com.udacity.popularmovies.data.dao;

import com.udacity.popularmovies.pojo.Pojo;

import java.util.List;

/**
 * Created by tomas on 01.03.2018.
 */

public interface DAO<T extends Pojo> {
    List<T> getList(String selection, String[] selectionArgs, String sortOrder);
    T findById(int id);
    int insert(List<T> list);
    boolean update(T element);
}
