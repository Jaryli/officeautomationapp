package com.app.officeautomationapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ApprovalWorkActivity;
import com.app.officeautomationapp.activity.HiddenProjectActivity;
import com.app.officeautomationapp.activity.StartWorkActivity;

/**
 * Created by yu on 2017/3/18.
 */
public class ButtonGroup1Fragment extends Fragment implements View.OnClickListener{

    private Button btnHiddenProject;
    private Button btnWorkApproval;
    private Button btnWorkStart;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_button_group1,null);
        btnHiddenProject=(Button)view.findViewById(R.id.btn_hidden_project);
        btnHiddenProject.setOnClickListener(this);
        btnWorkApproval=(Button)view.findViewById(R.id.btn_work_approval);
        btnWorkApproval.setOnClickListener(this);
        btnWorkStart=(Button)view.findViewById(R.id.btn_work_start);
        btnWorkStart.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hidden_project:
                startActivity(new Intent(getActivity(), HiddenProjectActivity.class));
                break;
            case R.id.btn_work_approval:
                startActivity(new Intent(getActivity(), ApprovalWorkActivity.class));
                break;
            case R.id.btn_work_start:
                startActivity(new Intent(getActivity(), StartWorkActivity.class));
                break;
            default:
                break;
        }
    }
}
