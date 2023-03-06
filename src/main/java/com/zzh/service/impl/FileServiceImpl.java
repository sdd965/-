package com.zzh.service.impl;

import cn.hutool.http.server.HttpServerResponse;
import com.zzh.common.exception.BusinessException;
import com.zzh.service.FileService;
import com.zzh.vo.baseVo.R;
import org.omg.CORBA.portable.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${zzh.basePath}")
    String basePath;
    @Override
    public R<String> uploadFile(MultipartFile file) {
        File path = new File(basePath);
        if(!path.exists())
        {
            path.mkdirs();
        }
        //获取上传时文件的名称
        String fileName = file.getOriginalFilename();
        //获取文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //为了防止文件重名使用UUID生成一个随机ID
        String newFileName = UUID.randomUUID()+suffix;
        try {
            file.transferTo(new File(basePath+newFileName));
            return R.success(newFileName,"文件上传成功");
        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }

    }


    @Override
    public void downLoadFile(String name, HttpServletResponse response) throws IOException {
        File newFile = new File(basePath + name);
        FileInputStream inputStream = new FileInputStream(newFile);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.setContentType("img/jpeg");
        try {
            byte[] bytes = new byte[2048];
            int len;
            while((len = inputStream.read(bytes))!=-1)
            {
                servletOutputStream.write(bytes,0,len);
                servletOutputStream.flush();
            }
        } catch (Exception e)
        {
            throw new BusinessException("下载失败");
        }
        finally {
            servletOutputStream.close();
            inputStream.close();

        }

    }
}
