package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;

/**
 * Created by CS-711701-00027 on 2017/4/14.
 */

public class ApprovalWorkActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivWorkApprovalBack;
    private ImageView btnNoApproval;

    private TextView bar_num;
    private int msgCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_approval);
        bar_num = (TextView) findViewById(R.id.bar_num);

        ivWorkApprovalBack=(ImageView)findViewById(R.id.iv_work_approval_back);
        ivWorkApprovalBack.setOnClickListener(this);
        btnNoApproval=(ImageView)findViewById(R.id.btn_no_approval);
        btnNoApproval.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no_approval:
                startActivity(new Intent(this, ApprovalActivity.class));
                break;
            case R.id.iv_work_approval_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    public void setMessageCount(int count) {
        msgCount = count;
        if (count <= 0) {
            bar_num.setVisibility(View.GONE);
        } else {
            bar_num.setVisibility(View.VISIBLE);
            if (count < 100) {
                bar_num.setText(count + "");
            } else {
                bar_num.setText("99+");
            }
        }
    }
}
