package com.xinbo.chainblock.controller.api;

/**
 * @author tony
 * @date 8/25/22 9:06 下午
 * @desc file desc
 */


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.xinbo.chainblock.annotation.JwtIgnore;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController("ApiHutoolUpload")
@RequestMapping("/api/upload")
public class HutoolUpload {

    @Value("${upload.path}")
    private String uploadPath;


    /**
     * desc: 单文件上传
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @JwtIgnore
    @Operation(summary = "uploadOne", description = "单文件上传")
    @PostMapping("uploadOne")
    public Object uploadOne(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String yyyyMMdd = uploadPath + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + File.separator;
        if (!FileUtil.exist(yyyyMMdd)) {
            FileUtil.mkdir(yyyyMMdd);
        }
        String fileName = UUID.randomUUID().toString() + "@" + multipartFile.getOriginalFilename();
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        File file1 = FileUtil.writeBytes(multipartFile.getBytes(), yyyyMMdd + fileName);
        List< Map<String, String>> pathList = new ArrayList<>();
        if (file1.length()>0){
            Map<String, String> map = new HashMap<>();
            map.put("fileName", fileName);
            map.put("suffix", suffix);
            map.put("path", yyyyMMdd.replaceAll("\\\\","/"));
            pathList.add(map);
        }
        return pathList;
    }

    /**
     * desc: 多文件上传
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @JwtIgnore
    @Operation(summary = "uploadMany", description = "多文件上传")
    @PostMapping("uploadMany")
    public Object uploadMany(@RequestParam(value = "file") MultipartFile[] file) throws IOException {
        String yyyyMMdd = uploadPath + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + File.separator;
        if (!FileUtil.exist(yyyyMMdd)) {
            FileUtil.mkdir(yyyyMMdd);
        }
        List< Map<String, String>> pathList = new ArrayList<>();
        if (file.length > 0) {
            for (MultipartFile multipartFile : file) {
                String fileName = UUID.randomUUID().toString() + "@" + multipartFile.getOriginalFilename();
                String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
                File file1 = FileUtil.writeBytes(multipartFile.getBytes(), yyyyMMdd + fileName);
                if (file1.length()>0){
                    Map<String, String> map = new HashMap<>();
                    map.put("fileName", fileName);
                    map.put("suffix", suffix);
                    map.put("path", yyyyMMdd.replaceAll("\\\\","/"));
                    pathList.add(map);
                }
            }
        }
        return pathList;
    }

    /**
     * desc: 图片显示
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @JwtIgnore
    @Operation(summary = "showImg", description = "图片显示")
    @PostMapping("showImg")
    public Object showImg(HttpServletResponse response,@RequestBody Map<String,Object> map) {
        if (map.isEmpty()){
            return "文件不能为空";
        }
        boolean suffix = checkPic(map.get("suffix").toString());
        if (!suffix){
            return "不是图片";
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtil.readBytes(map.get("path").toString()+map.get("fileName").toString()));
            IoUtil.close(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * desc: 文件下载
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    @JwtIgnore
    @Operation(summary = "download", description = "文件下载")
    @RequestMapping("download")
    public Object download(HttpServletResponse response,@RequestBody Map<String,Object> map) throws IOException {
        if (map.isEmpty()){
            return "文件不能为空";
        }
        String fileUrl = map.get("path").toString()+map.get("fileName").toString();
        String suffix = map.get("suffix").toString();
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/force-download");
        //        设置编码，避免文件名中文乱码
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(map.get("fileName").toString().getBytes("gb2312"), "ISO8859-1") + suffix);
        outputStream.write(FileUtil.readBytes(fileUrl));
        IoUtil.close(outputStream);
        return null;
    }


    /**
     * desc: 图片格式检验
     * param:
     * return:
     * author: CDN
     * date: 2019/11/17
     */
    private static boolean checkPic(String suffix){
        if (suffix.isEmpty()){
            return false;
        }
        String reg = "(.JPEG|.jpeg|.JPG|.jpg|.PNG|.png|.GIF|.gif|.BMP|.bmp)$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher("."+suffix);
        return matcher.find();
    }

}
