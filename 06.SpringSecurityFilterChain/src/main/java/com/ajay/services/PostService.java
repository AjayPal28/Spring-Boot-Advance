package com.ajay.services;



import java.util.List;

import com.ajay.dto.PostDTO;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
