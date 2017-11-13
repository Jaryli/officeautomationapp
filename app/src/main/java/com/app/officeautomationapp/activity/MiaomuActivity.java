package com.app.officeautomationapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.app.officeautomationapp.R;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class MiaomuActivity extends BaseActivity implements  View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miaomu);

        ListView theListView = (ListView) findViewById(R.id.miaomuListView);
    }

    @Override
    public void onClick(View view) {

    }
}
