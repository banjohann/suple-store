package com.banjohann.lojasuplementos.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Utils {

    public static String getErrorMessage(ResponseBody errorBody) {
        try {
            JSONObject json = new JSONObject(errorBody.string());
            if (json.has("message")) {
                return json.getString("message");
            }
        } catch (JSONException | IOException ignored) { }

        return "Erro desconhecido";
    }
}
