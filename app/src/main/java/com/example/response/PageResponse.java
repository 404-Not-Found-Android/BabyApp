package com.example.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author : ljt
 * Description :
 * CreateTime  : 2021/6/25
 */
public class PageResponse{

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataBean data;
    @SerializedName("ok")
    private Object ok;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getOk() {
        return ok;
    }

    public void setOk(Object ok) {
        this.ok = ok;
    }

    public static class DataBean {
        @SerializedName("photos")
        private List<PhotosBean> photos;

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class PhotosBean {
            @SerializedName("icon")
            private String icon;
            @SerializedName("userName")
            private String userName;
            @SerializedName("images")
            private List<String> images;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }
    }
}
