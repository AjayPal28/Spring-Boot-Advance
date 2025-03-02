package com.ajay.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.ajay.dto.PostDTO;
import com.ajay.services.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public PostDTO getPostById(@PathVariable("postId") long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }
    
    @PutMapping
    public PostDTO updateAll(@RequestBody PostDTO inputPost) {
        return postService.update(inputPost);
    }
    
    @PatchMapping
    public PostDTO partialUpdate(@PathVariable (name = "id") long id,@RequestBody Map<String,Object> dto) {
        return postService.partialUpdate(id,dto);
    }
    
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("postId") long postId) {
      postService.delete(postId);
    }

}
