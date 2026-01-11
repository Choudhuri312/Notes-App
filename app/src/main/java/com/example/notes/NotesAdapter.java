package com.example.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    Context context;
    ArrayList<ModelClass> list;
    DbClass db;
    boolean isSelectedMode = false; // Track if we are in "Delete Mode"

    public NotesAdapter(Context context, ArrayList<ModelClass> list){
        this.context = context;
        this.list = list;
        this.db = new DbClass(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_view_format, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelClass model = list.get(position);
        holder.noteTitle.setText(model.getTitle());
        holder.noteDesc.setText(model.getDesc());
        // Change background if selected
        holder.rows.setBackgroundResource(model.isSelected() ? R.drawable.border : 0);
        //delete oop
        holder.rows.setOnLongClickListener(v ->{
            if(!isSelectedMode){
                isSelectedMode = true;
                toggleSelection(position);
                // Trigger MainActivity to show the Delete/Select All icons
                if (context instanceof MainActivity) {
                    ((MainActivity) context).enterSelectionMode();
                }
            }
            return true;
        });
        //update oop
        holder.rows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectedMode){
                    toggleSelection(position);
                }else {
                    Intent intent = new Intent(context, InsertNote.class);
                    //send data to insertNote activity
                    intent.putExtra("id", model.getId());
                    intent.putExtra("title", model.getTitle());
                    intent.putExtra("desc", model.getDesc());
                    //required when calling intent from adapter
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    private void toggleSelection(int position){
        ModelClass model = list.get(position);
        model.setSelected(!model.isSelected());
        notifyItemChanged(position);
        // If nothing is selected anymore, turn off selection mode
        boolean anySelected = list.stream().anyMatch(ModelClass::isSelected);

        if (!anySelected) {
            isSelectedMode = false;
            // Triggers showSelectionMenu(false) which hides the back button
            if (context instanceof MainActivity) {
                ((MainActivity) context).exitSelectionMode();
            }
        }
    }
    public void selectAll(boolean select) {
        for (ModelClass m : list) m.setSelected(select);
        notifyDataSetChanged();
    }
    // deselect all items and reset the selection state within the adapter
    public void clearSelection() {
        for (ModelClass m : list) m.setSelected(false);
        isSelectedMode = false;
        notifyDataSetChanged();
    }
    public ArrayList<ModelClass> getSelectedItems() {
        ArrayList<ModelClass> selected = new ArrayList<>();
        for (ModelClass m : list) {
            if (m.isSelected()) selected.add(m);
        }
        return selected;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteDesc;
        LinearLayout rows;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteDesc = itemView.findViewById(R.id.noteDesc);
            rows = itemView.findViewById(R.id.row);
        }
    }
}
