package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ItemContactsActivity;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialogAcceptance {
    Activity context;

    EditText et_user;
    AlertDialog alertDialog;
    OnDoneClick onDoneClick;
    int style;

    int maxNum;
    int resultCode;

    public SpinnerDialogAcceptance(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialogAcceptance(Activity activity, int style, int maxNum, int resultCode) {

        this.context = activity;
        this.style=style;
        this.maxNum=maxNum;
        this.resultCode=resultCode;
    }

    public void bindOnSpinerListener(OnDoneClick onDoneClick) {
        this.onDoneClick = onDoneClick;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_acceptance, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView rippleViewdone = (TextView) v.findViewById(R.id.done);
        et_user= (EditText) v.findViewById(R.id.et_user);
        et_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("hasCheckBox", true);
                intent.putExtra("hasDone", true);
                intent.putExtra("code", resultCode);
                intent.putExtra("maxNum", maxNum);
                intent.setClass(context, ItemContactsActivity.class);
                context.startActivityForResult(intent,resultCode);
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
                onDoneClick.onClick();
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

    public void refreshData(String str)
    {
        et_user.setText(str);
    }


    public interface OnDoneClick
    {
        void onClick();
    }

}