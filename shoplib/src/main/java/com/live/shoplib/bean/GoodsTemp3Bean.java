package com.live.shoplib.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/19.
 */

public class GoodsTemp3Bean {
    /**
     * id : 27
     * spec_group : 含量
     * spec_image :
     * spec_value : 100mL
     */

    private String id;
    private String spec_group;
    private String spec_image;
    private String spec_value;

    public String getId() {
        Gson gson = new Gson();
        gson.fromJson("", GoodsTemp3Bean.class);
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse("").getAsJsonObject();
        Iterator it = jsonObj.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Log.v("", entry.getKey()+"|"+entry.getValue());
        }


        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpec_group() {
        return spec_group;
    }

    public void setSpec_group(String spec_group) {
        this.spec_group = spec_group;
    }

    public String getSpec_image() {
        return spec_image;
    }

    public void setSpec_image(String spec_image) {
        this.spec_image = spec_image;
    }

    public String getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(String spec_value) {
        this.spec_value = spec_value;
    }
}
