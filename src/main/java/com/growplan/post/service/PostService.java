package com.growplan.post.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.image.domain.Image;
import com.growplan.image.domain.repository.ImageRepository;
import com.growplan.image.service.ImageService;
import com.growplan.post.domain.*;
import com.growplan.post.domain.repository.PostRepository;
import com.growplan.post.domain.repository.PostTagRepository;
import com.growplan.post.domain.repository.TagTypeRepository;
import com.growplan.post.dto.request.PostRequest;
import com.growplan.record.domain.repository.TagRepository;
import com.growplan.survey.domain.DevelopmentType;
import com.growplan.survey.domain.repository.DevelopmentTypeRepository;
import com.growplan.user.domain.User;
import com.growplan.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final DevelopmentTypeRepository developmentTypeRepository;
    private final PostTagRepository postTagRepository;
    private final TagTypeRepository tagTypeRepository;
    private final TagRepository tagRepository;

    public void savePost(final Long userId, final PostRequest postRequest, final List<MultipartFile> files) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));

        imageService.checkCountOfImage(files);

        final Post post = new Post(
                postRequest.getTitle(),
                postRequest.getContent(),
                postRequest.getMonths(),
                user
        );

        final Post savedPost = postRepository.save(post);

        savePostImages(savedPost, files);

        savePostDevTags(savedPost, postRequest.getDevelopmentTypes());
        savePostTags(savedPost, postRequest.getTags());
    }

    private void savePostImages(final Post post, final List<MultipartFile> files) {
        if (files != null) {
            final List<String> imageUrls = uploadImages(files);
            final List<Image> images = imageUrls.stream()
                    .map(imageUrl -> new PostImage(imageUrl, post))
                    .collect(Collectors.toList());
            imageRepository.saveAll(images);
        }
    }

    private List<String> uploadImages(final List<MultipartFile> files) {
        return files.stream()
                .map(file -> imageService.upload(file, "post"))
                .collect(Collectors.toList());
    }

    private void savePostDevTags(final Post post, final List<String> selectedTypes) {
        List<DevelopmentType> developmentTypes = developmentTypeRepository.findByTypeIn(selectedTypes);
        List<PostDevTag> postDevTags = new ArrayList<>();

        for (DevelopmentType developmentType : developmentTypes) {
            postDevTags.add(new PostDevTag(developmentType, post));
        }

        tagRepository.saveAll(postDevTags);
    }

    private void savePostTags(final Post post, final List<String> selectedTypes) {
        List<TagType> TagTypes = tagTypeRepository.findByTypeIn(selectedTypes);
        List<PostTag> postTags = new ArrayList<>();

        for (TagType tagType : TagTypes) {
            postTags.add(new PostTag(tagType, post));
        }

        postTagRepository.saveAll(postTags);
    }
}
