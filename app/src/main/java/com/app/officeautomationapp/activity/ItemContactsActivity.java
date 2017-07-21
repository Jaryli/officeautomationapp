package com.app.officeautomationapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.ContactsAdapter;
import com.app.officeautomationapp.bean.PersonBean;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.CharacterParser;
import com.app.officeautomationapp.util.PinyinComparator;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.gjiazhe.wavesidebar.WaveSideBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by CS-711701-00027 on 2017/7/21.
 */

public class ItemContactsActivity extends BaseActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_contacts);
        rvContacts = (RecyclerView) findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        rvContacts.setAdapter(new ContactsAdapter(contacts, R.layout.item_contacts));
        sideBar = (WaveSideBar) findViewById(R.id.side_bar);
        sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i=0; i<contacts.size(); i++) {
                    if (contacts.get(i).getSortLetters().equals(index)) {
                        ((LinearLayoutManager) rvContacts.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }

    private void initData() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        RequestParams params = new RequestParams(Constants.GetPersonList);
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(this.getApplicationContext(),"user");
        params.addHeader("access_token", userDto.getAccessToken());
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(ItemContactsActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(ItemContactsActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<PersonBean> list=new ArrayList<PersonBean>();
                            Type type=new TypeToken<List<PersonBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("dataList").toString(), type);
//                            MessageDto messageDto = (MessageDto) gson.fromJson(jsonObject.toString(),MessageDto.class);
                            for(int i=0;i<list.size();i++)
                            {
                                SortModel sortModel=new SortModel();
                                sortModel.setId(list.get(i).getUserId());
                                sortModel.setName(list.get(i).getUserTrueName());
                                //汉字转换成拼音
                                String pinyin = characterParser.getSelling(list.get(i).getUserTrueName());
                                String sortString = pinyin.substring(0, 1).toUpperCase();

                                // 正则表达式，判断首字母是否是英文字母
                                if(sortString.matches("[A-Z]")){
                                    sortModel.setSortLetters(sortString.toUpperCase());
                                }else{
                                    sortModel.setSortLetters("#");
                                }

                                contacts.add(sortModel);
                                Collections.sort(contacts, pinyinComparator);
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
                Toast.makeText(ItemContactsActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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



}
