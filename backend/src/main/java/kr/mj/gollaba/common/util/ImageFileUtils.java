package kr.mj.gollaba.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class ImageFileUtils {

    private static final Set<String> imageContentTypes = Set.of(
            "image/png",
            "image/jpeg",
            "image/gif",
            "image/webp");

    public static boolean isImageFile(MultipartFile multipartFile) {
        return imageContentTypes.contains(multipartFile.getContentType());
    }
}

/*
Content-Type: image/png
Content-Type: image/jpeg
Content-Type: image/gif
Content-Type: image/webp
 */