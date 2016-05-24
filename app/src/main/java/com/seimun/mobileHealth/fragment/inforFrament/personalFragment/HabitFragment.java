package com.seimun.mobileHealth.fragment.inforFrament.personalFragment;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.seimun.mobileHealth.db.DBTool;
import com.seimun.mobileHealth.entity.AppConfig;
import com.seimun.mobileHealth.fragment.BaseSonFragment;
import com.seimun.mobileHealth.tools.ScrollViewOnTouch;
import com.seimun.mobileHealth.volley.VolleySingleton;
import com.example.administrator.thehealthy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7.
 * 生活习惯界面
 */
public class HabitFragment extends BaseSonFragment {
    private final String TAG = HabitFragment.class.getSimpleName();
    private TextView exhaustText, fuelText, waterText, toiletText,
            liveStokeText;
    private DBTool dbTool;
    private LinearLayout linearLayout;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_habit;
    }

    @Override
    protected void initView() {
        exhaustText = findView(R.id.text_information_exhaust);
        fuelText = findView(R.id.text_information_fuel);
        waterText = findView(R.id.text_information_water);
        toiletText = findView(R.id.text_information_toilet);
        liveStokeText = findView(R.id.text_information_liveStock);



        linearLayout = findView(R.id.linear_habit);
        ScrollViewOnTouch.getInstance().setViewFinishTouchFromFragment(linearLayout);

        dbTool = new DBTool();
    }

    @Override
    protected void initData() {
        final HashMap<String, String> user = dbTool.getUserDetails();

        StringRequest request = new StringRequest(Request.Method.POST,
                AppConfig.URL_PERSONAL_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "生活习惯网络通信响应");

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    if (!error) {
                        exhaustText.setText(jsonObject.getString("surroundings_kitchen_exhaust"));
                        fuelText.setText(jsonObject.getString("surroundings_fuel_type"));
                        waterText.setText(jsonObject.getString("surroundings_water"));
                        toiletText.setText(jsonObject.getString("surroundings_toilet"));
                        liveStokeText.setText(jsonObject.getString("surrounding_livestock_fence"));

                    } else if (jsonObject.getString("error_msg").equals("The resident has not fill the personal info table")) {
                        showAlertDialog("请前往卫生院完善个人信息", 2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("resident_id", user.get("resident_id"));
                return params;
            }
        };
        VolleySingleton.getInstace().addRequest(request);
    }


}
