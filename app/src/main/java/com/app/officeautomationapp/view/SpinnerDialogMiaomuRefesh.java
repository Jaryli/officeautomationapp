package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.MiaomuDetailActivity;

import java.util.ArrayList;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialogMiaomuRefesh {

    Activity context;
    AlertDialog alertDialog;
    int style;



    public SpinnerDialogMiaomuRefesh(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialogMiaomuRefesh(Activity activity,int style) {
        this.context = activity;
        this.style=style;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_miaomurefesh, null);

        EditText ySNumInfo = (EditText) v.findViewById(R.id.ySNumInfo);
        Button btn_post= (Button) v.findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"post",Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}