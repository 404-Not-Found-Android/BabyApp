package com.example.response;

import com.google.gson.annotations.SerializedName;

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/20/21
 */
public class BaseResponse {

    /**
     * status : 200
     * msg : OK
     * data : null
     * ok : null
     */

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
