package com.example.gariliapllication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class Spaceitem extends Draweritem<Spaceitem.ViewHolder> {

    private int spaceOp;
    public Spaceitem(int spaceOp){
        this.spaceOp = spaceOp;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public ViewHolder createViewHolde(ViewGroup parent) {
        Context c = parent.getContext();
        View view = new View(c);
        int height = (int) (c.getResources().getDisplayMetrics().density*spaceOp);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
        return new ViewHolder((view));
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    public  class ViewHolder extends DrawerAdapter.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
