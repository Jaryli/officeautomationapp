package com.app.officeautomationapp.dto;

import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/11/14 0014.
 */
public class Item {

    private String title;

    private FoldingCell cell;

//    private View.OnClickListener requestBtnClickListener;

    public Item() {
    }

    public Item(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public View.OnClickListener getRequestBtnClickListener() {
//        return requestBtnClickListener;
//    }
//
//    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
//        this.requestBtnClickListener = requestBtnClickListener;
//    }

    public FoldingCell getCell() {
        return cell;
    }

    public void setCell(FoldingCell cell) {
        this.cell = cell;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        items.add(new Item(""));
        return items;

    }

}

