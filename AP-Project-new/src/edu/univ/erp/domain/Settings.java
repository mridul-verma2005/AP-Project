package edu.univ.erp.domain;

public class Settings {
    private String setting_key;
    private int value;

    public Settings(String setting_key, int value) {
        this.setting_key = setting_key;
        this.value = value;
    }

    public String getSetting_key() {
        return setting_key;
    }

    public void setSetting_key(String setting_key) {
        this.setting_key = setting_key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
