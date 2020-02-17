package com.example.demo.helpers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.example.demo.constants.TypesConstants.EMPTY;
import static com.example.demo.constants.TypesConstants.JPG;

public class PictureFormat {

    public static boolean isPictureJPG(MultipartFile front_picture) {
        return Objects.equals(FilenameUtils.getExtension(front_picture.getOriginalFilename()), JPG);
    }
}
