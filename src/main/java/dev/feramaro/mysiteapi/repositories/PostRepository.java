package dev.feramaro.mysiteapi.repositories;

import dev.feramaro.mysiteapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
