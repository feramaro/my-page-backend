package dev.feramaro.mysiteapi.dto.user;

public record TokenDTO(String type, String token, long exp) {
}
