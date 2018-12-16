package com.example.subhamdivakar.alice.Listener;

import org.json.JSONObject;

public interface AsyncListener<T> {

    public T onPreDownload();

    public void onPostDownload(JSONObject result);

    public void onCancel();

}
