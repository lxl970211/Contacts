package com.zzptc.liuxiaolong.contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.SQLite.FindAllContacts;
import com.zzptc.liuxiaolong.contact.other.Roundcorner;


/**
 * Created by lxl97 on 2016/1/22.
 */
public class ContactsDetails_Activity extends Activity{
    private TextView tv_conInfo_name,tv_conInfo_phonenumber;
    private ImageView iv_coninfo_photo;
    private ImageButton imgbut_call,imgbut_message,imgbut_menu,img_return;
    private PopupWindow popupwindow;

    private Roundcorner r;

    FindAllContacts findAllContacts = new FindAllContacts(this);
    private String name = null;

    @Override
    protected void onStart() {
        //设置联系人详情信息
        final Intent intent = getIntent();
        if (intent != null){
            name = intent.getStringExtra("name");
            FindAllContacts find = new FindAllContacts(this);
            Contacts con = find.findcontacts(name);
            tv_conInfo_name.setText(name);
            tv_conInfo_phonenumber.setText(con.getPhone());
            iv_coninfo_photo.setImageBitmap(con.getPhoto());
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_details);
        //找到所有控件
        findAll();




        //拨号按钮监听
        imgbut_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = tv_conInfo_phonenumber.getText().toString();
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:" + num));
                startActivity(intent1);
            }
        });
        /*
        菜单
         */
        final View popupView = getLayoutInflater().inflate(R.layout.contacts_details_menu,null);
        LinearLayout layout_modify = (LinearLayout) popupView.findViewById(R.id.layout_modify);
        LinearLayout layout_delete = (LinearLayout) popupView.findViewById(R.id.layout_delete);

                layout_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent modify = new Intent(ContactsDetails_Activity.this,ModifyContacts.class);

                modify.putExtra("name", tv_conInfo_name.getText().toString());

                startActivity(modify);

                if (null != popupwindow && popupwindow.isShowing()) {
                        popupwindow.dismiss();
                }
            }
        });
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.setTitle("确定删除此联系人?");
               dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               }) ;
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (findAllContacts.deleteContacts(name)) {
                            Toast.makeText(ContactsDetails_Activity.this, "已删除", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(ContactsDetails_Activity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dia = dialog.create();
                dia.show();

                if (null != popupwindow && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                }
            }
        });
        popupwindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupwindow.setTouchable(true);
        popupwindow.setOutsideTouchable(true);
        popupwindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        imgbut_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindow.showAsDropDown(v);
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //找到所有控件
    public void findAll(){
        tv_conInfo_name = (TextView) findViewById(R.id.tv_conInfo_name);
        tv_conInfo_phonenumber = (TextView) findViewById(R.id.tv_conInfo_phonenumber);
        imgbut_call = (ImageButton) findViewById(R.id.imgbut_call);
        imgbut_message = (ImageButton) findViewById(R.id.imgbut_message);
        imgbut_menu = (ImageButton) findViewById(R.id.imgbut_menu);
        img_return = (ImageButton) findViewById(R.id.img_return);
        iv_coninfo_photo = (ImageView) findViewById(R.id.iv_conInfo_photo);
    }
}
