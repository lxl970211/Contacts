package com.zzptc.liuxiaolong.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.SQLite.FindAllContacts;
import com.zzptc.liuxiaolong.contact.other.Picture;



public class ModifyContacts extends Activity {
    private ImageButton img_modify_return,img_modify_headbut;
    private TextView tv_modify;
    private EditText et_modify_Name,et_modify_phone,et_modify_email;

    private Picture pic = new Picture();
    private FindAllContacts find = new FindAllContacts(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.modify_contacts);
        FindAll();
        img_modify_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        FindAllContacts f = new FindAllContacts(this);
        Contacts con = f.findcontacts(name);

        String et_name = name;
        String phone = con.getPhone();
        String email = con.getEmail();
        Bitmap bitmap = pic.zoomBitmap(con.getPhoto(),180,180);

        et_modify_Name.setText(et_name);
        et_modify_Name.setEnabled(false);

        et_modify_phone.setText(phone);
        et_modify_email.setText(email);
        img_modify_headbut.setImageBitmap(bitmap);

        img_modify_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacts con = new Contacts();
                con.setName(et_modify_Name.getText().toString());
                con.setPhone(et_modify_phone.getText().toString());
                con.setEmail(et_modify_email.getText().toString());


                if (et_modify_phone == null ) {
                    et_modify_phone.setText("");
                } else if (et_modify_email == null) {
                    et_modify_email.setText("");
                } else {
                    if (find.modify(con)) {
                        Toast.makeText(ModifyContacts.this, "修改成功", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(ModifyContacts.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void FindAll(){
        img_modify_return = (ImageButton) findViewById(R.id.img_modify_return);
        img_modify_headbut = (ImageButton) findViewById(R.id.img_modify_headbut);

        tv_modify = (TextView) findViewById(R.id.tv_modify);
        et_modify_Name = (EditText) findViewById(R.id.et_modify_Name);
        et_modify_phone = (EditText) findViewById(R.id.et_modifyphonenumber);
        et_modify_email = (EditText) findViewById(R.id.et_modify_email);



    }
}
