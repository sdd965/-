package com.zzh.controller;


import cn.hutool.http.server.HttpServerResponse;
import com.zzh.service.FileService;
import com.zzh.vo.baseVo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/common")
@RestController
public class FlieController {

    @Autowired
    private FileService fileService;
    @RequestMapping("/upload")
    @PostMapping
    public R<String> uploadFile(MultipartFile file)
    {
       return fileService.uploadFile(file);
        //file要和前端的name参数一致
        //file是一个临时文件，需要转存，一般放在C盘User下的temp目录下
    }

    @GetMapping("/download")
    public void  downloadFlie(String name, HttpServletResponse httpServletResponse) throws IOException {
        fileService.downLoadFile(name, httpServletResponse);
    }
}
