package com.example.basicactivity;

import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.basicactivity.data.DatabaseDescription;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>  {


    //Definicja interfejsu umplementowanego przez klasę ContactsFragment
    public interface ItemClickListener {
        void onClick(Uri itemUri);
    }

    //Klasa używana w implementacji wzorca ViewHolder w kontekście widoku RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {


        //Wiok textView wyswietlający nazwę kontaktu
        public final TextView textView;

        //Identyfikator rzędu kontaktu
        private long rowID;

        //Konstruktor klasy ViewHolder
        public ViewHolder(View view) {
            super(view);

            //Inicjalizacja widoku tekstView
            textView = (TextView) view.findViewById(android.R.id.text1);

            //Podłącz do obiektu wiew obiekt nasłuchujący zdarzeń
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(DatabaseDescription.Item.buildItemUri(rowID));

                }
            });
        }

        //określenie identyfikatora rzędu
        public void setRowID(long rowID) {
            this.rowID = rowID;
        }
    }

    //Zmienne egzemplarzowe
    private Cursor cursor = null;
    private final ItemClickListener clickListener;

    //konstruktor klasy contactAdapter
    public ItemsAdapter(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    //Uzyskanie obiektu ViewHolder bieżącego elementu kontaktu
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Prztgotowanie do wyświetlenia predefiniowanego rozkładu Androida
        View view = LayoutInflater.from(parent.getContext() ).inflate(android.R.layout.simple_list_item_1, parent, false);

        //Zwrócenie obiektu ViewHolder bieżącego elementu
        return new ViewHolder(view);

    }

    //Określenie teksu elementu listy

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Przeniesienie wybranego kontaktu w odpowiednnie miejsce widoku RecyclerView
        cursor.moveToPosition(position);

        //Określenie identyfikatora rowID elementu ViewHolder
        holder.setRowID(cursor.getLong(cursor.getColumnIndex(DatabaseDescription.Item._ID)));

        //Ustawnienie tekstu widoku TextView elementu widokuRecyclerView
        holder.textView.setText(cursor.getString(cursor.getColumnIndex(DatabaseDescription.Item.COLUMN_NAME)));

        //Zwraca liczbę elementów wiązanych przez adapter


    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    //Zamienia bieżący obiekt cursor na nowy
    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }
}
