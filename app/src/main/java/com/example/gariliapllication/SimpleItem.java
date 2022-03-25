package com.example.gariliapllication;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class SimpleItem extends Draweritem<SimpleItem.ViewHolder> {

    private  int selectedItemIconTint;
    private  int selectedItemTextTint;
    private  int normalItemIconTint;
    private  int normalItemTextTint;


    private Drawable icone;
    private String title;

    public  SimpleItem(Drawable icone, String title){
        this.icone = icone;
        this.title = title;
    }
    @Override
    public ViewHolder createViewHolde(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_menu,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(SimpleItem.ViewHolder holder) {
        holder.title.setText(title);
        holder.icone.setImageDrawable(icone);

        holder.title.setTextColor(ischeked ? selectedItemTextTint : normalItemTextTint);
        holder.icone.setColorFilter(ischeked ? selectedItemTextTint : normalItemIconTint);

    }
    public SimpleItem withSelectedIconTint(int selectedItemIconTint){
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }
    public SimpleItem withSelectedTextTint(int selectedItemTextTint){
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }
    public SimpleItem withIconTint(int normalItemIconTint){
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }
    public SimpleItem withTextTint(int normalItemTextTint){
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }

    static class  ViewHolder extends DrawerAdapter.ViewHolder{
        private ImageView icone;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icone = itemView.findViewById(R.id.icon_menu);
            title = itemView.findViewById(R.id.title_menu);
        }
    }
}
