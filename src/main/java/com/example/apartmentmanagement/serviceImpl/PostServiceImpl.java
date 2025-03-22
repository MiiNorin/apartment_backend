package com.example.apartmentmanagement.serviceImpl;

import com.example.apartmentmanagement.dto.ApartmentResponseDTO;
import com.example.apartmentmanagement.dto.PostResponseDTO;
import com.example.apartmentmanagement.dto.PostRequestDTO;
import com.example.apartmentmanagement.entities.Apartment;
import com.example.apartmentmanagement.entities.Post;
import com.example.apartmentmanagement.entities.PostImages;
import com.example.apartmentmanagement.entities.User;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.PostImagesRepository;
import com.example.apartmentmanagement.repository.PostRepository;
import com.example.apartmentmanagement.repository.UserRepository;
import com.example.apartmentmanagement.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostImagesRepository postImagesRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public List<PostResponseDTO> getPosts() {
        return postRepository.findAll().stream().map(post -> new PostResponseDTO(
                post.getPostId(),
                post.getUser().getUserId(),
                post.getTitle(),
                post.getContent(),
                post.isDepositCheck(),
                convertToApartmentDTO(post.getApartment()),
                post.getPrice(),
                post.getPostType(),
                post.getPostDate(),
                post.getUser().getUserName(),
                post.getPostImages().stream().map(PostImages::getPostImagesUrl).toList()
                ))
        .collect(Collectors.toList());
    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với ID: " + id));
        PostResponseDTO dto = new PostResponseDTO(
                post.getPostId(),
                post.getUser().getUserId(),
                post.getTitle(),
                post.getContent(),
                post.isDepositCheck(),
                convertToApartmentDTO(post.getApartment()),
                post.getPrice(),
                post.getPostType(),
                post.getPostDate(),
                post.getUser().getUserName(),
                post.getPostImages().stream().map(PostImages::getPostImagesUrl).toList()
        );
        return dto;
    }

    @Override
    public PostResponseDTO createPost(PostRequestDTO postDTO, List<MultipartFile> imageFiles) {
        Post post = new Post();

        System.out.println("apartment: " + postDTO.getApartmentName());

        if (postDTO == null) {
            throw new RuntimeException("Dữ liệu bài đăng không được để trống");
        }

        if (postDTO.getTitle() == null || postDTO.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Tiêu đề bài đăng không được để trống");
        }

        if (postDTO.getPostType() == null) {
            throw new RuntimeException("Loại bài đăng không được để trống");
        }

        if (postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            throw new RuntimeException("Nội dung bài đăng không được để trống");
        }

        if (postDTO.getUserName() == null || postDTO.getUserName().trim().isEmpty()) {
            throw new RuntimeException("Tên người dùng không được để trống");
        }

        Apartment apartment = apartmentRepository.findApartmentByApartmentName(postDTO.getApartmentName());

        post.setTitle(postDTO.getTitle());
        post.setPostType(postDTO.getPostType());
        post.setPrice(postDTO.getPrice());
        post.setPostDate(LocalDateTime.now());
        post.setDepositCheck(postDTO.isDepositCheck());
        post.setContent(postDTO.getContent());
        post.setApartment(apartment);

        User user = userRepository.findByUserNameOrEmail(postDTO.getUserName());
        if (user == null) {
            throw new RuntimeException("Không tìm thấy username này");
        }
        post.setUser(user);

        List<String> postImagesUrl = imageUploadService.uploadMultipleImages(imageFiles);
        List<PostImages> postImagesList = new ArrayList<>();

        for (String imageUrl : postImagesUrl) {
            PostImages postImages = new PostImages();
            postImages.setPostImagesUrl(imageUrl);
            postImages.setPost(post);
            postImagesList.add(postImages);
        }

        post.setPostImages(postImagesList);

        postRepository.save(post);
        postImagesRepository.saveAll(postImagesList);

        PostResponseDTO dto = new PostResponseDTO(
                post.getPostId(),
                user.getUserId(),
                post.getTitle(),
                post.getContent(),
                post.isDepositCheck(),
                convertToApartmentDTO(post.getApartment()),
                post.getPrice(),
                post.getPostType(),
                post.getPostDate(),
                post.getUser().getUserName(),
                post.getPostImages().stream().map(PostImages::getPostImagesUrl).toList()
        );
        return dto;
    }

    @Override
    public PostResponseDTO updatePost(Long id, PostRequestDTO postDTO, List<MultipartFile> imageFiles) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài đăng với ID: " + id));

        if (postDTO == null) {
            throw new RuntimeException("Dữ liệu bài đăng không được để trống");
        }
        if (postDTO.getTitle() == null || postDTO.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Tiêu đề bài đăng không được để trống");
        }
        if (postDTO.getPostType() == null) {
            throw new RuntimeException("Loại bài đăng không được để trống");
        }
        if (postDTO.getContent() == null || postDTO.getContent().trim().isEmpty()) {
            throw new RuntimeException("Nội dung bài đăng không được để trống");
        }
        if (postDTO.getUserName() == null || postDTO.getUserName().trim().isEmpty()) {
            throw new RuntimeException("Tên người dùng không được để trống");
        }

        post.setTitle(postDTO.getTitle());
        post.setPostType(postDTO.getPostType());
        post.setPostDate(LocalDateTime.now());
        post.setPrice(postDTO.getPrice());
        post.setDepositCheck(postDTO.isDepositCheck());
        post.setContent(postDTO.getContent());

        User user = userRepository.findByUserNameOrEmail(postDTO.getUserName());
        if (user == null) {
            throw new RuntimeException("Không tìm thấy username này");
        }
        post.setUser(user);

        if (imageFiles != null && !imageFiles.isEmpty()) {
            post.getPostImages().clear();

            List<String> postImagesUrl = imageUploadService.uploadMultipleImages(imageFiles);
            List<PostImages> postImagesList = postImagesUrl.stream().map(url -> {
                PostImages postImage = new PostImages();
                postImage.setPostImagesUrl(url);
                postImage.setPost(post);
                return postImage;
            }).toList();

            post.getPostImages().addAll(postImagesList);
        }

        postRepository.save(post);

        return new PostResponseDTO(
                post.getPostId(),
                user.getUserId(),
                post.getTitle(),
                post.getContent(),
                post.isDepositCheck(),
                convertToApartmentDTO(post.getApartment()),
                post.getPrice(),
                post.getPostType(),
                post.getPostDate(),
                post.getUser().getUserName(),
                post.getPostImages().stream().map(PostImages::getPostImagesUrl).toList()
        );
    }

    private ApartmentResponseDTO convertToApartmentDTO(Apartment apartment) {
        if (apartment == null) return null;
        return new ApartmentResponseDTO(
                apartment.getApartmentId(),
                apartment.getApartmentName(),
                apartment.getHouseholder(),
                apartment.getTotalNumber(),
                apartment.getStatus(),
                apartment.getAptImgUrl(),
                apartment.getNumberOfBedrooms(),
                apartment.getNumberOfBathrooms(),
                apartment.getNote(),
                apartment.getDirection(),
                apartment.getFloor(),
                apartment.getArea()
        );
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
