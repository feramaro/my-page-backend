package dev.feramaro.mysiteapi.services;

import dev.feramaro.mysiteapi.dto.post.CreatePostDTO;
import dev.feramaro.mysiteapi.dto.post.PostDTO;
import dev.feramaro.mysiteapi.dto.post.PostsDTO;
import dev.feramaro.mysiteapi.models.Post;
import dev.feramaro.mysiteapi.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO newPost(CreatePostDTO createPostDTO) {
        Post post = new Post();
        post.setTitle(createPostDTO.title());
        post.setText(createPostDTO.text());
        post.setImage(createPostDTO.image());
        post = postRepository.save(post);

        return new PostDTO(post.getId(), post.getTitle(), post.getText(), post.getImage());

    }

    public PostsDTO getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOS = posts.stream().map((post)
                        -> new PostDTO(post.getId(), post.getTitle(), post.getText(), post.getImage()))
                .toList();
        return new PostsDTO(postDTOS);
    }

    public PostDTO updatePost(String postId, CreatePostDTO postDTO) {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow();
        post.setTitle(postDTO.title());
        post.setText(postDTO.text());
        post.setImage(postDTO.image());
        post = postRepository.save(post);
        return new PostDTO(post.getId(), post.getTitle(), post.getText(), post.getImage());
    }

    public void deletePost(String postId) {
        postRepository.deleteById(UUID.fromString(postId));
    }


}
