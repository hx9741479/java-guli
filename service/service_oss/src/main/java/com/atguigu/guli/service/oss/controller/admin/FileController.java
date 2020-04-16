package com.atguigu.guli.service.oss.controller.admin;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Api(tags = {"讲师管理"})
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(@ApiParam(value = "模块", required = true)
                    @RequestParam("model") String model,
                    @ApiParam(value = "文件", required = true)
                    @RequestParam("file")MultipartFile file) {

        String uploadUrl = null;
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            uploadUrl = fileService.upload(inputStream, model, originalFilename);
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        return R.ok().message("上传成功").data("url", uploadUrl);
    }


}
