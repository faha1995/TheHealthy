package com.example.administrator.thehealthy.fragment.inforFrament.healthReportInforFragment;

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
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.entity.AppConfig;
import com.example.administrator.thehealthy.fragment.BaseSonFragment;
import com.example.administrator.thehealthy.tools.ScrollViewOnTouch;
import com.example.administrator.thehealthy.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16.
 */
public class NewbornFamilyVisitFragment extends BaseSonFragment {
    private final String TAG = NewbornFamilyVisitFragment.class.getSimpleName();
    private DBTool dbTool;
    private ScrollView scrollViewAfter;
    private ScrollViewOnTouch scrollViewOnTouch = new ScrollViewOnTouch();

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_newborn_family_visit;
    }

    @Override
    protected void initView() {
        dbTool = new DBTool();
        scrollViewAfter = findView(R.id.scrollView_newborn);
        scrollViewOnTouch.setScrollView(scrollViewAfter);
    }

    @Override
    protected void initData() {
        final Integer record_id = getArguments().getInt("record_id", 0);
        final HashMap<String, String> user = dbTool.getUserDetails();

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
                                    // Toast.makeText(getApplicationContext(), detail.getString("visit_date"), Toast.LENGTH_SHORT).show();
                                    TextView visit_date = findView(R.id.visit_date);
                                    visit_date.setText(detail.getString("visit_date"));
                                    TextView name = findView(R.id.name);
                                    name.setText(user.get("name"));
                                    TextView gender = findView(R.id.gender);
                                    gender.setText(detail.getString("gender"));
                                    TextView birthday = findView(R.id.birthday);
                                    birthday.setText(detail.getString("birthday"));
                                    TextView identity = findView(R.id.identity);
                                    identity.setText(detail.getString("identity"));
                                    TextView address = findView(R.id.address);
                                    address.setText(detail.getString("address"));
                                    TextView father_name = findView(R.id.father_name);
                                    father_name.setText(detail.getString("father_name"));
                                    TextView father_occupation = findView(R.id.father_occupation);
                                    father_occupation.setText(detail.getString("father_occupation"));
                                    TextView father_contact_number = findView(R.id.father_contact_number);
                                    father_contact_number.setText(detail.getString("father_contact_number"));
                                    TextView father_birthday = findView(R.id.father_birthday);
                                    father_birthday.setText(detail.getString("father_birthday"));
                                    TextView mother_name = findView(R.id.mother_name);
                                    mother_name.setText(detail.getString("mother_name"));
                                    TextView mother_occupation = findView(R.id.mother_occupation);
                                    mother_occupation.setText(detail.getString("mother_occupation"));
                                    TextView mother_contact_number = findView(R.id.mother_contact_number);
                                    mother_contact_number.setText(detail.getString("mother_contact_number"));
                                    TextView mother_birthday = findView(R.id.mother_birthday);
                                    mother_birthday.setText(detail.getString("mother_birthday"));
                                    TextView gestational_weeks = findView(R.id.gestational_weeks);
                                    gestational_weeks.setText(detail.getString("gestational_weeks"));
                                    TextView mother_gestational_disease = findView(R.id.mother_gestational_disease);
                                    mother_gestational_disease.setText(detail.getString("mother_gestational_disease"));
                                    TextView mother_gestational_disease_extra = findView(R.id.mother_gestational_disease_extra);
                                    mother_gestational_disease_extra.setText(detail.getString("mother_gestational_disease_extra"));
                                    TextView deliver_institution = findView(R.id.deliver_institution);
                                    deliver_institution.setText(detail.getString("deliver_institution"));
                                    TextView birth_situation = findView(R.id.birth_situation);
                                    birth_situation.setText(detail.getString("birth_situation"));
                                    TextView birth_situation_extra = findView(R.id.birth_situation_extra);
                                    birth_situation_extra.setText(detail.getString("birth_situation_extra"));
                                    TextView newborn_asphyxia = findView(R.id.newborn_asphyxia);
                                    newborn_asphyxia.setText(detail.getString("newborn_asphyxia"));
                                    TextView apgar_score = findView(R.id.apgar_score);
                                    apgar_score.setText(detail.getString("apgar_score"));
                                    TextView malformation_or_not = findView(R.id.malformation_or_not);
                                    malformation_or_not.setText(detail.getString("malformation_or_not"));
                                    TextView malformation_extra = findView(R.id.malformation_extra);
                                    malformation_extra.setText(detail.getString("malformation_extra"));
                                    TextView newborn_hearing_screening = findView(R.id.newborn_hearing_screening);
                                    newborn_hearing_screening.setText(detail.getString("newborn_hearing_screening"));
                                    TextView newborn_disease_screening = findView(R.id.newborn_disease_screening);
                                    newborn_disease_screening.setText(detail.getString("newborn_disease_screening"));
                                    TextView newborn_disease_screening_extra = findView(R.id.newborn_disease_screening_extra);
                                    newborn_disease_screening_extra.setText(detail.getString("newborn_disease_screening_extra"));
                                    TextView newborn_birth_weight = findView(R.id.newborn_birth_weight);
                                    newborn_birth_weight.setText(detail.getString("newborn_birth_weight"));
                                    TextView now_weight = findView(R.id.now_weight);
                                    now_weight.setText(detail.getString("now_weight"));
                                    TextView birth_height = findView(R.id.birth_height);
                                    birth_height.setText(detail.getString("birth_height"));
                                    TextView feed_way = findView(R.id.feed_way);
                                    feed_way.setText(detail.getString("feed_way"));
                                    if (!detail.getString("drink_milk_volume").equals("null")) {
                                        TextView drink_milk_volume = findView(R.id.drink_milk_volume);
                                        drink_milk_volume.setText(detail.getString("drink_milk_volume"));
                                    }
                                    if (!detail.getString("drink_milk_times").equals("null")) {
                                        TextView drink_milk_times = findView(R.id.drink_milk_times);
                                        drink_milk_times.setText(detail.getString("drink_milk_times"));
                                    }
                                    if (!detail.getString("emesis").equals("null")) {
                                        TextView emesis = findView(R.id.emesis);
                                        emesis.setText(detail.getString("emesis"));
                                    }
                                    if (!detail.getString("shit").equals("null")) {
                                        TextView shit = findView(R.id.shit);
                                        shit.setText(detail.getString("shit"));
                                    }
                                    if (!detail.getString("shit_times").equals("null")) {
                                        TextView shit_times = findView(R.id.shit_times);
                                        shit_times.setText(detail.getString("shit_times"));
                                    }
                                    TextView body_temperature = findView(R.id.body_temperature);
                                    body_temperature.setText(detail.getString("body_temperature"));
                                    TextView pulse = findView(R.id.pulse);
                                    pulse.setText(detail.getString("pulse"));
                                    TextView breath_frequency = findView(R.id.breath_frequency);
                                    breath_frequency.setText(detail.getString("breath_frequency"));
                                    TextView complexion = findView(R.id.complexion);
                                    complexion.setText(detail.getString("complexion"));
                                    TextView complexion_extra = findView(R.id.complexion_extra);
                                    complexion_extra.setText(detail.getString("complexion_extra"));
                                    TextView icterus_position = findView(R.id.icterus_position);
                                    icterus_position.setText(detail.getString("icterus_position"));
                                    TextView bregma_x = findView(R.id.bregma_x);
                                    bregma_x.setText(detail.getString("bregma_x"));
                                    TextView bregma_y = findView(R.id.bregma_y);
                                    bregma_y.setText(detail.getString("bregma_y"));
                                    TextView bregma_1 = findView(R.id.bregma_1);
                                    bregma_1.setText(detail.getString("bregma_1"));
                                    TextView bregma_1_extra = findView(R.id.bregma_1_extra);
                                    bregma_1_extra.setText(detail.getString("bregma_1_extra"));
                                    TextView eye_appearance = findView(R.id.eye_appearance);
                                    eye_appearance.setText(detail.getString("eye_appearance"));
                                    TextView eye_appearance_abnormal = findView(R.id.eye_appearance_abnormal);
                                    eye_appearance_abnormal.setText(detail.getString("eye_appearance_abnormal"));
                                    TextView all_fours_activity = findView(R.id.all_fours_activity);
                                    all_fours_activity.setText(detail.getString("all_fours_activity"));
                                    TextView all_fours_activity_abnormal = findView(R.id.all_fours_activity_abnormal);
                                    all_fours_activity_abnormal.setText(detail.getString("all_fours_activity_abnormal"));
                                    TextView ear_appearance = findView(R.id.ear_appearance);
                                    ear_appearance.setText(detail.getString("ear_appearance"));
                                    TextView ear_appearance_abnormal = findView(R.id.ear_appearance_abnormal);
                                    ear_appearance_abnormal.setText(detail.getString("ear_appearance_abnormal"));
                                    TextView neck_enclosed_mass = findView(R.id.neck_enclosed_mass);
                                    neck_enclosed_mass.setText(detail.getString("neck_enclosed_mass"));
                                    TextView neck_enclosed_mass_yes = findView(R.id.neck_enclosed_mass_yes);
                                    neck_enclosed_mass_yes.setText(detail.getString("neck_enclosed_mass_yes"));
                                    TextView nose = findView(R.id.nose);
                                    nose.setText(detail.getString("nose"));
                                    TextView nose_abnormal = findView(R.id.nose_abnormal);
                                    nose_abnormal.setText(detail.getString("nose_abnormal"));
                                    TextView skin = findView(R.id.skin);
                                    skin.setText(detail.getString("skin"));
                                    TextView skin_extra = findView(R.id.skin_extra);
                                    skin_extra.setText(detail.getString("skin_extra"));
                                    TextView oral_cavity = findView(R.id.oral_cavity);
                                    oral_cavity.setText(detail.getString("oral_cavity"));
                                    TextView oral_cavity_abnormal = findView(R.id.oral_cavity_abnormal);
                                    oral_cavity_abnormal.setText(detail.getString("oral_cavity_abnormal"));
                                    TextView anus = findView(R.id.anus);
                                    anus.setText(detail.getString("anus"));
                                    TextView anus_abnormal = findView(R.id.anus_abnormal);
                                    anus_abnormal.setText(detail.getString("anus_abnormal"));
                                    TextView heart_lung_auscultation = findView(R.id.heart_lung_auscultation);
                                    heart_lung_auscultation.setText(detail.getString("heart_lung_auscultation"));
                                    TextView heart_lung_auscultation_abnormal = findView(R.id.heart_lung_auscultation_abnormal);
                                    heart_lung_auscultation_abnormal.setText(detail.getString("heart_lung_auscultation_abnormal"));
                                    TextView externalia = findView(R.id.externalia);
                                    externalia.setText(detail.getString("externalia"));
                                    TextView externalia_abnormal = findView(R.id.externalia_abnormal);
                                    externalia_abnormal.setText(detail.getString("externalia_abnormal"));
                                    TextView abdomen_palpation = findView(R.id.abdomen_palpation);
                                    abdomen_palpation.setText(detail.getString("abdomen_palpation"));
                                    TextView abdomen_palpation_abnormal = findView(R.id.abdomen_palpation_abnormal);
                                    abdomen_palpation_abnormal.setText(detail.getString("abdomen_palpation_abnormal"));
                                    TextView spine = findView(R.id.spine);
                                    spine.setText(detail.getString("spine"));
                                    TextView spine_abnormal = findView(R.id.spine_abnormal);
                                    spine_abnormal.setText(detail.getString("spine_abnormal"));
                                    TextView navel = findView(R.id.navel);
                                    navel.setText(detail.getString("navel"));
                                    TextView navel_extra = findView(R.id.navel_extra);
                                    navel_extra.setText(detail.getString("navel_extra"));
                                    TextView transfer_treatment_suggestion = findView(R.id.transfer_treatment_suggestion);
                                    transfer_treatment_suggestion.setText(detail.getString("transfer_treatment_suggestion"));
                                    TextView transfer_treatment_suggestion_reason = findView(R.id.transfer_treatment_suggestion_reason);
                                    transfer_treatment_suggestion_reason.setText(detail.getString("transfer_treatment_suggestion_reason"));
                                    TextView transfer_treatment_suggestion_institution = findView(R.id.transfer_treatment_suggestion_institution);
                                    transfer_treatment_suggestion_institution.setText(detail.getString("transfer_treatment_suggestion_institution"));
                                    TextView guide = findView(R.id.guide);
                                    guide.setText(detail.getString("guide"));
                                    TextView doctor_signature = findView(R.id.doctor_signature);
                                    doctor_signature.setText(detail.getString("doctor_signature"));
                                    TextView next_visit_date = findView(R.id.next_visit_date);
                                    next_visit_date.setText(detail.getString("next_visit_date"));
                                    TextView next_visit_place = findView(R.id.next_visit_place);
                                    next_visit_place.setText(detail.getString("next_visit_place"));


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
