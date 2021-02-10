package ltd.newbee.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class UploadController {
    private final static String FILE_UPLOAD_PATH = "E:\\_dev\\IdeaProjects\\upload\\";

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败";
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("fileName:" + fileName);
        System.out.println("suffixName:" + suffixName);
        //生成文件名称通用方法
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()))
                .append(r.nextInt(100))
                .append(suffixName);
        String newFileName = tempName.toString();
        try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功，图片地址为：/upload/" + newFileName;
    }
}
