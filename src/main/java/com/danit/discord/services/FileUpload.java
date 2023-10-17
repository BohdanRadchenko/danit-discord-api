package com.danit.discord.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileUpload {
    private final Cloudinary cloudinary;

    public String uploadAvatarImage(MultipartFile multipartFile, String imageName) throws IOException {
        Map params = ObjectUtils.asMap(
                "public_id", String.format("discord/avatars/%s", imageName),
                "overwrite", true,
                "notification_url", "https://mysite.com/notify_endpoint",
                "resource_type", "image"
        );

        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), params)
                .get("url")
                .toString();
    }
}
