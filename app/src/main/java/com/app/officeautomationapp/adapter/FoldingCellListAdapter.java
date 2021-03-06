package com.app.officeautomationapp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private String type="";

    public FoldingCellListAdapter(Context context, List<Item> objects,String type) {
        super(context, 0, objects);
        this.list=objects;
        this.type=type;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder= new ViewHolder();;
            LayoutInflater vi = LayoutInflater.from(getContext());
            if(position==0) {
                cell = (FoldingCell) vi.inflate(R.layout.cell1, parent, false);

                viewHolder.title = (TextView) cell.findViewById(R.id.title);
                viewHolder.projectName = (TextView) cell.findViewById(R.id.projectName);
                viewHolder.contentRequestBtn = (Button) cell.findViewById(R.id.btn_post);
                cell.setTag(viewHolder);
            }
            else
            {

                cell = (FoldingCell) vi.inflate(R.layout.cell2, parent, false);
                viewHolder.title = (TextView) cell.findViewById(R.id.title);
                viewHolder.projectName = (TextView) cell.findViewById(R.id.projectName);
                viewHolder.contentRequestBtn = (Button) cell.findViewById(R.id.btn_post);
                cell.setTag(viewHolder);
            }


        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();

        }

        if(position==0)
        {
            viewHolder.title.setText(type+"验收单");
            viewHolder.projectName.setText(list.get(position).getTitle());
        }
        else
        {

            viewHolder.title.setText("验收明细单"+position);
            viewHolder.projectName.setText(list.get(position).getTitle());
        }


        list.get(position).setCell(cell);
        final FoldingCell finalCell = cell;
        if(position==0) {
            viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(finalCell, position, view);
                }
            });
        }
        else
        {
            viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(finalCell, position, view);
                }
            });
        }

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
    private static class ViewHolder {
        TextView projectName;
        Button contentRequestBtn;
        TextView title;
    }


}
