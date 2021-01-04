package com.example.basicactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDivider extends RecyclerView.ItemDecoration {

    private final Drawable divider;

    public ItemDivider(Context context) {
        int[] attrs = {android.R.attr.listDivider};
        divider = context.obtainStyledAttributes(attrs).getDrawable(0);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);

        /* Wyliczenie współrzędnych początku i końca linii*/
        int left = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();

        /*Rysowanie linii pod każdym elemetem poza ostatnim z nich*/

        for (int i = 0; i < recyclerView.getChildCount() - 1; ++i) {
            /* Odczytywnie i-tego elementu z listy */
            View item = recyclerView.getChildAt(i);

//            Obliczenie współrzędnych na osi Y
            int top = item.getBottom() + ((RecyclerView.LayoutParams) item.getLayoutParams()).bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            /* Narysowanie zdefiniowanej linii*/
            divider. setBounds(left,top,right,bottom);
            divider.draw(canvas);
        }

    }
}
