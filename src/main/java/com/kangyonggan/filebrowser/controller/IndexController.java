package com.kangyonggan.filebrowser.controller;

import com.kangyonggan.filebrowser.constants.AppConstants;
import com.kangyonggan.filebrowser.util.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kyg
 */
@RestController
@RequestMapping("/")
public class IndexController {

    /**
     * 文件列表
     *
     * @param dir
     * @return
     */
    @GetMapping
    public Map<String, Object> list(@RequestParam(required = false, defaultValue = "") String dir) {
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("respCo", "0000");
        resultMap.put("rootPath", AppConstants.ROOT_PATH);
        try {
            resultMap.put("files", FileUtil.files(AppConstants.ROOT_PATH + dir));
        } catch (Exception e) {
            resultMap.put("respCo", "9999");
            resultMap.put("respMsg", e.getMessage());
        }

        return resultMap;
    }

    /**
     * 文件上传
     *
     * @param dir
     * @param file
     * @return
     */
    @PostMapping("upload")
    public Map<String, Object> upload(@RequestParam String dir, @RequestParam("file") MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("respCo", "0000");
        try {
            FileUtil.upload(file, AppConstants.ROOT_PATH + dir);
        } catch (Exception e) {
            resultMap.put("respCo", "9999");
            resultMap.put("respMsg", e.getMessage());
        }

        return resultMap;
    }

    /**
     * 创建文件夹
     *
     * @param parentDir
     * @param dir
     * @return
     */
    @PostMapping("mkdir")
    public Map<String, Object> mkdir(@RequestParam String parentDir, @RequestParam String dir) {
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("respCo", "0000");
        try {
            FileUtil.mkdir(AppConstants.ROOT_PATH + parentDir, dir);
        } catch (Exception e) {
            resultMap.put("respCo", "9999");
            resultMap.put("respMsg", e.getMessage());
        }

        return resultMap;
    }

    /**
     * 删除文件/文件夹
     *
     * @param dir
     * @param fileName
     * @return
     */
    @DeleteMapping("removeFile")
    public Map<String, Object> removeFile(@RequestParam String dir, @RequestParam String fileName) {
        Map<String, Object> resultMap = new HashMap<>(4);
        resultMap.put("respCo", "0000");
        try {
            FileUtil.removeFile(AppConstants.ROOT_PATH + dir + fileName);
        } catch (Exception e) {
            resultMap.put("respCo", "9999");
            resultMap.put("respMsg", e.getMessage());
        }

        return resultMap;
    }

}