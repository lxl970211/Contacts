package com.zzptc.liuxiaolong.contact;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zzptc.liuxiaolong.contact.Fragment.CallLogsFragment;
import com.zzptc.liuxiaolong.contact.Fragment.ContactsFragment;
import com.zzptc.liuxiaolong.contact.Fragment.DialFragment;
import com.zzptc.liuxiaolong.contact.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {


    //声明四个按钮
    private ImageView mDial_img,mCallLogs_img,mContacts_img;
    //按钮的文字
    private TextView mDial_text,mCallLogs_text,mContacts_text;
    //声明四个tab布局
    private LinearLayout mDial,mCallLogs,mContacts;


    private ViewPager viewpager;

    //fragment适配器
    private FragmentPagerAdapter fragmentadapter;
    //fragment集合
    private List<Fragment> mfragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

       //初始化所有控件
        initviews();
        //初始化事件
        initEvents();
        initdatas();

    }
    public void initviews(){//初始化控件
        viewpager = (ViewPager) findViewById(R.id.view_viewpager);

        mDial = (LinearLayout) findViewById(R.id.id_tab_dial);
        mCallLogs = (LinearLayout) findViewById(R.id.id_tab_calllogs);
        mContacts = (LinearLayout) findViewById(R.id.id_tab_contact);

        mDial_img = (ImageView) findViewById(R.id.id_tab_dial_img);
        mCallLogs_img = (ImageView) findViewById(R.id.id_tab_calllogs_img);
        mContacts_img = (ImageView) findViewById(R.id.id_tab_contact_img);

        mDial_text = (TextView) findViewById(R.id.id_tab_dial_text);
        mCallLogs_text = (TextView) findViewById(R.id.id_tab_calllogs_text);
        mContacts_text = (TextView) findViewById(R.id.id_tab_contact_text);


    }

    public void initEvents(){//初始化事件
        mDial.setOnClickListener(this);
        mContacts.setOnClickListener(this);
        mCallLogs.setOnClickListener(this);
    }

    public void initdatas(){
        mfragments = new ArrayList<>();
        //将四个fragment加入集合当中

        mfragments.add(new DialFragment());
        mfragments.add(new CallLogsFragment());
        mfragments.add(new ContactsFragment());
        //初始化适配器
       fragmentadapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
           @Override
           public Fragment getItem(int position) {
               return mfragments.get(position);
           }

           @Override
           public int getCount() {
               return mfragments.size();
           }
       };
        viewpager.setAdapter(fragmentadapter);
        viewpager.setOffscreenPageLimit(2);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.setCurrentItem(position);
                resetImgs();
                selecttab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    @Override
    public void onClick(View v) {
        resetImgs();
        switch (v.getId()){
            case R.id.id_tab_dial:
                selecttab(0);
                break;
            case R.id.id_tab_calllogs:
                selecttab(1);
                break;
            case R.id.id_tab_contact:
                selecttab(2);
                break;

        }

    }


    public void selecttab(int i){
        switch (i){
            case 0:
                mDial_img.setImageResource(R.mipmap.oh);
                mDial_text.setTextColor(Color.parseColor("#5DC75F"));
                break;

            case 1:
                mCallLogs_img.setImageResource(R.mipmap.od);
                mCallLogs_text.setTextColor(Color.parseColor("#5DC75F"));
                break;
            case 2:
                mContacts_img.setImageResource(R.mipmap.of);
                mContacts_text.setTextColor(Color.parseColor("#5DC75F"));
                break;
        }
        viewpager.setCurrentItem(i);

    }
    private void resetImgs() {
        mDial_img.setImageResource(R.mipmap.og);
        mDial_text.setTextColor(Color.parseColor("#7B7C7C"));
        mCallLogs_img.setImageResource(R.mipmap.oc);
        mCallLogs_text.setTextColor(Color.parseColor("#7B7C7C"));
        mContacts_img.setImageResource(R.mipmap.oe);
        mContacts_text.setTextColor(Color.parseColor("#7B7C7C"));

    }


}
