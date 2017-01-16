package com.itude.apt.prophiles.util;

import android.support.v7.widget.RecyclerView;

/**
 * Created by athompson on 1/15/17.
 */

public class RecyclerViewSingleSelector {

    private RecyclerView mRecyclerView;
    private int mSelectedPosition = RecyclerView.NO_POSITION;

    public RecyclerViewSingleSelector(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void select(int position) {
        Selectable previous = (Selectable) mRecyclerView.findViewHolderForAdapterPosition(
            mSelectedPosition
        );

        if (previous != null) {
            previous.setSelected(false);
        }

        Selectable next = (Selectable) mRecyclerView.findViewHolderForAdapterPosition(position);
        next.setSelected(true);

        mSelectedPosition = position;
    }

    public interface Selectable {
        void setSelected(boolean isSelected);
    }
}
