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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaomu);

        ListView theListView = (ListView) findViewById(R.id.miaomuListView);

        final ArrayList<Item> items = Item.getTestingList();

        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "123123", Toast.LENGTH_SHORT).show();
                ((FoldingCell) v).toggle(false);
            }
        });

        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "321312", Toast.LENGTH_SHORT).show();

            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
//                adapter.registerToggle(pos);
            }
        });

    }




    @Override
    public void onClick(View view) {

    }
}
