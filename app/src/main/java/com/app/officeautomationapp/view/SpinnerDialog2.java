package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.officeautomationapp.R;

import java.util.ArrayList;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialog2 {

    Activity context;
    RadioButton radio_yes;
    RadioButton radio_no;
    EditText et_reason;

    AlertDialog alertDialog;
    OnDoneClick onDoneClick;
    int pos;
    int style;

    int status=1;//同意



    public SpinnerDialog2(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialog2(Activity activity, int style) {

        this.context = activity;
        this.style=style;
    }

    public void bindOnSpinerListener(OnDoneClick onDoneClick) {
        this.onDoneClick = onDoneClick;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_layout2, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView rippleViewdone = (TextView) v.findViewById(R.id.done);
        et_reason= (EditText) v.findViewById(R.id.et_reason);
        radio_yes= (RadioButton) v.findViewById(R.id.radio_yes);
        radio_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=1;
            }
        });
        radio_no= (RadioButton) v.findViewById(R.id.radio_no);
        radio_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status=0;
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
                onDoneClick.onClick(status,et_reason.getText().toString());
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


    public interface OnDoneClick
    {
        void onClick(int status,String reason);
    }

}