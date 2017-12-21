package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.WzAdapter;
import com.app.officeautomationapp.adapter.WzTujianAdapter;
import com.app.officeautomationapp.bean.SupplyBean;
import com.app.officeautomationapp.bean.WzBean;
import com.app.officeautomationapp.bean.WzTujianBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yu on 2017/6/4.
 */

public class WzGetActivity extends BaseActivity implements View.OnClickListener{

    ProgressDialog progressDialog;
    private EditText searchBox;
    private UserDto userDto;
    private String type;

    WzAdapter adapter;
    WzTujianAdapter wzTujianAdapter;
    ListView listView;
    String code;
    HashMap<Integer, Object> mapPage = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wz_get);
        Intent intent=getIntent();
        code =  intent.getStringExtra("data");
        type=  intent.getStringExtra("type");
        mapPage= (HashMap<Integer, Object>) intent.getSerializableExtra("mapPage");
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        searchBox=(EditText)findViewById(R.id.searchBox);
        listView = (ListView) findViewById(R.id.list);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getData();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        init();
    }


    private void init(){
        getData();
    }

    private void getData()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        String url="";
        if(type.equals("miaomu"))
        {
            url=Constants.GetTreeDetailsList;
        }
        else
        {
            url=Constants.GetCivilDetailsList;
        }
        RequestParams params = new RequestParams(url);
        Log.i("WzGetActivity", "post-url:" + url);
        params.addHeader("access_token", userDto.getAccessToken());
        params.addBodyParameter("orderCode",code);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(WzGetActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        if(type.equals("miaomu"))
                        {
                            List<WzBean> list=new ArrayList<WzBean>();
                            Type type=new TypeToken<List<WzBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
                            if(list!=null)
                            {
                                if(adapter!=null)
                                {
                                    adapter.clear();
                                }
                                adapter= new WzAdapter(WzGetActivity.this, R.layout.item_wz, list);
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                final List<WzBean> finalList = list;
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                    {
                                        boolean b=false;
                                        for (Integer in : mapPage.keySet()) {
                                            if(finalList.get(i).getId().toString().equals(mapPage.get(in).toString()))
                                            {
                                                b=true;
                                            }
                                        }
                                        if(b)
                                        {
                                            Toast.makeText(WzGetActivity.this,"已经选择过了",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        Intent intent =getIntent();
                                        //这里使用bundle绷带来传输数据
                                        Bundle bundle =new Bundle();
                                        //传输的内容仍然是键值对的形式
                                        bundle.putString("data_tree_name", finalList.get(i).getTreeName());//回发的消息
                                        bundle.putString("data_tree_id", finalList.get(i).getId()+"");
                                        intent.putExtras(bundle);
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(WzGetActivity.this,"没有可用数据",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else//tujian
                        {
                            List<WzTujianBean> list=new ArrayList<WzTujianBean>();
                            Type type=new TypeToken<List<WzTujianBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
                            if(list!=null)
                            {
                                if(adapter!=null)
                                {
                                    adapter.clear();
                                }
                                wzTujianAdapter= new WzTujianAdapter(WzGetActivity.this, R.layout.item_wz_tujian, list);
                                listView.setAdapter(wzTujianAdapter);
                                wzTujianAdapter.notifyDataSetChanged();

                                final List<WzTujianBean> finalList = list;
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                    {
                                        boolean b=false;
                                        for (Integer in : mapPage.keySet()) {
                                            if(finalList.get(i).getId().toString().equals(mapPage.get(in).toString()))
                                            {
                                                b=true;
                                            }
                                        }
                                        if(b)
                                        {
                                            Toast.makeText(WzGetActivity.this,"已经选择过了",Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        Intent intent =getIntent();
                                        //这里使用bundle绷带来传输数据
                                        Bundle bundle =new Bundle();
                                        //传输的内容仍然是键值对的形式
                                        bundle.putString("data_tree_name", finalList.get(i).getCivilName());//回发的消息
                                        bundle.putString("data_tree_id", finalList.get(i).getId()+"");
                                        intent.putExtras(bundle);
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(WzGetActivity.this,"没有可用数据",Toast.LENGTH_SHORT).show();
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
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(WzGetActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("JAVA", "onCancelled:" + cex);
            }
            @Override
            public void onFinished() {
                Log.i("JAVA", "onFinished:" + "");
                progressDialog.hide();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
