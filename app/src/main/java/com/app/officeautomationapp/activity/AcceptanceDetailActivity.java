package com.app.officeautomationapp.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.AcceptanceItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by xmuSistone on 2016/9/19.
 */
public class AcceptanceDetailActivity extends BaseActivity {

    public static final String EXTRA_IMAGE_URL = "detailImageUrl";

    public static final String IMAGE_TRANSITION_NAME = "transitionImage";

    public static final String PROJECTTYPRNAME = "projectTypeName";
    public static final String ITEM= "AcceptanceItem";
    public static final String POSITION= "position";
//    public static final String ADDRESS1_TRANSITION_NAME = "address1";
//    public static final String ADDRESS2_TRANSITION_NAME = "address2";
//    public static final String ADDRESS3_TRANSITION_NAME = "address3";
//    public static final String ADDRESS4_TRANSITION_NAME = "address4";
//    public static final String ADDRESS5_TRANSITION_NAME = "address5";
//    public static final String RATINGBAR_TRANSITION_NAME = "ratingBar";

//    public static final String HEAD1_TRANSITION_NAME = "head1";
//    public static final String HEAD2_TRANSITION_NAME = "head2";
//    public static final String HEAD3_TRANSITION_NAME = "head3";
//    public static final String HEAD4_TRANSITION_NAME = "head4";
//    public static final String dot = "dot";

    private TextView projectType;
    private ImageView imageView;
//    private RatingBar ratingBar;

    private LinearLayout listContainer;
//    private static final String[] headStrs = {HEAD1_TRANSITION_NAME, HEAD2_TRANSITION_NAME, HEAD3_TRANSITION_NAME, HEAD4_TRANSITION_NAME,dot};
    private static final int[] imageIds = {R.mipmap.head1, R.mipmap.head1, R.mipmap.head1, R.mipmap.head1,R.mipmap.head1};

    private Button test;

    ArrayList<AcceptanceItem> list;
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_detail);
        String projectTypeName = getIntent().getStringExtra(PROJECTTYPRNAME);
        list = (ArrayList<AcceptanceItem>)getIntent().getSerializableExtra(ITEM);
        position = getIntent().getIntExtra(POSITION,0);

        imageView = (ImageView) findViewById(R.id.image);
        projectType = (TextView)findViewById(R.id.projectType);
        projectType.setText(projectTypeName);
//        address2 = findViewById(R.id.address2);
//        address3 = findViewById(R.id.address3);
//        address4 = findViewById(R.id.address4);
//        address5 = findViewById(R.id.address5);
//        ratingBar = (RatingBar) findViewById(R.id.rating);
        listContainer = (LinearLayout) findViewById(R.id.detail_list_container);
        test=(Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                list.get(position).sethProjectType("aaaaa");
                onBackPressed();
                AcceptanceActivity.refresh(position,"aaaaa");
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


//        ImageLoader.getInstance().displayImage(imageUrl, imageView);

        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);

        ViewCompat.setTransitionName(projectType, projectTypeName);
//        ViewCompat.setTransitionName(address2, ADDRESS2_TRANSITION_NAME);
//        ViewCompat.setTransitionName(address3, ADDRESS3_TRANSITION_NAME);
//        ViewCompat.setTransitionName(address4, ADDRESS4_TRANSITION_NAME);
//        ViewCompat.setTransitionName(address5, ADDRESS5_TRANSITION_NAME);
//        ViewCompat.setTransitionName(ratingBar, RATINGBAR_TRANSITION_NAME);

        dealListView();
    }

    private void dealListView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

//        for (int i = 0; i < 20; i++) {
//            View childView = layoutInflater.inflate(R.layout.detail_list_item, null);
//            listContainer.addView(childView);
//            ImageView headView = (ImageView) childView.findViewById(R.id.head);
//            if (i < headStrs.length) {
//                headView.setImageResource(imageIds[i % imageIds.length]);
//                ViewCompat.setTransitionName(headView, headStrs[i]);
//            }
//        }
    }
}
