package com.example.notesapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.data.NoteDataClass;
import com.example.notesapp.data.NoteSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener;
    private OnLongItemClickListener longItemClickListener;

    private NoteSource noteSource;

    public NoteAdapter() {
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder viewHolder, int i) {
        viewHolder.setData(noteSource.getNoteData(i));
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void SetOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView description;
        private final TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.noteName);
            description = itemView.findViewById(R.id.noteDescription);
            date = itemView.findViewById(R.id.noteDate);

            itemView.setOnLongClickListener(v -> {
                if (longItemClickListener != null) {
                    longItemClickListener.onLongItemClick(v, getAdapterPosition());
                }
                return false;
            });

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }

            });
        }

        public void setData(NoteDataClass noteData) {
            name.setText(noteData.getName());
            description.setText(noteData.getDescription());
            date.setText(noteData.getDateOfCreation());
        }
    }

    public void setDataSource(NoteSource noteSource) {
        this.noteSource = noteSource;
    }
}


