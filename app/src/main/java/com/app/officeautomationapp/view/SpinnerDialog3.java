package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.PersonAdapter;
import com.app.officeautomationapp.bean.SortModel;

import java.util.ArrayList;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialog3 {

    Activity context;
    RadioButton radio_yes;
    RadioButton radio_no;
    EditText et_reason;
    MyGridView mygridview;//主办人
    int maxNum;
    MyGridView mygridview2;//经办人
    int maxNum2;

    AlertDialog alertDialog;
    OnDoneClick onDoneClick;
    int pos;
    int style;

    int status=0;//同意
    final ArrayList<SortModel> list1=new ArrayList<>();//返回获取,需要在最后面丢上一个空的
    final ArrayList<SortModel> list2=new ArrayList<>();//返回获取,需要在最后面丢上一个空的

    PersonAdapter myGridAdapter;
    PersonAdapter myGridAdapter2;
    ArrayList<SortModel> defaultList1;
    ArrayList<SortModel> defaultList2;

    public SpinnerDialog3(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialog3(Activity activity, int style,ArrayList<SortModel> defaultList1,ArrayList<SortModel> defaultList2) {

        this.context = activity;
        this.style=style;
        this.defaultList1=defaultList1;
        this.defaultList2=defaultList2;
    }

    public void bindOnSpinerListener(OnDoneClick onDoneClick) {
        this.onDoneClick = onDoneClick;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_layout3, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView rippleViewdone = (TextView) v.findViewById(R.id.done);
        et_reason= (EditText) v.findViewById(R.id.et_reason);
        radio_yes= (RadioButton) v.findViewById(R.id.radio_yes);
        radio_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=0;
            }
        });
        radio_no= (RadioButton) v.findViewById(R.id.radio_no);
        radio_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=1;
            }
        });//不同意
        mygridview= (MyGridView) v.findViewById(R.id.mygridview);
        initData();
        //实例化一个适配器
        myGridAdapter=new PersonAdapter(context,R.layout.item_person,R.layout.item_person2,list1,1,codeNum1);
        //为GridView设置适配器
        mygridview.setAdapter(myGridAdapter);
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                clickBtn1(list1,position);
            }
        });

        mygridview2= (MyGridView) v.findViewById(R.id.mygridview2);
        //实例化一个适配器
        myGridAdapter2=new PersonAdapter(context,R.layout.item_person,R.layout.item_person2,list2,10,codeNum2);
        //为GridView设置适配器
        mygridview2.setAdapter(myGridAdapter2);
        mygridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                clickBtn2(list1,position);
            }
        });

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false);

        rippleViewdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                onDoneClick.onClick(status,et_reason.getText().toString(),list1,list2);
                alertDialog.dismiss();
            }
        });
        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    private final int codeNum1=1;
    private final int codeNum2=2;

    public void refreshData(int num,ArrayList<SortModel> list)
    {
        if(num==codeNum1)
        {
            list1.clear();
            SortModel sortMode=new SortModel();
            sortMode.setId(0);
            if(list!=null)
            {
                list1.addAll(list);
            }
            list1.add(sortMode);
            myGridAdapter.refresh(list1);
        }
        if(num==codeNum2)
        {
            list2.clear();
            SortModel sortMode=new SortModel();
            sortMode.setId(0);
            if(list!=null)
            {
                list2.addAll(list);
            }
            list2.add(sortMode);
            myGridAdapter2.refresh(list2);
        }
    }



    public void initData()
    {
        SortModel sortMode=new SortModel();
        sortMode.setId(0);
        if(defaultList1!=null)
        {
            list1.addAll(defaultList1);
        }
        list1.add(sortMode);
        if(defaultList2!=null)
        {
            list2.addAll(defaultList2);
        }
        list2.add(sortMode);

    }

    public interface OnDoneClick
    {
        void onClick(int status, String reason,ArrayList<SortModel> list1,ArrayList<SortModel> list2);
    }

}