package com.example.janken.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.janken.model.GameRecord;

import java.util.ArrayList;
import java.util.List;

public class GameDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "janken.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "game_records";
    private static final int MAX_RECORDS = 20;

    public GameDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "playerHand TEXT," +
                "cpuHand TEXT," +
                "result TEXT," +
                "timestamp TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void insertRecord(GameRecord record) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("playerHand", record.getPlayerHand());
        cv.put("cpuHand", record.getCpuHand());
        cv.put("result", record.getResult());
        cv.put("timestamp", record.getTimestamp());
        db.insert(TABLE, null, cv);
        deleteOldRecords(db);
        db.close();
    }

    private void deleteOldRecords(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE + " WHERE id NOT IN " +
                "(SELECT id FROM " + TABLE + " ORDER BY id DESC LIMIT " + MAX_RECORDS + ")");
    }

    public List<GameRecord> getRecentRecords() {
        List<GameRecord> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE + " ORDER BY id DESC LIMIT " + MAX_RECORDS, null);
        if (cursor.moveToFirst()) {
            do {
                GameRecord r = new GameRecord();
                r.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                r.setPlayerHand(cursor.getString(cursor.getColumnIndexOrThrow("playerHand")));
                r.setCpuHand(cursor.getString(cursor.getColumnIndexOrThrow("cpuHand")));
                r.setResult(cursor.getString(cursor.getColumnIndexOrThrow("result")));
                r.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow("timestamp")));
                list.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
