package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liaocx on 2017/12/16.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
