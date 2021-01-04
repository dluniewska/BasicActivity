package com.example.basicactivity.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.os.IResultReceiver;

public class DatabaseDescription  {

    /*Nazwa obiektu ContentPrivider - zwykle nazwa pakietu*/
    public static final String AUTHORITY = "com.example.basicactivity.data";

    /* Adres URI używany do nawiązania interakcji z obiektem ContentProvider*/

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private DatabaseDescription() {}

    public static final class Item implements BaseColumns {

        /* Nazwa tabeli */

        public static final String TABLE_NAME = "items";

        /* Adres tabeli */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        /* Nazwy kolumn tabeli */
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_ZIP = "zip";

        /* Metoda do tworzenia adresu dla nowego itemu*/
        public static Uri buildItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
