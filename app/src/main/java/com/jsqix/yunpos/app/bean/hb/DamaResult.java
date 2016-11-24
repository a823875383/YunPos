package com.jsqix.yunpos.app.bean.hb;

/**
 * Created by dongqing on 2016/11/18.
 */

public class DamaResult {

    /**
     * result : true
     * data : {"val":"TBHR","id":2037086793}
     */

    private boolean result;
    private DataBean data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * val : TBHR
         * id : 2037086793
         */

        private String val;
        private String id;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
