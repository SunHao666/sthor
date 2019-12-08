package com.hao.jsthor.network;

public class BaseCallModel<T> {

    /**
     * code : 200
     * data : {"totalRow":1,"pageNumber":1,"firstPage":true,"lastPage":true,"totalPage":1,"pageSize":20,"list":[{"date":"2019-11-12 14:37:42","supplierName":"北京天士力医药有限公司","reportedLossNo":"GKBSD0000000001","innName":"测试货品3","batchNo":"2e12e","manufacturerName":"上海浦东金环医疗用品股份有限公司","specification":"7","purchasePrice":30000,"userName":"管理员","tradeMark":"s上海浦东金环医疗用品股份有限公司","tradeName":"测试货品3","tempcolumn":0,"articalNumber":"","rfid":"0001280696229034","temprownumber":1,"drugSerialNo":"YPJBXX008824"}]}
     * success : true
     * mess : 成功
     */

    public int code;
    public T data;
    public boolean success;
    public String mess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
