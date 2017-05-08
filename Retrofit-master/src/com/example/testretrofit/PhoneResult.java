package com.example.testretrofit;

public class PhoneResult {
    /**
     * errNum : 0
     * retMsg : success
     * retData : {"phone":"15210011578","prefix":"1521001","supplier":"�ƶ�","province":"����","city":"����","suit":"152��"}
     */
    private int errNum;
    private String retMsg;
    /**
     * phone : 15210011578
     * prefix : 1521001
     * supplier : �ƶ�
     * province : ����
     * city : ����
     * suit : 152��
     */
    private RetDataEntity retData;
 
    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }
 
    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
 
    public void setRetData(RetDataEntity retData) {
        this.retData = retData;
    }
 
    public int getErrNum() {
        return errNum;
    }
 
    public String getRetMsg() {
        return retMsg;
    }
 
    public RetDataEntity getRetData() {
        return retData;
    }
 
    public static class RetDataEntity {
        private String phone;
        private String prefix;
        private String supplier;
        private String province;
        private String city;
        private String suit;
 
        public void setPhone(String phone) {
            this.phone = phone;
        }
 
        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
 
        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }
 
        public void setProvince(String province) {
            this.province = province;
        }
 
        public void setCity(String city) {
            this.city = city;
        }
 
        public void setSuit(String suit) {
            this.suit = suit;
        }
 
        public String getPhone() {
            return phone;
        }
 
        public String getPrefix() {
            return prefix;
        }
 
        public String getSupplier() {
            return supplier;
        }
 
        public String getProvince() {
            return province;
        }
 
        public String getCity() {
            return city;
        }
 
        public String getSuit() {
            return suit;
        }
    }
}

