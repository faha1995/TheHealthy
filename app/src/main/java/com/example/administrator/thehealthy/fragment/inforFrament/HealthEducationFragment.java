package com.example.administrator.thehealthy.fragment.inforFrament;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.adapter.HealthEducationAdapter;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.application.BaseApplication;
import com.example.administrator.thehealthy.entity.AppData;
import com.example.administrator.thehealthy.entity.HealthEduEntity;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.MyClickListener;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/3.
 * 健康教育界面
 */
public class HealthEducationFragment extends BaseFragment implements MyClickListener {
    private final String TAG = HealthEducationFragment.class.getSimpleName();
    private RecyclerView educationRv;
    private HealthEducationAdapter educationAdapter;
    private FrameLayout frameLayout;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_health_education;
    }

    @Override
    protected void initView() {
        // 清理之前eduEntityList中的数据
        AppData.eduEntityList.clear();
        frameLayout = findView(R.id.fragment_healthEducation);
        frameLayout.setOnTouchListener(this);
        educationRv = findView(R.id.recyclerView_health_education);
        educationRv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        educationAdapter = new HealthEducationAdapter(getActivity());
        educationAdapter.setMyClickListener(this);
        educationRv.setAdapter(educationAdapter);


//        NetBroadcastReceiver.mListeners.add(this);

    }


    @Override
    protected void initData() {
        if (BaseApplication.isNetwork()) {
            initNetWork();
        } else {
            Toast.makeText(getActivity(),"当前无网络", Toast.LENGTH_SHORT).show();
        }
//        switch (BaseApplication.getNETCHANGE()) {
//            case 1:
//                initNetWork();
//                break;
//            case 2:
//                Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT).show();
//                break;
//        }

//        if (BaseApplication.isNetwork()) {
//            initNetWork();
//        } else {
//            Toast.makeText(getActivity(),"请检查网络",Toast.LENGTH_SHORT).show();
//        }
//        NetBroadcastReceiver.mListeners.add(this);
    }



    private void initNetWork() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.URL_EDUCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Log.i("aaaa", object.getBoolean("error") + "");
                    if (!object.getBoolean("error")) {

                        int length = object.getInt("length");
                        for (int i = 0; i < length; i++) {
                            JSONObject obj = (JSONObject) object.getJSONArray("list").get(i);
                            HealthEduEntity eduEntity = new HealthEduEntity();
                            eduEntity.setItem_id(obj.getInt("item_id"));
                            eduEntity.setTitle(obj.getString("title"));
                            eduEntity.setContent(obj.getString("content"));
                            eduEntity.setCreate_at(obj.getString("create_at"));
                            eduEntity.setCreate_by(obj.getString("create_by"));
                            AppData.eduEntityList.add(eduEntity);
                            educationAdapter.addData(AppData.eduEntityList);
                        }
                    } else {
                        String errorMsg = object.getString("error_msg");
                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络无应答,请连接网络重新进入", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        VolleySingleton.getInstace().addRequest(stringRequest);
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (v.getId()) {
//            case R.id.fragment_healthEducation:
//
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.i(TAG, "--------> " + MotionEvent.ACTION_UP);
//                    if (BaseApplication.isNetwork()) {
//                        Log.i(TAG, "--------> isNetwork()" + BaseApplication.isNetwork());
//                        initNetWork();
//                    }
//                }
//                return true;
//        }
//        return true;
//    }


    @Override
    public void myOnClickListener(int pos) {
        goToNextFragmentFromEducation(new EducationInferFragment(), pos);
    }

    @Override
    public void myOnClickListener(String type_alias, String item_alias, String title, int record_id) {

    }


//    @Override
//    public void netState() {
//        Log.i(TAG,"------->   netState()");
//        Log.i(TAG, "------->   netState()" + NetUtil.getNetworkState(getActivity()));
//
//        switch (BaseApplication.getmNetWorkState()) {
//            case 1:
//                initNetWork();
//                break;
//            case 2:
//                Toast.makeText(getActivity(),"没有网络",Toast.LENGTH_SHORT).show();
//                break;
//        }
        //没有网络
//        if (NetUtil.getNetworkState(getActivity()) == NetUtil.NETWORK_NONE) {
//            Toast.makeText(getActivity(),"没有网络",Toast.LENGTH_SHORT).show();
//        } else {
//            initNetWork();
//        }
//    }
}
