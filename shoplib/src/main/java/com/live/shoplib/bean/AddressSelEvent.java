package com.live.shoplib.bean;

/**
 * 作者：Mr.Xu
 * 时间：2017/12/28
 */
public class AddressSelEvent {

    private boolean type=true;//有无地址
    private MyRecAddressModel.DEntity dEntity;
    public AddressSelEvent(MyRecAddressModel.DEntity dEntity,boolean type) {
        this.dEntity = dEntity;
        this.type=type;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public MyRecAddressModel.DEntity getdEntity() {
        return dEntity;
    }

    public void setdEntity(MyRecAddressModel.DEntity dEntity) {
        this.dEntity = dEntity;
    }
}
