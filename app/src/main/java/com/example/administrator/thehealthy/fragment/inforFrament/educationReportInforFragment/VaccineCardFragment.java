package com.example.administrator.thehealthy.fragment.inforFrament.educationReportInforFragment;

import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.application.AppConfig;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class VaccineCardFragment extends BaseFragment {
    private final String TAG = VaccineCardFragment.class.getSimpleName();
    private DBTool dbTool;
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_vaccine_card;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();
        scrollViewAfter = findView(R.id.scrollView_vaccine);
        scrollViewOnTouch.setScrollView(scrollViewAfter);

    }

    @Override
    protected void initData() {
        final HashMap<String, String> user = dbTool.getUserDetails();
        final Integer record_id = getArguments().getInt("record_id", 0);

        if (record_id != 0) {
            Log.e(TAG, "开始从后台获取详情");
            final StringRequest detailReq = new StringRequest(
                    Request.Method.POST,
                    AppConfig.URL_DETAIL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (!obj.getBoolean("error")) {
                                    JSONObject detail = obj.getJSONObject("detail");
                                    TextView name = findView(R.id.name);
                                    name.setText(user.get("name"));
                                    TextView gender = findView(R.id.gender);
                                    gender.setText(detail.getString("gender"));
                                    TextView birth_date = findView(R.id.birth_date);
                                    birth_date.setText(detail.getString("birth_date"));
                                    TextView guardian_name = findView(R.id.guardian_name);
                                    guardian_name.setText(detail.getString("guardian_name"));
                                    TextView relation_to_child = findView(R.id.relation_to_child);
                                    relation_to_child.setText(detail.getString("relation_to_child"));
                                    TextView contact_number = findView(R.id.contact_number);
                                    contact_number.setText(detail.getString("contact_number"));
                                    TextView home_county = findView(R.id.home_county);
                                    home_county.setText(detail.getString("home_county"));
                                    TextView home_town = findView(R.id.home_town);
                                    home_town.setText(detail.getString("home_town"));
                                    if (!detail.getString("register_local").equals("false")) {
                                        TextView register_local = findView(R.id.register_local);
                                        register_local.setText(detail.getString("register_local"));
                                    }
                                    TextView register_province = findView(R.id.register_province);
                                    register_province.setText(detail.getString("register_province"));
                                    TextView register_city = findView(R.id.register_city);
                                    register_city.setText(detail.getString("register_city"));
                                    TextView register_county = findView(R.id.register_county);
                                    register_county.setText(detail.getString("register_county"));
                                    TextView register_town = findView(R.id.register_town);
                                    register_town.setText(detail.getString("register_town"));
                                    if (!detail.getString("immigrate_time").equals("null")) {
                                        TextView immigrate_time = findView(R.id.immigrate_time);
                                        immigrate_time.setText(detail.getString("immigrate_time"));
                                    }
                                    if (!detail.getString("emigrate_time").equals("null")) {
                                        TextView emigrate_time = findView(R.id.emigrate_time);
                                        emigrate_time.setText(detail.getString("emigrate_time"));
                                    }
                                    TextView emigrate_reason = findView(R.id.emigrate_reason);
                                    emigrate_reason.setText(detail.getString("emigrate_reason"));
                                    TextView vaccine_abnormal_reaction_history = findView(R.id.vaccine_abnormal_reaction_history);
                                    vaccine_abnormal_reaction_history.setText(detail.getString("vaccine_abnormal_reaction_history"));
                                    TextView vaccinate_taboo = findView(R.id.vaccinate_taboo);
                                    vaccinate_taboo.setText(detail.getString("vaccinate_taboo"));
                                    TextView infection_history = findView(R.id.infection_history);
                                    infection_history.setText(detail.getString("infection_history"));
                                    TextView found_card_date = findView(R.id.found_card_date);
                                    found_card_date.setText(detail.getString("found_card_date"));
                                    TextView found_card_person = findView(R.id.found_card_person);
                                    found_card_person.setText(detail.getString("found_card_person"));


                                } else {
                                    String errorMsg = obj.getString("error_msg");
                                    Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("record_id", Integer.toString(record_id));
                    Log.e(TAG, "to get record_id: " + record_id);
                    return params;
                }
            };

            VolleySingleton.getInstace().addRequest(detailReq);
        } else {
            Toast.makeText(getActivity(), "没有获得记录ID", Toast.LENGTH_LONG).show();
        }
    }
}
