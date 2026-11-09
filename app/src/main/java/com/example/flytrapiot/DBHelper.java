package com.example.flytrapiot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    static final String DATABASE_NAME = "flytrap_db";
    static final int DATABASE_VERSION = 1;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tabla = "CREATE TABLE trampas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "codigo_sector TEXT NOT NULL, " +
                "tipo_trampa TEXT NOT NULL, " +
                "nivel_captura INTEGER NOT NULL, " +
                "observaciones TEXT)";
        db.execSQL(tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS trampas");
        onCreate(db);
    }
}