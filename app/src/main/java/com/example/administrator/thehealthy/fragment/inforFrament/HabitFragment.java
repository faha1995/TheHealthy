package com.example.administrator.thehealthy.fragment.inforFrament;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ChangeString;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/7.
 * 生活习惯界面
 */
public class HabitFragment extends BaseFragment {
    private final String TAG = HabitFragment.class.getSimpleName();
    private TextView exhaustText, fuelText, waterText, toiletText,
            liveStokeText, exposureText;
    private DBTool dbTool;
    private LinearLayout linearLayout;
    private GestureDetector gestureDetector;
int startX,stopX;
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
        exposureText = findView(R.id.text_information_exposure);
        linearLayout = findView(R.id.linear_habit);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = (int) event.getX();
                    Log.i("startX","--------->"+ startX);
                }

                else if (event.getAction() == MotionEvent.ACTION_MOVE){
                    stopX = (int) event.getX();
                    Log.i("stopX","--------->"+ stopX);
                }

                else if (stopX - startX >200){
                    Log.i("--","--------->"+ (stopX - startX));
                    backBeforFragment();
                }
                return true;
            }
        });

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
                        exposureText.setText(
                                ChangeString.splitMain(jsonObject.getString("expose_history")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
