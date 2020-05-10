package com.atguigu.guli.service.oss.controller.admin;

import com.atguigu.guli.common.base.result.R;
import com.atguigu.guli.common.base.result.ResultCodeEnum;
import com.atguigu.guli.service.base.exception.GuliException;
import com.atguigu.guli.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Api(tags = {"讲师管理"})
//@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
@Slf4j
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public R upload(@ApiParam(value = "模块", required = true)
                    @RequestParam("module") String module,
                    @ApiParam(value = "文件", required = true)
                    @RequestParam("file") MultipartFile file) {
        String uploadUrl = null;
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            uploadUrl = fileService.upload(inputStream, module, originalFilename);
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }

        return R.ok().message("上传成功").data("url", uploadUrl);
    }

    @ApiOperation(value = "测试")
    @GetMapping("test")
    public R test() {
        log.info("............oss test被调用");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    @ApiOperation("文件删除")
    @DeleteMapping("remove")
    public R removeFile(@ApiParam(value = "需要删除的头像的url", required = true)
                        @RequestBody String url) {
        fileService.removeFile(url);
        return R.ok().message("文件删除成功");
    }


}
