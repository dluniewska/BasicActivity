package com.example.basicactivity.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.basicactivity.R;

public class BasicActivityContentProvider extends ContentProvider {

    /* Egzemplarz klasy - umożliwka obiektowi ContentProvider uzyskanie dostępu do bazy danych */
    private BasicActivityDatabaseHelper dbHelper;

    /* Pomocnik obiektu ContentProvider */
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /* Stałe obiektu UriMatcher używane w celu określenia operacji do wykonania na bazie danych */
    private static final int ONE_ITEM = 1; // Wykonanie operacji dla jednego itema
    private static final int ITEMS = 2; // Wykonanie operacji dla całej tabeli kontaktów


    /* Konfiguracja obiektu UriMatcher */
    static {
        /* Adres Uri Itemki o określonym ientyfikatorze */
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, DatabaseDescription.Item.TABLE_NAME + "/#", ONE_ITEM);

        /*Adres Uri dla całej tabeli Itemków */
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, DatabaseDescription.Item.TABLE_NAME, ITEMS);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //Przyjmuje wartość 1, jeżeli usuwanie przebiegło pomyślnie, w przeciwnym razie 0
        int numberOfRowsDeleted;

        // Sprawdza adres URi
        switch (uriMatcher.match(uri)) {
            case ONE_ITEM:

                //Odczytywanie identyfikatowa kontaktu, ktory ma zostać usunięty
                String id = uri.getLastPathSegment();

                //Skasowanie itemki
                numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(DatabaseDescription.Item.TABLE_NAME, DatabaseDescription.Item._ID + "=" + id, selectionArgs);

                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_delete_uri) + uri);

        }
        //Jeżeli dokonano usunięcia, to powiadom obiekty nasłuchujące zmian w bazie danych
        if (numberOfRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        //Zwróć info o usunięciu
        return  numberOfRowsDeleted;

    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Deklaracja biektu Uri
        Uri newItemUri = null;

        //Sprawdzenie czy adress Uri odwołuje się do tabeli Items
        switch (uriMatcher.match(uri)) {
            case ITEMS:

                // Wstawienie nowego kontaktu do tabeli
                long rowId = dbHelper.getWritableDatabase().insert(DatabaseDescription.Item.TABLE_NAME, null, values);

                // Tworzenie adresu  Uri dla dodanego itemka
                // Jeżeli dodanie się powiodło
                if (rowId > 0) {
                    newItemUri = DatabaseDescription.Item.buildItemUri(rowId);

                    // Powiadomienie obiektów nasłuchujących zmian w tabeli
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                 // Jeżeli dodanie się nie powiodło
                 else {
                    throw new SQLException(getContext().getString(R.string.insert_failed) + uri);
                 }
                 break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_insert_uri) + uri);

        }
        //Zwrócenie adresu URI
        return newItemUri;
    }

    @Override
    public boolean onCreate() {
        // Utworzenie obiektu BasicActivityDatabaseHelper
        dbHelper = new BasicActivityDatabaseHelper(getContext());

        // Operacja utworzenia obiektu ContentProvider została zakończona sukcesem
        return true;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Obiekt SQLitQueryBuilder służący do tworzenia zapytań SQL"
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables((DatabaseDescription.Item.TABLE_NAME));

        // Wybranie jednego lub wszystkich Itemów z tabeli
        switch (uriMatcher.match(uri)) {
            case ONE_ITEM:
                queryBuilder.appendWhere(DatabaseDescription.Item._ID + "=" + uri.getLastPathSegment());
                break;
            case ITEMS:
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_query_uri) + uri);
        }

        // Wykonanie zapytania SQL i inicjalizacja obiektu Cursor
        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);

        // Konfiguracja obiektu Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Zwrócenie obiektu Cursor
        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        // Przyjmuje wartość 1, jeżeli aktualizacja przebiegła pomyślnie, inaczej 0
        int numberOfRowsUpdated;

        // Sprawdza adres Uri
        switch (uriMatcher.match(uri)) {
            case ONE_ITEM:

                // Odczytanie identyfikatora kontaktu, który ma zostać zaktualizowany
                String id = uri.getLastPathSegment();

                //Aktualizacja zawartości Itemku
                numberOfRowsUpdated = dbHelper.getWritableDatabase().update(DatabaseDescription.Item.TABLE_NAME, values, DatabaseDescription.Item._ID + "=" + id, selectionArgs );
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_update_uri) + uri);

        }
        //Jeżeli dokonano akualizacji, to powiadom obiekty nasłuchujące zmian w bazie danych
        if (numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);

         //Zwróć info o  aktualizacji

        }
        return numberOfRowsUpdated;
    }
}
