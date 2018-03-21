package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.ContactsAdapter;
import com.app.officeautomationapp.bean.ApprovalPostBean;
import com.app.officeautomationapp.bean.PersonBean;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.bean.WorkFileBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.CharacterParser;
import com.app.officeautomationapp.util.PinyinComparator;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.SpinnerDialogContacts;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by CS-711701-00027 on 2017/7/21.
 */

public class ItemContactsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rvContacts;
    private WaveSideBar sideBar;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private ArrayList<SortModel> contacts = new ArrayList<SortModel>();
    private ArrayList<SortModel> selectContacts = new ArrayList<SortModel>();

    private ImageView imageView;
    private TextView done;

    private boolean hasCheckBox;
    private boolean hasDone;
    private int code;
    private int maxNum;
    private UserDto userDto;

    private int workId;
    private int sortId;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDto = (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(), "user");
        workId = getIntent().getIntExtra("workId", 0);
        sortId = getIntent().getIntExtra("sortId", 0);
        type = getIntent().getIntExtra("type", 0);

        initData(workId, sortId);
    }

    private void initView() {
        hasCheckBox = getIntent().getBooleanExtra("hasCheckBox", false);
        hasDone = getIntent().getBooleanExtra("hasDone", false);
        maxNum = getIntent().getIntExtra("maxNum", 999);
        code = getIntent().getIntExtra("code", 0);
        setContentView(R.layout.activity_contacts);
        imageView = (ImageView) findViewById(R.id.iv_contacts_back);
        imageView.setOnClickListener(this);
        done = (TextView) findViewById(R.id.done);
        done.setOnClickListener(this);
        if (!hasDone) {
            done.setVisibility(View.GONE);
        }
        rvContacts = (RecyclerView) findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        ContactsAdapter adapter = new ContactsAdapter(contacts, R.layout.item_contacts, hasCheckBox, maxNum);
        rvContacts.setAdapter(adapter);
        adapter.setOnItemClickListener(new ContactsAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                Toast.makeText(ItemContactsActivity.this,contacts.get(position).getName(),Toast.LENGTH_SHORT).show();

                if (!hasDone)//弹出
                {
                    SpinnerDialogContacts spinnerDialog = new SpinnerDialogContacts(ItemContactsActivity.this, R.style.DialogAnimations_SmileWindow, contacts.get(position).getName(), contacts.get(position).getPhone(), contacts.get(position).getS_phone(), contacts.get(position).getQq());
                    spinnerDialog.showSpinerDialog();
                } else {
                    boolean isHas = false;
                    SortModel sortModel = null;
                    for (int i = 0; i < selectContacts.size(); i++) {
                        if (selectContacts.get(i).getId() == contacts.get(position).getId())//有过
                        {
                            sortModel = selectContacts.get(i);
                            isHas = true;
                            break;
                        }
                    }
                    if (!isHas) {
                        if (maxNum <= selectContacts.size()) {
                            Toast.makeText(ItemContactsActivity.this, "最多选择" + maxNum + "个", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (isHas) {
                        if (sortModel != null) {
                            selectContacts.remove(sortModel);
                        }
                    } else {
                        selectContacts.add(contacts.get(position));
                    }
                }
            }
        });
        sideBar = (WaveSideBar) findViewById(R.id.side_bar);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getSortLetters().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initData(int workId, int sortId) {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        RequestParams params = new RequestParams(Constants.getToUser + "?pageIndex=1&pageSize=9999");
        params.addHeader("access_token", userDto.getAccessToken());
        params.addQueryStringParameter("workId", workId + "");
        params.addQueryStringParameter("nextSort", sortId + "");

        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re = jsonObject.getInt("result");
                    Object data = null;
                    if (type == 1) {
                        data = jsonObject.get("userlist1");
                    } else if (type == 2) {
                        data =  jsonObject.get("userlist2");

                    }else {
                        data =  jsonObject.get("data");
                    }
                    if (re != 1) {
                        Toast.makeText(ItemContactsActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (data == "" || data == null) {
                            Toast.makeText(ItemContactsActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Gson gson = new Gson();
                            List<PersonBean> list = new ArrayList<PersonBean>();
                            Type type = new TypeToken<List<PersonBean>>() {
                            }.getType();
                            list = gson.fromJson(data.toString(), type);
//                            MessageDto messageDto = (MessageDto) gson.fromJson(jsonObject.toString(),MessageDto.class);
                            for (int i = 0; i < list.size(); i++) {
                                SortModel sortModel = new SortModel();
                                sortModel.setId(list.get(i).getUserId());
                                sortModel.setName(list.get(i).getUserTrueName());
                                sortModel.setPhone(list.get(i).getMobile());
                                sortModel.setS_phone(list.get(i).getShortPhone());
                                sortModel.setQq(list.get(i).getQQ());
                                sortModel.setChecked(false);
                                //汉字转换成拼音
                                String pinyin = characterParser.getSelling(list.get(i).getUserTrueName());
                                String sortString = pinyin.substring(0, 1).toUpperCase();

                                // 正则表达式，判断首字母是否是英文字母
                                if (sortString.matches("[A-Z]")) {
                                    sortModel.setSortLetters(sortString.toUpperCase());
                                } else {
                                    sortModel.setSortLetters("#");
                                }

                                contacts.add(sortModel);
                                Collections.sort(contacts, pinyinComparator);
                                initView();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(ItemContactsActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("JAVA", "onCancelled:" + cex);

            }

            @Override
            public void onFinished() {
                Log.e("JAVA", "onFinished:" + "");
            }
        });

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_contacts_back:
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(0, intent);
                this.finish();
                break;
            case R.id.done:
                intent.putExtra("data", selectContacts);
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(code, intent);
                this.finish();
                break;
            default:
                break;

        }
    }


}
