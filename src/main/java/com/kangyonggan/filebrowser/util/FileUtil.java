package com.kangyonggan.filebrowser.util;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author kyg
 */
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * 文件上传
     *
     * @param file
     * @param dir
     * @throws Exception
     */
    public static void upload(MultipartFile file, String dir) throws FileUploadException {
        if (file.getSize() != 0) {
            try {
                File fileDir = new File(dir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                file.transferTo(new File(dir + file.getOriginalFilename()));
            } catch (Exception e) {
                throw new FileUploadException("File Upload Exception", e);
            }
        }
    }

    /**
     * 获取目录
     *
     * @param fullName
     * @return
     */
    private static String getDir(String fullName) {
        return fullName.substring(0, fullName.lastIndexOf(File.separatorChar));
    }

    /**
     * 获取文件夹下的所有文件
     *
     * @param dirPath
     * @return
     */
    public static List<Map<String, Object>> files(String dirPath) {
        List<Map<String, Object>> list = new ArrayList<>();
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return list;
        }

        File[] files = dir.listFiles(pathname -> !pathname.isHidden());
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            File file = files[i];
            Map<String, Object> map = new HashMap<>(8);
            map.put("isDir", file.isDirectory());
            map.put("name", file.getName());
            map.put("size", file.length());
            map.put("time", file.lastModified());

            list.add(map);
        }

        return list;
    }

    /**
     * 创建文件夹
     *
     * @param parentDir
     * @param dir
     */
    public static void mkdir(String parentDir, String dir) {
        File file = new File(parentDir + dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @throws IOException
     */
    public static void removeFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File subFile : files) {
                    removeFile(subFile.getAbsolutePath());
                }
            }
        }
        if(!file.delete()) {
            Runtime.getRuntime().exec("rm -rf " + file.getAbsolutePath());
        }
    }
}
