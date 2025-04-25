package com.atriel.springsecurity.oauth2.user;

import java.util.Map;

public interface OAuth2UserInfo {
    String getId();
    String getName();
    String getEmail();
    String getPictureUrl();
    Map<String, Object> getAttributes();
}