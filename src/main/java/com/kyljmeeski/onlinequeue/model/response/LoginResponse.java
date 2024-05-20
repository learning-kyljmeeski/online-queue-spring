package com.kyljmeeski.onlinequeue.model.response;

public record LoginResponse(String username, String token, long tokenExpiration) {
}
