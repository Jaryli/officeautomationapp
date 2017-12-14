package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.AcceptanceActivity;
import com.app.officeautomationapp.activity.MiaomuActivity;
import com.app.officeautomationapp.activity.ProjectActivity;
import com.app.officeautomationapp.bean.ProjectMiaomuTujianBean;

import java.util.ArrayList;

/**
 * Created by yu on 2017/11/17.
 */

public class ProjectDialog {
    ProjectMiaomuTujianBean  projectMiaomuTujianBean;
    String type;
    Activity context;
    AlertDialog alertDialog;
    int style;

    public ProjectDialog(Activity activity,ProjectMiaomuTujianBean  projectMiaomuTujianBean,String type) {
        this.projectMiaomuTujianBean = projectMiaomuTujianBean;
        this.context = activity;
        this.type=type;
    }

    public ProjectDialog(Activity activity,ProjectMiaomuTujianBean  projectMiaomuTujianBean,String type,int style) {
        this.projectMiaomuTujianBean = projectMiaomuTujianBean;
        this.context = activity;
        this.style=style;
        this.type=type;
    }


    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_project, null);
        Button btn_post=(Button) v.findViewById(R.id.btn_post);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView tx_project_name = (TextView) v.findViewById(R.id.tx_project_name);
        TextView tx_project_address = (TextView) v.findViewById(R.id.tx_project_address);
        TextView tx_project_num = (TextView) v.findViewById(R.id.tx_project_num);
        TextView tx_project_person = (TextView) v.findViewById(R.id.tx_project_person);
        TextView tx_project_date = (TextView) v.findViewById(R.id.tx_project_date);


        tx_project_name.setText(projectMiaomuTujianBean.getProjectName());
        tx_project_address.setText(projectMiaomuTujianBean.getProjectSite());
        tx_project_num.setText(projectMiaomuTujianBean.getApplyCode());
        tx_project_person.setText(projectMiaomuTujianBean.getMaker());
        tx_project_date.setText(projectMiaomuTujianBean.getMakeDate());

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,AcceptanceActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("data",projectMiaomuTujianBean);
                context.startActivity(intent);
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
}
