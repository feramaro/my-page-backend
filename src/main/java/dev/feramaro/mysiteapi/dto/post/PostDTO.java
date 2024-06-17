package dev.feramaro.mysiteapi.dto.post;

import java.util.UUID;

public record PostDTO(UUID id, String title, String text, String image) {
}
