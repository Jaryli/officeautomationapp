package com.app.officeautomationapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.AcceptanceDetailActivity;
import com.app.officeautomationapp.util.DragLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by xmuSistone on 2016/9/18.
 */
public class CommonFragment extends Fragment implements DragLayout.GotoDetailListener {
    private ImageView imageView;
    private View address1, address2, address3, address4, address5;
    private RatingBar ratingBar;
    private View head1, head2, head3, head4,dot;
    private String imageUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_common, null);
        DragLayout dragLayout = (DragLayout) rootView.findViewById(R.id.drag_layout);
        imageView = (ImageView) dragLayout.findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        address1 = dragLayout.findViewById(R.id.address1);
        address2 = dragLayout.findViewById(R.id.address2);
        address3 = dragLayout.findViewById(R.id.address3);
        address4 = dragLayout.findViewById(R.id.address4);
        address5 = dragLayout.findViewById(R.id.address5);
        ratingBar = (RatingBar) dragLayout.findViewById(R.id.rating);

        head1 = dragLayout.findViewById(R.id.head1);
        head2 = dragLayout.findViewById(R.id.head2);
        head3 = dragLayout.findViewById(R.id.head3);
        head4 = dragLayout.findViewById(R.id.head4);
        dot = dragLayout.findViewById(R.id.dot);

        dragLayout.setGotoDetailListener(this);
        return rootView;
    }

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair(imageView, AcceptanceDetailActivity.IMAGE_TRANSITION_NAME),
                new Pair(address1, AcceptanceDetailActivity.ADDRESS1_TRANSITION_NAME),
                new Pair(address2, AcceptanceDetailActivity.ADDRESS2_TRANSITION_NAME),
                new Pair(address3, AcceptanceDetailActivity.ADDRESS3_TRANSITION_NAME),
                new Pair(address4, AcceptanceDetailActivity.ADDRESS4_TRANSITION_NAME),
                new Pair(address5, AcceptanceDetailActivity.ADDRESS5_TRANSITION_NAME),
                new Pair(ratingBar, AcceptanceDetailActivity.RATINGBAR_TRANSITION_NAME),
                new Pair(head1, AcceptanceDetailActivity.HEAD1_TRANSITION_NAME),
                new Pair(head2, AcceptanceDetailActivity.HEAD2_TRANSITION_NAME),
                new Pair(head3, AcceptanceDetailActivity.HEAD3_TRANSITION_NAME),
                new Pair(head4, AcceptanceDetailActivity.HEAD4_TRANSITION_NAME),
                new Pair(dot, AcceptanceDetailActivity.dot)
        );
        Intent intent = new Intent(activity, AcceptanceDetailActivity.class);
        intent.putExtra(AcceptanceDetailActivity.EXTRA_IMAGE_URL, imageUrl);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
