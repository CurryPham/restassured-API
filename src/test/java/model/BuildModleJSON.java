package model;

import com.google.gson.Gson;

public class BuildModleJSON {

    public static String parseJsonString(PostBody postBody){
        if(postBody == null){
            throw new IllegalArgumentException("PostBody can be null");
        }
        return new Gson().toJson(postBody);
    }
}
