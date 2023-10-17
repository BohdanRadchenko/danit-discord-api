package com.danit.discord.utils;

import com.danit.discord.annotations.ValidAvatar;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageAvatarFileValidator implements ConstraintValidator<ValidAvatar, MultipartFile> {
    private static final long MAX_AVATAR_SIZE = 3000000L;
    private static final int MAX_AVATAR_WIDTH = 1024;
    private static final int MAX_AVATAR_HEIGHT = 1024;

    @Override
    public void initialize(ValidAvatar constraintAnnotation) {

    }

    @SneakyThrows
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        if (multipartFile == null) return true;

        boolean result = true;

        if (!isSupportedContentType(multipartFile)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Only PNG or JPG images are allowed.")
                    .addConstraintViolation();

            result = false;
        }

        if (!isSupportedSize(multipartFile)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("Max avatar size %s", MAX_AVATAR_SIZE))
                    .addConstraintViolation();

            result = false;
        }

        if (!isSupportedWidthAndHeight(multipartFile)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            String.format("Max avatar width:%s, height: %s", MAX_AVATAR_WIDTH, MAX_AVATAR_HEIGHT))
                    .addConstraintViolation();

            result = false;
        }

        return result;
    }

    private boolean isSupportedContentType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }

    private boolean isSupportedSize(MultipartFile multipartFile) {
        long size = multipartFile.getSize();
        return size <= MAX_AVATAR_SIZE;
    }

    private boolean isSupportedWidthAndHeight(MultipartFile multipartFile) throws IOException {
        BufferedImage bImg = ImageIO.read(multipartFile.getInputStream());
        int width = bImg.getWidth();
        int height = bImg.getHeight();
        return width <= MAX_AVATAR_WIDTH && height <= MAX_AVATAR_HEIGHT;

    }
}
