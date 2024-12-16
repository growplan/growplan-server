package com.growplan.image.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.growplan.common.exception.ImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.growplan.common.code.ExceptionCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(final MultipartFile multipartFile, final String dirName) {
        try {
            final File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new ImageException(FILE_CONVERSION_FAILED));
            return upload(uploadFile, dirName);
        } catch (IOException e) {
            throw new ImageException(FILE_CONVERSION_IO_EXCEPTION);
        }
    }

    private String upload(final File uploadFile, final String dirName) {
        final String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        final String uploadImageUrl = putS3(uploadFile, fileName);
        deleteFile(uploadFile);
        return uploadImageUrl;
    }

    private Optional<File> convert(final MultipartFile file) throws IOException {
        final File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String putS3(final File uploadFile, final String fileName) {
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (AmazonS3Exception e) {
            throw new ImageException(S3_UPLOAD_FAILED);
        }
    }

    private void deleteFile(final File targetFile) {
        try {
            targetFile.delete();
        } catch (AmazonServiceException e) {
            throw new ImageException(FILE_DELETE_FAILED);
        }
    }

    public void deleteFileFromS3(final String fileUrl) {
        try {
            final String splitStr = bucket + "/";
            final String fileName = fileUrl.substring(fileUrl.lastIndexOf(splitStr) + splitStr.length());
            final String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            log.info(decodedFileName);
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, decodedFileName));
        } catch (AmazonServiceException e) {
            throw new ImageException(FILE_DELETE_FAILED);
        }
    }

    public void checkCountOfImage(final List<MultipartFile> files) {
        if (files != null && files.size() > 5) {
            throw new ImageException(EXCEEDED_MAX_IMAGE_UPLOAD);
        }
    }
}
