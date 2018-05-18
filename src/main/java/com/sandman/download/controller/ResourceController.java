package com.sandman.download.controller;

import com.sandman.download.entity.BaseDto;
import com.sandman.download.entity.Resource;
import com.sandman.download.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by sunpeikai on 2018/5/14.
 */
@RestController
@RequestMapping("/api/sandman/v1/resource")
public class ResourceController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourceService resourceService;

    /**
     * 上传资源
     * */
    @PostMapping("/uploadResource")
    public BaseDto uploadResource(Resource resource, @RequestParam("file")MultipartFile file) throws IOException {
        //TODO:限制用户上传过大的文件
        log.info("用户上传资源:{}" + file.getOriginalFilename());
        return resourceService.uploadRes(resource,file);
    }
    /**
     * 下载资源
     * */
    @GetMapping("/downloadResource")
    public BaseDto downloadResource(Long id,HttpServletResponse response){
        log.info("用户下载资源id:{}",id);
        BaseDto baseDto = null;

        try {
            baseDto = resourceService.downloadRes(id, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseDto;
    }

    /**
     * 更新资源信息
     */
    @PostMapping("/updateResource")
    public BaseDto updateResource(Resource resource){
        log.info("resource:{}",resource.toString());
        return resourceService.updateResource(resource);
    }

    /**
     * 根据id获取资源列表（分页）
     */
    @GetMapping("/getAllMyResources")
    public BaseDto getAllMyResources(Integer pageNumber,Integer size,Long userId,String order,String sortType) {
        log.info("pageNumber：{}，size：{}，userId：{}",pageNumber, size, userId);
        Map data = resourceService.getAllMyResources(pageNumber, size, userId,order,sortType);
        if(data==null){
            return new BaseDto(410,"请先登录或者传入id");
        }
        return new BaseDto(200,"查询成功!",data);
    }

    /**
     * 根据资源id获取资源
     */
    @GetMapping("/getOneResource")
    public BaseDto getOneResource(Long id) {
        log.debug("REST request to get one Resource : {}", id);
        Resource resource = resourceService.getOneResource(id);
        if(resource!=null)
            return new BaseDto(200,"查询成功!",resource);
        return new BaseDto(408,"资源不存在!",resource);
    }
    /**
     * 按照名称检索资源，查resName、resUrl和resDesc
     * */
    @GetMapping("/getManyResourcesByFuzzy")
    public BaseDto getManyResourcesByFuzzy(Integer pageNumber,Integer size,String search){//检索资源
        Map data = resourceService.getManyResourcesByFuzzy(pageNumber, size, search);
        return new BaseDto(200,"检索成功!",data);
    }

    /**
     * 资源删除（假删）
     */
    @GetMapping("/delResource")
    public BaseDto delResource(Long id) {
        log.debug("REST request to delete Resource : {}", id);
        return resourceService.delResource(id);
    }

}
