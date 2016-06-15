package com.zzptc.liuxiaolong.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;



import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.SQLite.FindAllContacts;
import com.zzptc.liuxiaolong.contact.other.Roundcorner;

import java.io.File;

/**
 * Created by lxl97 on 2016/2/13.
 */
public class AddContacts extends Activity {
    private EditText et_firstname,et_lastname,et_addphonenumber,et_addemail;
    private ImageButton img_return;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private ImageButton headImage = null;
    Bitmap headphoto = null;
    String phonenumber = null;
    String email = null;

    private Roundcorner r = new Roundcorner();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contacts);
        //找到所有控件
        FindAll();
        registerForContextMenu(headImage);
        //返回按钮监听，返回后关闭此activity
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("phonenumber");
        et_addphonenumber.setText(phonenumber);
        }

    /*
    头像上下文菜单
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(1, 1, 1, "从本地选取");
        menu.add(1,2,2,"启动相机");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case 1:
                //从相册选取图片设置为头像，--功能无法使用
                choseHeadImageFromGallery();
                break;
            case 2:
                //拍照设置为头像
                choseHeadImageFromCameraCapture();
                break;
        }
        return super.onContextItemSelected(item);

    }
    //找到所有控件
    public void FindAll(){
        et_firstname = (EditText) findViewById(R.id.et_firstName);
        et_lastname = (EditText) findViewById(R.id.et_lastName);
        et_addphonenumber = (EditText) findViewById(R.id.et_addphonenumber);
        et_addemail = (EditText) findViewById(R.id.et_addemail);
        headImage = (ImageButton) findViewById(R.id.img_headbut);
        img_return = (ImageButton) findViewById(R.id.img_addcon_return);
    }
    /*
    保存联系人
     */
    public void savecontacts(View view){
        String name = et_firstname.getText().toString()+et_lastname.getText().toString().trim();
        String phonenumber = et_addphonenumber.getText().toString().trim();
        String email = et_addemail.getText().toString().trim();

        if (name == null || name == ""){
            Toast.makeText(AddContacts.this, "请输入姓名", Toast.LENGTH_SHORT).show();
        }else if (phonenumber == null || phonenumber == ""){
            phonenumber = "";
        }else if (email == null || email == ""){
            email = "";
        }else {

            Contacts con = new Contacts();
            con.setName(name);
            con.setPhone(phonenumber);
            con.setEmail(email);
            con.setPhoto(headphoto);


            FindAllContacts find = new FindAllContacts(this);

            if (find.AddContacts(con)) {
                Toast.makeText(AddContacts.this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(this, ContactsDetails_Activity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            } else {
                Toast.makeText(AddContacts.this, "添加失败", Toast.LENGTH_SHORT).show();

            }
        }
        }

//-----------------------------------------------------
    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Bitmap pho = r.GetRoundedCornerBitmap(photo);
            headImage.setImageBitmap(pho);
            headphoto = pho;
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
}

