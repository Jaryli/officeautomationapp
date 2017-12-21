package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.AcceptanceActivity;
import com.app.officeautomationapp.bean.ProjectMiaomuBean;
import com.app.officeautomationapp.bean.ProjectTujianBean;

/**
 * Created by yu on 2017/11/17.
 */

public class ProjectDialog {
    ProjectMiaomuBean projectMiaomuBean;
    ProjectTujianBean projectTujianBean;
    String type;
    Activity context;
    AlertDialog alertDialog;
    int style;

    public ProjectDialog(Activity activity, ProjectMiaomuBean projectMiaomuBean, String type) {
        this.projectMiaomuBean = projectMiaomuBean;
        this.context = activity;
        this.type=type;
    }

    public ProjectDialog(Activity activity, ProjectMiaomuBean projectMiaomuBean, String type, int style) {
        this.projectMiaomuBean = projectMiaomuBean;
        this.context = activity;
        this.style=style;
        this.type=type;
    }

    public ProjectDialog(Activity activity, ProjectTujianBean projectTujianBean, String type, int style) {
        this.projectTujianBean = projectTujianBean;
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

        if(type.equals("miaomu")) {
            tx_project_name.setText(projectMiaomuBean.getProjectName());
            tx_project_address.setText(projectMiaomuBean.getProjectSite());
            tx_project_num.setText(projectMiaomuBean.getApplyCode());
            tx_project_person.setText(projectMiaomuBean.getMaker());
            tx_project_date.setText(projectMiaomuBean.getApplyDate());
        }
        else
        {
            tx_project_name.setText(projectTujianBean.getProjectName());
            tx_project_address.setText(projectTujianBean.getProjectSite());
            tx_project_num.setText(projectTujianBean.getApplyCode());
            tx_project_person.setText(projectTujianBean.getApplyer());
            tx_project_date.setText(projectTujianBean.getApplyDate());
        }

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(false);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,AcceptanceActivity.class);
                intent.putExtra("type",type);
                if(type.equals("miaomu")) {
                    intent.putExtra("data",projectMiaomuBean);
                }else
                {
                    intent.putExtra("data",projectTujianBean);
                }
                context.startActivity(intent);
//                Toast.makeText(context,"功能开发中，稍后为您呈现！",Toast.LENGTH_SHORT).show();
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
