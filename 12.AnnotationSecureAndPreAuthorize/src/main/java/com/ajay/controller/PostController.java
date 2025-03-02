package com.ajay.controller;



import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN','USER','CREATER') AND hasAuthority('VIEW')")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','CREATER') AND hasAuthority('VIEW')")
    public PostDTO getPostById(@PathVariable("postId") long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','CREATER') AND hasAuthority('CREATE')")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }
    
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','CREATER') AND hasAuthority('UPDATE')")
    public PostDTO updateAll(@RequestBody PostDTO inputPost) {
        return postService.update(inputPost);
    }
    
    @PatchMapping
    @PreAuthorize("hasAnyRole('ADMIN','CREATER') AND hasAuthority('UPDATE')")
    public PostDTO partialUpdate(@PathVariable (name = "id") long id,@RequestBody Map<String,Object> dto) {
        return postService.partialUpdate(id,dto);
    }
    
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable("postId") long postId) {
      postService.delete(postId);
    }

}
