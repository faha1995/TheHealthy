package com.example.administrator.thehealthy.fragment.inforFrament;

import android.widget.TextView;

import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.db.DBTool;
import com.example.administrator.thehealthy.fragment.BaseFragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/7.
 * 既往病史界面
 */
public class MedicalHistoryFragment extends BaseFragment {
    private final String TAG = MedicalHistoryFragment.class.getSimpleName();
    private TextView diseaseText, surgeryText, traumaText, bloodText, disabilityText,
            fatherSickText, momSickText, bortherSickText, sonSickText, alergyText,
            geneticText;
    private DBTool dbTool;

    @Override
    protected int setLayoutView() {
        return R.layout.fragment_medicalhistory;
    }

    @Override
    protected void initView() {
        diseaseText = findView(R.id.text_medicalHistory_disease);
        surgeryText = findView(R.id.text_medicalHistory_disease);
        traumaText = findView(R.id.text_medicalHistory_disease);
        bloodText = findView(R.id.text_medicalHistory_disease);
        disabilityText = findView(R.id.text_medicalHistory_disease);
        fatherSickText = findView(R.id.text_medicalHistory_disease);
        momSickText = findView(R.id.text_medicalHistory_disease);
        bortherSickText = findView(R.id.text_medicalHistory_disease);
        sonSickText = findView(R.id.text_medicalHistory_disease);
        alergyText = findView(R.id.text_medicalHistory_disease);
        geneticText = findView(R.id.text_medicalHistory_disease);
    }

    @Override
    protected void initData() {
         final HashMap<String, String> user = dbTool.getUserDetails();

    }
}
