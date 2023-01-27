package model;

import io.restassured.http.Header;

import java.util.Objects;
import java.util.function.Function;

public interface RequestCapability {
    Header defaultHeader = new Header("Content-Type", "application/json; charset=UTF-8");

    static Header getAuthenticatedHeader(String encodedCredStr){
        if(encodedCredStr == null){
            throw new IllegalArgumentException("[ERR] encodedCredStr can't be null");
        }
        return new Header("Authorization", "Basic " + encodedCredStr);
    }

    Function<String, Header> getAuthenticatedHeader = encodedCredStr -> {
            if(encodedCredStr == null){
                throw new IllegalArgumentException("[ERR] encodedCredStr can't be null");
            }
            return new Header("Authorization", "Basic " + encodedCredStr);
    };
}
