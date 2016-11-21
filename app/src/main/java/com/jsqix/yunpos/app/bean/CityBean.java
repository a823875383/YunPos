package com.jsqix.yunpos.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dq on 2016/7/4.
 */
public class CityBean extends BaseBean {

    /**
     * level : 1
     * name : 北京市
     * code : 110000
     * parent_code : 0
     */

    private List<ObjEntity> obj;

    public List<ObjEntity> getObj() {
        return obj;
    }

    public void setObj(List<ObjEntity> obj) {
        this.obj = obj;
    }

    public static class ObjEntity implements Parcelable {

        private int level;
        private String name;
        private String code;
        private String parent_code;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getParent_code() {
            return parent_code;
        }

        public void setParent_code(String parent_code) {
            this.parent_code = parent_code;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.level);
            dest.writeString(this.name);
            dest.writeString(this.code);
            dest.writeString(this.parent_code);
        }

        public ObjEntity() {
        }

        protected ObjEntity(Parcel in) {
            this.level = in.readInt();
            this.name = in.readString();
            this.code = in.readString();
            this.parent_code = in.readString();
        }

        public static final Creator<ObjEntity> CREATOR = new Creator<ObjEntity>() {
            @Override
            public ObjEntity createFromParcel(Parcel source) {
                return new ObjEntity(source);
            }

            @Override
            public ObjEntity[] newArray(int size) {
                return new ObjEntity[size];
            }
        };
    }


}
