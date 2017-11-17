package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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

    private String type;//miaomu tujian

    final ArrayList<Item> items = Item.getTestingList();

    int openid=0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏，只有android5.0以上才可以
        Window window = MiaomuActivity.this.getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.TRANSPARENT);

        ViewGroup mContentView = (ViewGroup) MiaomuActivity.this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }


        setContentView(R.layout.activity_miaomu);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
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
                    items.add(new Item("$5", "$300", "666"+position, "W 36th St, NY, 10029", 0, "TODAY", "06:15 PM"));
                    adapter.notifyDataSetChanged();
//                    items.get(position+1).getCell().toggle(false);
                }
                else
                {
                    items.get(position+1).getCell().toggle(false);
                    openid=position+1;
                }

            }
        });
        theListView.setAdapter(adapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
//                if(pos==0&& temp[0] ==0)
//                {
//                    ((FoldingCell) view).toggle(false);
//                    temp[0]++;
//                }
//                else if(pos!=0)
//                {
//                    items.remove(pos);
//                    adapter.notifyDataSetChanged();
////                    items.get(openid).getCell().toggle(false);
//                }

            }
        });

    }


    @Override
    public void onClick(View view) {

    }
}
