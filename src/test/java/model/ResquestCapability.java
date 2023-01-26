package model;

import io.restassured.http.Header;

public interface ResquestCapability {
    Header defaultHeader = new Header("Content-Type", "application/json; charset=UTF-8");
}
