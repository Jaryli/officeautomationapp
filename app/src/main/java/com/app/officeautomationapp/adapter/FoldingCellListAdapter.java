package com.app.officeautomationapp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.dto.Item;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class FoldingCellListAdapter extends ArrayAdapter<Item> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //    private View.OnClickListener defaultRequestBtnClickListener;
//    private View.OnClickListener defaultRequestBtnClickListener;

    List<Item> list=new ArrayList<Item>();

    public FoldingCellListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
        this.list=objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        if (cell == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell1, parent, false);
            viewHolder = new ViewHolder(cell);
            // binding view parts to view holder
//            viewHolder.price = (TextView) cell.findViewById(R.id.title_price);
//            viewHolder.time = (TextView) cell.findViewById(R.id.title_time_label);
//            viewHolder.date = (TextView) cell.findViewById(R.id.title_date_label);
//            viewHolder.fromAddress = (TextView) cell.findViewById(R.id.title_from_address);
//            viewHolder.toAddress = (TextView) cell.findViewById(R.id.title_to_address);
//            viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
//            viewHolder.pledgePrice = (TextView) cell.findViewById(R.id.title_pledge);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.content_request_btn);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
//        viewHolder.price.setText(item.getPrice());
//        viewHolder.time.setText(item.getTime());
//        viewHolder.date.setText(item.getDate());
//        viewHolder.fromAddress.setText(item.getFromAddress());
//        viewHolder.toAddress.setText(item.getToAddress());
//        viewHolder.requestsCount.setText(String.valueOf(item.getRequestsCount()));
//        viewHolder.pledgePrice.setText(item.getPledgePrice());

        // set custom btn handler for list item from that item
//        if (item.getRequestBtnClickListener() != null) {
//            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
//        } else {
//            // (optionally) add "default" handler if no handler found in item
//            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
//        }
        list.get(position).setCell(cell);
        final FoldingCell finalCell = cell;
        viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(finalCell,position, view);
            }
        });

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

//    public View.OnClickListener getDefaultRequestBtnClickListener() {
//        return defaultRequestBtnClickListener;
//    }
//
//    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
//        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
//    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(FoldingCell cell,int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    // View lookup cache
    private static class ViewHolder extends RecyclerView.ViewHolder{
//        TextView price;
        TextView contentRequestBtn;

        public ViewHolder(View itemView) {
            super(itemView);
        }
//        TextView pledgePrice;
//        TextView fromAddress;
//        TextView toAddress;
//        TextView requestsCount;
//        TextView date;
//        TextView time;
    }
}
