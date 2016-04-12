package com.zzptc.LiuXiaolong.baidu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zzptc.LiuXiaolong.baidu.MainActivity;
import com.zzptc.LiuXiaolong.baidu.R;

/**
 * Created by lxl97 on 2016/4/12.
 */
public class Welcome extends Activity implements Runnable {
    private boolean isFirstUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        Handler handler = new Handler();
        handler.postDelayed(this,2000);

    }

    @Override
    public void run() {

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.welcome);
            ImageView img = (ImageView) findViewById(R.id.welcome_center);
            img.setAnimation(animation);


        SharedPreferences preferences = getSharedPreferences("isFirstUse",MODE_PRIVATE);
        isFirstUse = preferences.getBoolean("isFirstUse",true);
        if (isFirstUse){

            startActivity(new Intent(this, MainActivity.class));
        }else{

            startActivity(new Intent(this,Home_Activity.class));

        }
        overridePendingTransition(R.anim.transition2,R.anim.transition1);

        finish();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("isFirstUse",false);
        editor.commit();

    }
}
