package builder;

import com.google.gson.Gson;

public class BodyJSONBuilder {

    public static <T> String  getJSONContent(T dataOnbject){
        return new Gson().toJson(dataOnbject);
    }
}
