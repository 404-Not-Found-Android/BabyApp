package com.example.response;

import com.google.gson.annotations.SerializedName;

/**
 * Author : ljt
 * Description :
 * CreateTime  : 2021/6/21
 */
public class UploadResponse extends BaseResponse {


    /**
     * id : 2
     * createTime : 1624249770553
     * url : http://192.168.2.147/image//IMG_20210414_143721.jpg
     * uploadAuthor : admin
     * fileName : IMG_20210414_143721.jpg
     * fileUuid : 414beb29-826e-4893-97a0-cbbc456eb05e
     */

    @SerializedName("data")
    private DataBean data;
    /**
     * data : {"id":2,"createTime":1624249770553,"url":"http://192.168.2.147/image//IMG_20210414_143721.jpg","uploadAuthor":"admin","fileName":"IMG_20210414_143721.jpg","fileUuid":"414beb29-826e-4893-97a0-cbbc456eb05e"}
     * msg : 上传成功
     * status : 200
     */

    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        @SerializedName("id")
        private int id;
        @SerializedName("createTime")
        private long createTime;
        @SerializedName("url")
        private String url;
        @SerializedName("uploadAuthor")
        private String uploadAuthor;
        @SerializedName("fileName")
        private String fileName;
        @SerializedName("fileUuid")
        private String fileUuid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUploadAuthor() {
            return uploadAuthor;
        }

        public void setUploadAuthor(String uploadAuthor) {
            this.uploadAuthor = uploadAuthor;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileUuid() {
            return fileUuid;
        }

        public void setFileUuid(String fileUuid) {
            this.fileUuid = fileUuid;
        }
    }
}
