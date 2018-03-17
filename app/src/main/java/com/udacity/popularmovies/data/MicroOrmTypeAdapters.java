package com.udacity.popularmovies.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.chalup.microorm.TypeAdapter;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by tomas on 01.03.2018.
 */

public class MicroOrmTypeAdapters {

    public static class LocalDateAdapter implements TypeAdapter<LocalDate> {

        @Override
        public LocalDate fromCursor(Cursor c, String columnName) {
            return LocalDate.parse(c.getString(c.getColumnIndex(columnName)), DateTimeFormatter.ISO_DATE);
        }

        @Override
        public void toContentValues(ContentValues values, String columnName, LocalDate object) {
            values.put(columnName, object.format(DateTimeFormatter.ISO_DATE));
        }
    }
}
