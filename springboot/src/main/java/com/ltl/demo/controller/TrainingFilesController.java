package com.ltl.demo.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ltl.demo.common.constant.StatusConstant;
import com.ltl.demo.entity.TrainingFiles;
import com.ltl.demo.exception.MyException;
import com.ltl.demo.service.TrainingFilesService;
import com.ltl.demo.vo.Result;
import com.ltl.demo.vo.TrainingFilesPageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ltl
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/trainingFiles")
@Slf4j
public class TrainingFilesController {
    @Autowired
    private TrainingFilesService trainingFilesService;

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    /**
     * 上传接口
     * @param file
     * @return  上传成功后返回文件的下载链接
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        //获取源文件的名称
        String originalFilename = file.getOriginalFilename();
        //定义文件唯一标识
        String flag = IdUtil.fastSimpleUUID();
        //获取上传路径
        String rootFilePath = System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+flag+"_"+originalFilename;
        //hutool工具类，把file写入到rootFilePath
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        //把图片的下载地址返回
        return new Result<>("200",ip + ":" +port + "/trainingFiles/" + flag);

    }


    /**
     * 下载接口
     * @param response
     * @param flag
     */
    @GetMapping("/{flag}")
    public void download(HttpServletResponse response, @PathVariable String flag){
        //创建一个输出流对象
        OutputStream os;
        //定位文件上传的根路径
        String bashPath= System.getProperty("user.dir")+"/springboot/src/main/resources/files/";
        //获取所有的文件名称
        List<String> fileNames = FileUtil.listFileNames(bashPath);
        //根据传进来的flag（uuid）找到对应的文件
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");

        try {
            if (StringUtils.isNotEmpty(fileName)){
                response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                //通过文件路径读取文件字节流
                byte[] bytes = FileUtil.readBytes(bashPath + fileName);
                //通过输出流返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e){
            log.error("文件下载失败！");
        }
    }


    /**
     * 删除文件接口
     * @param deleteFiles
     */
    @PostMapping("/deleteFile")
    public void deleteFile(@RequestBody List<String> deleteFiles){
        for (String deleteFile : deleteFiles) {
            //通过文件路径删除
            String filePath = System.getProperty("user.dir")+"/springboot/src/main/resources/files/" + deleteFile;
            FileUtil.del(filePath);
        }
    }

    @GetMapping("/queryPage")
    public Result<IPage<TrainingFiles>> queryPage(TrainingFilesPageVo trainingFilesPageVo){
        IPage<TrainingFiles> page = trainingFilesService.queryPage(trainingFilesPageVo);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.QUERY_SUCCESS_MSG,page);
    }

    @PostMapping("/addOrUpdate")
    public Result<?> addOrUpdate(@Validated @RequestBody TrainingFiles trainingFiles) throws MyException {
        return trainingFilesService.addOrUpdate(trainingFiles);
    }

    @DeleteMapping("/deleteById")
    public Result<?> deleteById(int id){
        trainingFilesService.deleteById(id);
        return new Result<>(StatusConstant.SUCCESS_CODE,StatusConstant.DELETE_SUCCESS_MSG);
    }
}

