package com.app.officeautomationapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.FoldingCellListAdapter;
import com.app.officeautomationapp.dto.Item;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class MiaomuActivity extends BaseActivity implements  View.OnClickListener{

    final ArrayList<Item> items = Item.getTestingList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaomu);
        final int[] temp = {0};

        final ListView theListView = (ListView) findViewById(R.id.miaomuListView);

        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);

        adapter.setOnItemClickListener(new FoldingCellListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoldingCell cell, int position, View v) {
                Toast.makeText(getApplicationContext(), "22222", Toast.LENGTH_SHORT).show();
//                cell.toggle(false);
                items.get(position).getCell().toggle(false);
                if(items.size()<position+2)
                {
                    //最后一位
                }
                else
                {
                    items.get(position+1).getCell().toggle(false);
                }

            }
        });
        theListView.setAdapter(adapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos==0&& temp[0] ==0)
                {
                    ((FoldingCell) view).toggle(false);
                    temp[0]++;
                }

            }
        });

    }


    @Override
    public void onClick(View view) {

    }
}
