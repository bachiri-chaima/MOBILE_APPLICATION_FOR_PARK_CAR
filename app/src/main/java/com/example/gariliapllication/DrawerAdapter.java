package com.example.gariliapllication;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private List<Draweritem> items;
    private Map<Class<? extends  Draweritem>,Integer> viewTypes;
    private SparseArray<Draweritem> holderFactories;

    private OnItemSelectedListener listener;

    public  DrawerAdapter(List<Draweritem> items){
        this.items=items;
        this.viewTypes= new HashMap<>();
        this.holderFactories = new SparseArray<>();
        processViewTypes();
    }

    private void processViewTypes() {
        int type = 0;
        for (Draweritem item : items){
            if(!viewTypes.containsKey(item.getClass())){
                viewTypes.put(item.getClass(),type);
                holderFactories.put(type,item);
                type++;
            }
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = holderFactories.get(viewType).createViewHolde(parent);
        holder.drawerAdapter=this;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        items.get(position).bindViewHolder(holder);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    public void setSelected(int position){
        Draweritem newcheked = items.get(position);
        if (!newcheked.isSelectable()){
            return;
        }
        for (int i=0; i<items.size(); i++){
            Draweritem item = items.get(i);
            if(item.ischeked()){
                item.setcheked(false);
                notifyItemChanged(i);
                break;
            }
        }
        newcheked.setcheked(true);
        notifyItemChanged(position);

        if (listener !=null){
            listener.onItemSelected(position);
        }
    }

    public void setListener(OnItemSelectedListener listener){
        this.listener = listener;
    }
    public interface OnItemSelectedListener{
        void onItemSelected(int position);
    }


    static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private DrawerAdapter drawerAdapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            drawerAdapter.setSelected(getAdapterPosition());

        }
    }
}
