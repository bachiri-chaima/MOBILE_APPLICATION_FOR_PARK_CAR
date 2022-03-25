package com.example.gariliapllication;

import android.view.ViewGroup;

public abstract class Draweritem < T extends DrawerAdapter.ViewHolder> {

    protected boolean ischeked;

    public abstract T createViewHolde(ViewGroup parent);

    public  abstract void bindViewHolder (T holder);

    public Draweritem<T>setcheked(boolean ischeked){
        this.ischeked = ischeked;
        return this;
    }

    public boolean ischeked(){
        return ischeked;
    }

    public boolean isSelectable(){
        return true;
    }

}
