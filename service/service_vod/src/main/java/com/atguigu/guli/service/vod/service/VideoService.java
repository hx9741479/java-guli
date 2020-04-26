package com.atguigu.guli.service.vod.service;

import java.io.InputStream;
import java.util.List;

public interface VideoService {
    String uploadVideo(InputStream file, String originalFilename);

    void removeVideo(String videoId) throws Exception;

    void removeVideoByIdList(List<String> videoIdList) throws Exception;
}