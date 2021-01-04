package com.example.basicactivity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //Interfejs z metodą wywołania zwrotnego implemenowaną przez główną aktywność
    public interface AddEditFragmentListener {
        //wywołanie gry kontakt jest zapisywany
        void onAddEditCompleted(Uri itemUri);

    }

    // Pole używane do identyfikacji obiektu loader
    private static final int ITEM_LOADER = 0;

    // Pole obiektu implementującego zagnieżdzony interfejs - główna aktywność (MainActivity)
    private AddEditFragmentListener listener;

    //Adres Uri wybranej itemki
    private Uri itemUri;

    // Dodawanie (true) / edycja (false) itemki
    private boolean addingNewItem = true;

    //Poa gradicznego interfejsu użytkownika
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout phoneTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout streetTextInputLayout;
    private TextInputLayout cityTextInputLayout;
    private TextInputLayout stateTextInputLayout;
    private TextInputLayout zipTextInputLayout;
    private FloatingActionButton saveItemFAB;

    // Używany wraz z obiektami SnackBar
    private CoordinatorLayout coordinatorLayout;

    //Inicjalizacja obiekru AddEditFragmentListenerr przy dołączeniufagmentu do głównej aktywności
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        listener = (AddEditFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    //Utworzenie widoku obiektu Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);

        //Przygotowanie elementów graficznego interfejsu użytkownika
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

    //

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
