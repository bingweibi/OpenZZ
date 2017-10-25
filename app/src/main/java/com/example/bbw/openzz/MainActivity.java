package com.example.bbw.openzz;

/**
 * @author bibingwei
 */

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.bbw.openzz.tabRes.TabRes;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mFragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentTabHost = super.findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(this,super.getSupportFragmentManager(),R.id.main_content);
        mFragmentTabHost.getTabWidget().setDividerDrawable(null);
        mFragmentTabHost.setOnTabChangedListener(this);

        //初始化底部导航栏
        initTab();
    }

    private void initTab() {

        String tabs[]  = TabRes.getTabText();
        for (int i=0;i<tabs.length;i++){
            TabHost.TabSpec mTabSpec = mFragmentTabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            mFragmentTabHost.addTab(mTabSpec,TabRes.getFragment()[i],null);
            mFragmentTabHost.setTag(i);
        }
    }

    private View getTabView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_content,null);
        ((TextView)view.findViewById(R.id.tab_content)).setText(TabRes.getTabText()[index]);
        if (index == 0){
            ((TextView)view.findViewById(R.id.tab_content)).setTextColor(Color.RED);
        }
        return view;
    }

    @Override
    public void onTabChanged(String s) {
        updateTab();
    }

    private void updateTab() {
        TabWidget mTabWidget = mFragmentTabHost.getTabWidget();
        for (int i=0;i<mTabWidget.getChildCount();i++){
            View view = mTabWidget.getChildAt(i);
            if (i == mFragmentTabHost.getCurrentTab()){
                ((TextView)view.findViewById(R.id.tab_content)).setTextColor(Color.parseColor("#FFB6C1"));
            }else {
                ((TextView)view.findViewById(R.id.tab_content)).setTextColor(Color.BLACK);
            }
        }
    }
}
