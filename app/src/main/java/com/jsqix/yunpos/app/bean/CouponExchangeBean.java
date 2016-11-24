package com.jsqix.yunpos.app.bean;

import java.util.List;

/**
 * Created by dongqing on 2016/11/21.
 */

public class CouponExchangeBean extends BaseBean {

    /**
     * obj : {"GoodsInfoList":[{"addtime":"2016-11-21 11:13:26","remark":"融e购","productList":[{"amount":10,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:18:28","product_id":"11","remark":"","product_code":"20161102165044","product_name":"融e购商城通用券10元"},{"amount":20,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:20:20","product_id":"12","remark":"","product_code":"20161103095624","product_name":"融e购商城通用券20元"},{"amount":30,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:20:50","product_id":"13","remark":"","product_code":"20161103095806","product_name":"融e购商城通用券30元"},{"amount":50,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:21:58","product_id":"14","remark":"","product_code":"20161103095916","product_name":"融e购商城通用券50元"},{"amount":100,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:22:26","product_id":"15","remark":"","product_code":"20161103100020","product_name":"融e购商城通用券100元"}],"goods_id":"1","goods_name":"融e购商城通用券","goods_pic":"wap201611023024523177.jpg"}]}
     */

    private ObjBean obj;

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private List<GoodsInfoListBean> GoodsInfoList;

        public List<GoodsInfoListBean> getGoodsInfoList() {
            return GoodsInfoList;
        }

        public void setGoodsInfoList(List<GoodsInfoListBean> GoodsInfoList) {
            this.GoodsInfoList = GoodsInfoList;
        }

        public static class GoodsInfoListBean {
            /**
             * addtime : 2016-11-21 11:13:26
             * remark : 融e购
             * productList : [{"amount":10,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:18:28","product_id":"11","remark":"","product_code":"20161102165044","product_name":"融e购商城通用券10元"},{"amount":20,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:20:20","product_id":"12","remark":"","product_code":"20161103095624","product_name":"融e购商城通用券20元"},{"amount":30,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:20:50","product_id":"13","remark":"","product_code":"20161103095806","product_name":"融e购商城通用券30元"},{"amount":50,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:21:58","product_id":"14","remark":"","product_code":"20161103095916","product_name":"融e购商城通用券50元"},{"amount":100,"product_pic":"wap201611023024523177.jpg","addtime":"2016-11-21 11:22:26","product_id":"15","remark":"","product_code":"20161103100020","product_name":"融e购商城通用券100元"}]
             * goods_id : 1
             * goods_name : 融e购商城通用券
             * goods_pic : wap201611023024523177.jpg
             */

            private String addtime;
            private String remark;
            private String goods_id;
            private String goods_name;
            private String goods_pic;
            private List<ProductListBean> productList;

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_pic() {
                return goods_pic;
            }

            public void setGoods_pic(String goods_pic) {
                this.goods_pic = goods_pic;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public static class ProductListBean {
                /**
                 * amount : 10.0
                 * product_pic : wap201611023024523177.jpg
                 * addtime : 2016-11-21 11:18:28
                 * product_id : 11
                 * remark :
                 * product_code : 20161102165044
                 * product_name : 融e购商城通用券10元
                 */

                private double amount;
                private String product_pic;
                private String addtime;
                private String product_id;
                private String remark;
                private String product_code;
                private String product_name;

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public String getProduct_pic() {
                    return product_pic;
                }

                public void setProduct_pic(String product_pic) {
                    this.product_pic = product_pic;
                }

                public String getAddtime() {
                    return addtime;
                }

                public void setAddtime(String addtime) {
                    this.addtime = addtime;
                }

                public String getProduct_id() {
                    return product_id;
                }

                public void setProduct_id(String product_id) {
                    this.product_id = product_id;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getProduct_code() {
                    return product_code;
                }

                public void setProduct_code(String product_code) {
                    this.product_code = product_code;
                }

                public String getProduct_name() {
                    return product_name;
                }

                public void setProduct_name(String product_name) {
                    this.product_name = product_name;
                }
            }
        }
    }
}
