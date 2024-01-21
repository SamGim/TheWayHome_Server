package com.thewayhome.project.utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OauthInfo {
    String provider;
    String userId;
}
