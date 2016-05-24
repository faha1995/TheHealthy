package com.seimun.mobileHealth.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/3/4.
 */
public class PersonalAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<Fragment> fragmentList;
    private List<String> titles;

    public PersonalAdapter(FragmentManager fm,Context context,List<Fragment> fragmentList,List<String> titles) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position % titles.size());
    }

    // 为Tab设置布局
//    public View getTabView(int position,TabLayout.Tab tab){
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.personaladapter_tabview, null);
//        TextView textView = (TextView) view.findViewById(R.id.text_personalAdapter);
//        textView.setText(titles[position]);
//        textView.setTextSize(13);
//        return  view;
//    }

}
