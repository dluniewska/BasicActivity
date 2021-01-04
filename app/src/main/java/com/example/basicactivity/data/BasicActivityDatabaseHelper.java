package com.example.basicactivity.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BasicActivityDatabaseHelper extends SQLiteOpenHelper {

    /* Nazwa bazy danych*/
    private static final String DATABASE_NAME = "ItemBook.db";

    /* Wersja bazy danych */
    private static final int DATABASE_VERSION = 1;

    /* Konstruktor tworzący bazę danych*/
    public BasicActivityDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /* Zapytanie SQL tworzące tabelę*/
        final String CREATE_ITEMS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Item.TABLE_NAME +
                        "(" + DatabaseDescription.Item._ID + " integer primary key, "
                        + DatabaseDescription.Item.COLUMN_NAME + " TEXT, "
                        + DatabaseDescription.Item.COLUMN_PHONE + " TEXT, "
                        + DatabaseDescription.Item.COLUMN_EMAIL + " TEXT, "
                        + DatabaseDescription.Item.COLUMN_STREET + " TEXT, "
                        + DatabaseDescription.Item.COLUMN_CITY + " TEXT, "
                        + DatabaseDescription.Item.COLUMN_STATE + " TEXT, "
                        + DatabaseDescription.Item.COLUMN_ZIP + " TEXT);";

        /* Egzekucja zapytania SQL */
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion) {}
}
