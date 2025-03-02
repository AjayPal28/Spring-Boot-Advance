package com.ajay.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.ajay.dto.PostDTO;
import com.ajay.entities.PostEntity;
import com.ajay.exception.ResourceNotFoundException;
import com.ajay.repositories.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<PostDTO> getAllPosts() {
		return postRepository.findAll().stream().map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDTO createNewPost(PostDTO inputPost) {
		PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
		return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
	}

	@Override
	public PostDTO getPostById(Long postId) {
		PostEntity postEntity = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));
		return modelMapper.map(postEntity, PostDTO.class);
	}

	@Override
	public PostDTO update(PostDTO inputPost) {
		postRepository.findById(inputPost.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found By ID"));
		return modelMapper.map(postRepository.save(modelMapper.map(inputPost, PostEntity.class)), PostDTO.class);
	}

	@Override
	public void delete(long postId) {
		postRepository.findById(postId)
		.orElseThrow(() -> new ResourceNotFoundException("Post Not Found By ID"));
		
		postRepository.deleteById(postId);
		
	}

	@Override
	public PostDTO partialUpdate(long id, Map<String, Object> dto) {
		PostEntity entity = postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post Not Found By ID"));
		dto.forEach((key, value) -> {
			Field field = ReflectionUtils.findRequiredField(PostEntity.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, entity, value);
		});
		return modelMapper.map(postRepository.save(entity), PostDTO.class);
	}
}
