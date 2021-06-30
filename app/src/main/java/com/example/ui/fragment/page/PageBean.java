package com.example.ui.fragment.page;

import java.util.List;

/**
 * Author : ljt
 * Description :
 * CreateTime  : 2021/6/23
 */
public class PageBean {
    private String userName;
    private String userIcon;
    private List<String> images;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
