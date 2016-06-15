package com.zzptc.liuxiaolong.contact.Model;

import android.graphics.Bitmap;

/**
 * Created by lxl97 on 2016/1/18.
 */
public class Contacts{
    private String id;
    private String name;
    private String phone;
    private Bitmap photo;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
