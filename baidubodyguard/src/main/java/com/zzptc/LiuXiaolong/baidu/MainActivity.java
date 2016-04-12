package com.zzptc.LiuXiaolong.baidu;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.LiuXiaolong.baidu.Adapter.MyPagerAdapter;
import com.zzptc.LiuXiaolong.baidu.activity.Home_Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private List<View> list;
    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private CheckBox checkbox;
    private ImageView btn_left;
    private ImageView btn_center;
    private ImageView btn_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initdata();
        initlisten();
    }

    public void init(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_left = (ImageView) findViewById(R.id.btn_left);
        btn_center = (ImageView) findViewById(R.id.btn_center);
        btn_right = (ImageView) findViewById(R.id.btn_right);
    }
    public void initdata(){
        list = new ArrayList<>();

        View splash_first = View.inflate(this,R.layout.activity_splashfirst,null);
        View splash_two = View.inflate(this,R.layout.activity_splashtwo,null);
        View splash_second = View.inflate(this,R.layout.activity_splashsecond,null);

        Button btn_Experience = (Button) splash_second.findViewById(R.id.btn_Experience);
        btn_Experience.setOnClickListener(this);
        checkbox = (CheckBox) splash_second.findViewById(R.id.checkBox);


        TextView jumpover1 = (TextView) splash_first.findViewById(R.id.first_jumpover);
        jumpover1.setOnClickListener(this);

        TextView jumpover2 = (TextView) splash_two.findViewById(R.id.two_jumpover);
        jumpover2.setOnClickListener(this);

        list.add(splash_first);
        list.add(splash_two);
        list.add(splash_second);

        adapter = new MyPagerAdapter(list);
        viewPager.setAdapter(adapter);
    }
    public void initlisten(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        btn_left.setImageResource(R.mipmap.dot_selected);
                        btn_center.setImageResource(R.mipmap.dot_unselected);
                        btn_right.setImageResource(R.mipmap.dot_unselected);
                        break;
                    case 1:

                        btn_left.setImageResource(R.mipmap.dot_unselected);
                        btn_center.setImageResource(R.mipmap.dot_selected);
                        btn_right.setImageResource(R.mipmap.dot_unselected);
                        break;
                    case 2:

                        btn_left.setImageResource(R.mipmap.dot_unselected);
                        btn_center.setImageResource(R.mipmap.dot_unselected);
                        btn_right.setImageResource(R.mipmap.dot_selected);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Experience:
                startActivity(new Intent(this, Home_Activity.class));
                finish();
                break;
            case R.id.first_jumpover:
                startActivity(new Intent(this, Home_Activity.class));
                finish();

                break;
            case R.id.two_jumpover:
                startActivity(new Intent(this, Home_Activity.class));
                finish();
                break;
        }
        overridePendingTransition(R.anim.transition2,R.anim.transition1);
    }
}
