package com.example.basicactivity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicactivity.data.DatabaseDescription;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ItemsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // Metody wywołania zwrotnego implementowanne przez klasę MainActivity
    public interface ItemsFragmentListener {

        //Wywołanie w wyniku wybrania kontaktu
        void onItemSelected(Uri itemUri);

        //Wywołanie w wyniku dotknięcia przycisku dodawania (+)
        void onAddItem();
    }

    //Identyfikator obiektu Loader
    private static final int ITEMS_LOADER = 0;

    // Obiekt informujący aktywność MainActivity o wybraniu kontaktu
    private  ItemsFragmentListener listener;

    //Adapter obiektu RecyclerView
    private ItemsAdapter itemsAdapter;


    public ItemsFragment() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        super.onCreateView(inflater, container,savedInstanceState);
        setHasOptionsMenu(true);

        //przygotowanie do wyświetlenia graficznego interfejsu użytkownika
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        //Uzyskanie odwołania do widoku RecyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // Konfiguracja widoku RecyclerView - widok powinien wyświetlać elementy w formie pionowej listy
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        itemsAdapter = new ItemsAdapter(new ItemsAdapter.ItemClickListener() {
            @Override
                    public void onClick(Uri itemUri) {
                listener.onItemSelected(itemUri);
            }
        });

        //Ustawienie adaptera widoku RecyclerView
        recyclerView.setAdapter(itemsAdapter);

        //  Dołączenie spersonalizowanego obiektu ItemDivider
        recyclerView.addItemDecoration(new ItemDivider(getContext()));

        //Rozmiar widoku RecyclerView nie ulega zmianie
        recyclerView.setHasFixedSize(true);

        //Inicjalizacja i konfiguracja przycisku dodawnaia kontaktów
        FloatingActionButton addbutton = (FloatingActionButton) view.findViewById(R.id.addButton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View  v) {
                listener.onAddItem();
            }
        });

        //Zwrócenie widoku graficznego interfejsu użytkownika
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ItemsFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);
        getLoaderManager().initLoader((ITEMS_LOADER), null, this);
    }

    public void updateItemList() {
        itemsAdapter.notifyDataSetChanged();

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        //Utworzenie obiektu CursorLoader
        switch (id) {
            case ITEMS_LOADER:
                return  new CursorLoader(getActivity(),
                        DatabaseDescription.Item.CONTENT_URI, //Uri tabeli itemek
                        null, // wartość null zzwraca wszystkie kolumny
                        null, // zwraca wszystkie wiersze
                        null, // brak argumentów selekcji
                        DatabaseDescription.Item.COLUMN_NAME + "COLLATE NOCASE ASC"); //kolejność sortowania
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        itemsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        itemsAdapter.swapCursor(null);
    }
}