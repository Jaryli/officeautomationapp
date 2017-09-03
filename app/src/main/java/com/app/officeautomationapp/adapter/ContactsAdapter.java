package com.app.officeautomationapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.SortModel;

import java.util.List;

/**
 * Created by gjz on 9/4/16.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private List<SortModel> contacts;
    private int layoutId;

    private boolean hasCheckBox;

    private onRecyclerViewItemClickListener itemClickListener = null;

    public ContactsAdapter(List<SortModel> contacts, int layoutId,boolean hasCheckBox) {
        this.contacts = contacts;
        this.layoutId = layoutId;
        this.hasCheckBox=hasCheckBox;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    int layoutPosition = holder.getLayoutPosition();
                    itemClickListener.onItemClick(holder.itemView, layoutPosition);
                    if(hasCheckBox) {
                        if (holder.checkBox.isChecked()) {
                            holder.checkBox.setChecked(false);
                        } else {
                            holder.checkBox.setChecked(true);
                        }
                    }
                }
            }
        });

        SortModel contact = contacts.get(position);
        if (position == 0 || !contacts.get(position-1).getSortLetters().equals(contact.getSortLetters())) {
            holder.tvIndex.setVisibility(View.VISIBLE);
            holder.tvIndex.setText(contact.getSortLetters());
        } else {
            holder.tvIndex.setVisibility(View.GONE);
        }
        holder.tvName.setText(contact.getName());
        if(!hasCheckBox) {
            holder.checkBox.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIndex;
        public ImageView ivAvatar;
        public TextView tvName;
        public CheckBox checkBox;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            tvIndex = (TextView) itemView.findViewById(R.id.tv_index);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    public void setOnItemClickListener(onRecyclerViewItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public  interface onRecyclerViewItemClickListener {

        void onItemClick(View v, int position);
    }
}