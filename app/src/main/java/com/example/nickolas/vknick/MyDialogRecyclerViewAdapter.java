package com.example.nickolas.vknick;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nickolas.vknick.DialogFragment.OnListFragmentInteractionListener;
import com.example.nickolas.vknick.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDialogRecyclerViewAdapter extends RecyclerView.Adapter<MyDialogRecyclerViewAdapter.ViewHolder> {

    DialogModel dialogModel;


    public MyDialogRecyclerViewAdapter(DialogModel dialogModel) {
        this.dialogModel = dialogModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mFullNameView.setText(dialogModel.getFullName(position));
        holder.mLastMessageView.setText(dialogModel.getLastMessage(position));
        new DownloadImageTask(holder.mAvatarImageView).execute(dialogModel.getPhoto(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //--------------------------------------------------------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFullNameView;
        public final TextView mLastMessageView;
        public final ImageView mAvatarImageView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFullNameView = (TextView) view.findViewById(R.id.full_name);
            mLastMessageView = (TextView) view.findViewById(R.id.last_message_view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLastMessageView.getText() + "'";
        }
    }
}
