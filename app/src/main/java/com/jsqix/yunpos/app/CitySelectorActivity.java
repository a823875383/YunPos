package com.jsqix.yunpos.app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jsqix.yunpos.app.adapter.CityAdapter;
import com.jsqix.yunpos.app.api.HttpPost;
import com.jsqix.yunpos.app.base.BaseAty;
import com.jsqix.yunpos.app.bean.CityBean;
import com.jsqix.yunpos.app.utils.UAD;
import com.jsqix.yunpos.app.view.TitleBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_city_selector)
public class CitySelectorActivity extends BaseAty {
    @ViewInject(R.id.title_bar)
    private TitleBar titleBar;
    @ViewInject(R.id.listView)
    private ListView listView;
    private CityAdapter adapter;
    private List<CityBean.ObjEntity> data = new ArrayList<>();

    private String title;
    private String cid, level;
    private ArrayList<CityBean.ObjEntity>extras=new ArrayList<>();

    final static int PROVINCE = 0, CITY = 1, AREA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getData(PROVINCE);
    }


    private void initView() {
        cid = getIntent().getStringExtra(UAD.CID);
        level = getIntent().getStringExtra(UAD.LEVEL);

        title = getIntent().getExtras().getString(UAD.TITLE, "请选择");
        titleBar.setLeftBackground(R.mipmap.ic_back).setTitle(title);

        adapter = new CityAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityBean.ObjEntity objEntity = data.get(position);
                extras.add(objEntity);
                if ((int)listView.getTag() == PROVINCE) {
                    cid = objEntity.getCode();
                    level = "2";
                    titleBar.setTitle("请选择市");
                    getData(CITY);
                } else if ((int)listView.getTag() == CITY) {
                    cid = objEntity.getCode();
                    level = "3";
                    titleBar.setTitle("请选择区");
                    getData(AREA);
                } else if ((int)listView.getTag() == AREA) {
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList(UAD.RESULT, extras);
                    setResult(RESULT_OK, getIntent().putExtras(bundle));
                    finish();
                }
            }
        });
    }

    private void getData(int code) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", cid);
        map.put("level", level);
        HttpPost post = new HttpPost(this, this, map) {
            @Override
            public void onPreExecute() {
                loading.show();
            }
        };
        post.execute("getArea");
        post.setResultCode(code);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        try {
            loading.dismiss();
            CityBean bean = new Gson().fromJson(result, CityBean.class);
            if (bean.getCode().equals("000")) {
                data.clear();
                listView.setTag(resultCode);
                data.addAll(bean.getObj());
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {

        }
    }
}
