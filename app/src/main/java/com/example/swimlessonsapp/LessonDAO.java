package com.example.swimlessonsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LessonDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public LessonDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteLesson(long id) {
        database.delete(DatabaseHelper.TABLE_LESSONS,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public long addLesson(String date, String time, String coach, String child) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_COACH, coach);
        values.put(DatabaseHelper.COLUMN_CHILD, child);
        return database.insert(DatabaseHelper.TABLE_LESSONS, null, values);
    }

    public Lesson getLessonById(long id) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_LESSONS,
                null,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Lesson lesson = new Lesson(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COACH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CHILD))
            );
            cursor.close();
            return lesson;
        }
        return null;
    }

    public int updateLesson(long id, String date, String time, String coach, String child) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_DATE, date);
        values.put(DatabaseHelper.COLUMN_TIME, time);
        values.put(DatabaseHelper.COLUMN_COACH, coach);
        values.put(DatabaseHelper.COLUMN_CHILD, child);
        return database.update(DatabaseHelper.TABLE_LESSONS, values,
                DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public List<Lesson> getAllLessons() {
        List<Lesson> lessonList = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_LESSONS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Lesson lesson = new Lesson(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COACH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CHILD))
                );
                lessonList.add(lesson);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lessonList;
    }
}