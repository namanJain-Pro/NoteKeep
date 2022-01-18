package dev.naman.notetaking.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.naman.notetaking.R;
import dev.naman.notetaking.model.Note;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final AsyncListDiffer<Note> diffUtil = new AsyncListDiffer<Note>(this, DIFF_CALLBACK);

    public void submitList(List<Note> noteList) {
        diffUtil.submitList(noteList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ConstraintLayout mItemLayout;
        private final TextView mItemDate, mItemTitle, mItemContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mItemLayout = (ConstraintLayout) itemView.findViewById(R.id.item_layout);
            mItemDate = (TextView) itemView.findViewById(R.id.item_date);
            mItemTitle = (TextView) itemView.findViewById(R.id.item_title);
            mItemContent = (TextView) itemView.findViewById(R.id.item_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = diffUtil.getCurrentList().get(position);

        holder.mItemLayout.setBackgroundColor(Color.parseColor(note.getColor()));
        holder.mItemDate.setText(note.getDate());
        holder.mItemTitle.setText(note.getTitle());
        holder.mItemContent.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return diffUtil.getCurrentList().size();
    }

}
