package dev.feramaro.mysiteapi.controllers;

import dev.feramaro.mysiteapi.dto.post.CreatePostDTO;
import dev.feramaro.mysiteapi.dto.post.PostDTO;
import dev.feramaro.mysiteapi.dto.post.PostsDTO;
import dev.feramaro.mysiteapi.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostsDTO> getAllPosts() {
        PostsDTO posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<PostDTO> newPost(@RequestBody CreatePostDTO createPostDTO) {
        PostDTO post = postService.newPost(createPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable("postId") String postId, @RequestBody CreatePostDTO createPostDTO) {
        PostDTO post = postService.updatePost(postId, createPostDTO);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") String postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
