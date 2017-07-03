package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.officeautomationapp.R;

/**
 * Created by yu on 2017/5/10.
 */

public class StartWorkActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivWorkBack;
    private Button btnTaiBan;
    private Button btnDianGong;
    private Button btnYongZhang;
    private Button btnVacate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_work);

        ivWorkBack=(ImageView)findViewById(R.id.iv_work_back);
        ivWorkBack.setOnClickListener(this);

        btnTaiBan=(Button)findViewById(R.id.btn_taiban);
        btnTaiBan.setOnClickListener(this);
        btnDianGong=(Button)findViewById(R.id.btn_diangong);
        btnDianGong.setOnClickListener(this);
        btnYongZhang=(Button)findViewById(R.id.btn_yongzhang);
        btnYongZhang.setOnClickListener(this);
        btnVacate=(Button)findViewById(R.id.btn_qingjia);
        btnVacate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_work_back:
                this.finish();
                break;
            case R.id.btn_taiban:
                startActivity(new Intent(this, WorkTaibanActivity.class));
                break;
            case R.id.btn_diangong:
                startActivity(new Intent(this, WorkTaibanActivity.class));
                break;
            case R.id.btn_yongzhang:
                startActivity(new Intent(this, WorkTaibanActivity.class));
                break;
            case R.id.btn_qingjia:
                startActivity(new Intent(this, WorkTaibanActivity.class));
                break;

            default:
                break;
        }
    }
}
