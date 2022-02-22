package pl.raportsa.memelibrary.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class FileUtils {

    private final static String SLASH = "/";
    private final static String CONSTANT_PATH = "/src/main/resources/static/img/";

    public static String saveImage(String userName, MultipartFile imgUrl) {
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        String name = DateUtils.formatDate(new Date()) + "_" + imgUrl.getOriginalFilename();
        String fullPath = absolutePath + CONSTANT_PATH + userName;
        new File(fullPath).mkdirs();
        String destPath = (fullPath + SLASH + name);
        try {
            imgUrl.transferTo(Paths.get(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }
}