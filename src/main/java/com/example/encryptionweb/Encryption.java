package com.example.encryptionweb;

public class Encryption {
    private String text;
    private String key;

    public Encryption(String text, String key){
        this.setKey(key);
        this.setText(text);
    }

    public Encryption(){

    }

    public void setText(String text){
        this.text = text;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }
}
