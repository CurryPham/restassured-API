package model;

import io.restassured.http.Header;

public interface RequestCapability {
    Header defaultHeader = new Header("Content-Type", "application/json; charset=UTF-8");
}
