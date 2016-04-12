package com.zzptc.LiuXiaolong.baidu.Adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lxl97 on 2016/4/11.
 */
public class MyPagerAdapter extends android.support.v4.view.PagerAdapter {
    private List<View> list;
    public MyPagerAdapter(List<View> list){
        this.list = list;
    }
    @Override
    public int getCount() {
            return list.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }
}
