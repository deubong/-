package com.donguibogam.donguibogam;

import java.io.Serializable;

public class Drug_Item implements Serializable
{
    private String item_code;
    private String item_name;
    private String entp_name;
    private String class_no;
    private String otc;
    private String bar_code;
    private String material_name;
    private String capacity;
    private String precaution;
    private String insert_file;
    private String storage_method;
    private String valid_term;
    private String pack_unit;
    private String chart;

    public Drug_Item(String item_code, String item_name, String entp_name, String class_no, String otc,
                        String bar_code, String material_name, String capacity, String precaution, String insert_file,
                        String storage_method, String valid_term, String pack_unit, String chart)
    {
        this.item_code = item_code;
        this.item_name = item_name;
        this.entp_name = entp_name;
        this.class_no = class_no;
        this.otc = otc;
        this.bar_code = bar_code;
        this.material_name = material_name;
        this.capacity = capacity;
        this.precaution = precaution;
        this.insert_file = insert_file;
        this.storage_method = storage_method;
        this.valid_term = valid_term;
        this.pack_unit = pack_unit;
        this.chart = chart;
    }

    public String getItem_code() {
        return item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getEntp_name() {
        return entp_name;
    }

    public String getClass_no() {
        return class_no;
    }

    public String getOtc() {
        return otc;
    }

    public String getBar_code() {
        return bar_code;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getPrecaution() {
        return precaution;
    }

    public String getInsert_file() {
        return insert_file;
    }

    public String getStorage_method() {
        return storage_method;
    }

    public String getValid_term() {
        return valid_term;
    }

    public String getPack_unit() {
        return pack_unit;
    }

    public String getChart() {
        return chart;
    }
}