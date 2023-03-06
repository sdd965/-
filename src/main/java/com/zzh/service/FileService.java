package com.zzh.service;

import com.zzh.vo.baseVo.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    R<String> uploadFile(MultipartFile file);

    void downLoadFile(String name, HttpServletResponse httpServletResponse) throws IOException;
}
