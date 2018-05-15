package com.sandman.download.service;

import com.github.pagehelper.PageHelper;
import com.sandman.download.dao.mysql.ResourceDao;
import com.sandman.download.entity.*;
import com.sandman.download.security.SecurityUtils;
import com.sandman.download.utils.FileUtils;
import com.sandman.download.utils.PageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangj on 2018/5/14.
 */
@Service
public class ResourceService {
    private final Logger log = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UploadRecordService uploadRecordService;
    @Autowired
    private GoldRecordService goldRecordService;
    @Autowired
    private DownloadRecordService downloadRecordService;
    @Autowired
    private ResourceDao resourceDao;

    /**
     * 上传资源
     * */
    public BaseDto uploadRes(Resource resource, MultipartFile file){//上传不用修改USER表，所以直接拿过来登录信息就可以了
        if(file.isEmpty()){
            return new BaseDto(401,"上传文件为空!");
        }
        if(file.getSize()>(220*1024*1024)){
            return new BaseDto(412,"单个文件最大220MB!");
        }
        if(resource.getResGold()==null)
            return new BaseDto(420,"资源积分必填!");
        if(resource.getResDesc().length()<10)
            return new BaseDto(421,"资源描述必须10个字符以上");
        //开始做用户资源记录
        Long userId = SecurityUtils.getCurrentUserId();//登录的时候保存的信息，不用再次查询数据库

        resource.setUserId(userId);//设置UserId给resource
        resource.setUserName(SecurityUtils.getCurrentUserName());//设置资源所属用户
        resource.setNickName(SecurityUtils.getCurrentNickName());//设置资源所属用户的昵称

        String fileType = FileUtils.getSuffixNameByFileName(file.getOriginalFilename());
        fileType = (fileType==null || "".equals(fileType))?"file":fileType;//如果utils给出的文件类型为null，将file赋值给fileType
        String filePath = SftpParam.getPathPrefix() + "/" + fileType + "/" + userId + "/";//  /var/www/html/spkIMG + / + rar + / + userId + /

        String resName = resource.getResName();
        log.info("用户传入的resName:{}",resName);
        resName = (resName==null || "".equals(resName) || "null".equals(resName))?FileUtils.getPrefixByFileName(file.getOriginalFilename()):resName;
        log.info("如果用户没有传入resName，默认取文件的名字:{}",resName);
        resource.setResName(resName);//如果用户设置的resName为空，将原文件名赋值给resName

        //应该放在resName做好值处理后再进行一下判断，否则报空指针
        if(resourceExist(resource.getResName(),file))
            return new BaseDto(410,"请勿重复上传");

        String fileName = ("file".equals(fileType))?resName:(resName + "." + fileType);

        log.info("fileName={}",fileName);

        resource.setResUrl(filePath + fileName);//设置文件url为 服务器路径+文件类型+userId+fileName
        log.info("resUrl={}",resource.getResUrl());

        //resourceDTO.setResGold(0);//用户填写的 下载资源所需积分
        //resourceDTO.setResDesc("");//用户填写的资源描述

        resource.setResSize(file.getSize());//获取文件大小，存的时候不做操作，取得时候四舍五入带单位

        resource.setResType(fileType);//资源类型,rar
        resource.setDownloadCount(0);//设置默认下载数为0，因为是刚上传
        resource.setCreateBy(resource.getUserId());//用户创建，userId
        resource.setCreateTime(ZonedDateTime.now());
        resource.setUpdateBy(resource.getUserId());
        resource.setUpdateTime(ZonedDateTime.now());
        resource.setDelFlag(0);//1：已删除，0:正常

        resourceDao.createResource(resource);//保存到数据库

        //开始做用户上传日志记录
        UploadRecord uploadRecord = new UploadRecord();
        uploadRecord.setResId(resource.getId());//资源id
        uploadRecord.setUserId(userId);//用户id
        uploadRecord.setCreateBy(1L);//系统记录
        uploadRecord.setCreateTime(ZonedDateTime.now());
        uploadRecord.setUpdateBy(1L);
        uploadRecord.setUpdateTime(ZonedDateTime.now());
        uploadRecord.setDelFlag(0);

        uploadRecord.setId(uploadRecordService.createUploadRecord(uploadRecord));//得到保存后的数据的id
        log.info(uploadRecord.toString());//打印日志，看看带不带id

        //开始将文件上传到远程服务器
        File tempFile = FileUtils.getFileByMultipartFile(file);//MultiPartFile转File
        boolean uploadSuccess = FileUtils.upload(filePath,fileName,tempFile);//上传服务器
        tempFile.delete();
        if(!uploadSuccess){//如果上传远程服务器失败
            resourceDao.updateById(resource);//删除资源数据
            uploadRecordService.deleteById(uploadRecord.getId());//删除日志记录
            return new BaseDto(402,"上传远程服务器失败!");
        }
        return new BaseDto(200,"上传成功!",resource);
    }
    /**
     * 根据resName和fileName判断用户正在上传的资源是否已经存在在该用户下
     * */
    private boolean resourceExist(String resName,MultipartFile file){

        List<Resource> resourceList = resourceDao.findByUserId(SecurityUtils.getCurrentUserId());
        for(Resource resource:resourceList){
            if(resName.equals(resource.getResName())){
                log.info("上传的文件已经存在:{}",resource.toString());
                return true;
            }
            String existFileName = FileUtils.getCompleteFileNameByUrl(resource.getResUrl());
            if(existFileName.equals(file.getOriginalFilename())
                    && resource.getResSize().equals(file.getSize())) {
                log.info("文件名和文件大小都一样啊:{}",existFileName);
                return true;
            }
        }
        return false;
    }

    /**
     * 下载资源
     * */
    public BaseDto downloadRes(Long resId,HttpServletResponse response)throws Exception{//下载需要修改USER表，需要再次去数据库查询
        Resource resource = resourceDao.findById(resId);
        if(resource==null){
            return new BaseDto(408,"资源不存在!");
        }

        String resName = FileUtils.getFileNameByUrl(resource.getResUrl());//根据url获取到文件名前缀，不带扩展名
        String fileName = ("file".equals(resource.getResType()))?resName:(resName + "." + resource.getResType());
        log.info("resource:{}",resource.toString());
        User resOwner = userService.findOne(resource.getUserId());//根据userId查询出资源拥有者

        User curUser = userService.findUserByUserName(SecurityUtils.getCurrentUserName());//根据用户名查询出当前登录的用户

        if(!curUser.getId().equals(resOwner.getId())){//当前登录用户与资源拥有者不是同一人
            log.info("上传下载不同人");
            //判断用户积分是否足够
            int curUserGold = curUser.getGold();//当前用户积分
            int resGold = resource.getResGold();//资源积分
            if(curUserGold<resGold)
                return new BaseDto(403,"积分不足!");


            //下载者写入积分详情
            GoldRecord curUserRecord = goldRecordService.reduceGold(curUser,resource,0,"下载资源，积分扣除");
            //资源拥有者写入积分详情
            GoldRecord ownerRecord = goldRecordService.addGold(resOwner,resource,0,"其他用户下载该资源，积分增加");

            //下载者写入下载记录
            DownloadRecord downloadRecord = downloadRecordService.createDownloadRecord(resource.getId());

            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=\"" + FileUtils.getRightFileNameUseCode(fileName) + "\"");// 设置文件名
            boolean success = FileUtils.download(FileUtils.getFilePathByUrl(resource.getResUrl()),fileName,response);
            if(success){//如果下载成功
                log.info("上传下载不同人，下载成功");
                //资源下载次数++
                log.info("id:{}的资源下载次数+1。原下载次数:{}",resource.getId(),resource.getDownloadCount());
                resource.setDownloadCount(resource.getDownloadCount()+1);
                log.info("现下载次数:{}",resource.getDownloadCount());
                resourceDao.updateById(resource);//更新数据库
                //用户积分操作: 下载者扣除积分，上传者加上积分
                log.info("curUserGold={},resGold={},ownerGold={}",curUserGold,resGold,resOwner.getGold());
                curUser.setGold(curUserGold - resGold);//如果积分足够，扣除相应积分
                int ownerGold = resOwner.getGold();
                resOwner.setGold(ownerGold + resGold);//资源拥有者加上相应积分
                userService.updateUser(curUser);
                userService.updateUser(resOwner);

            }else{//下载失败，两个用户信息还没有保存，所以只需要删除日志记录和积分记录即可
                log.info("上传下载不同人,下载失败");
                goldRecordService.deleteById(curUserRecord.getId());//删除下载者积分记录
                goldRecordService.deleteById(ownerRecord.getId());//删除资源拥有者积分记录
                downloadRecordService.deleteById(downloadRecord.getId());//删除下载记录
                return new BaseDto(404,"下载出错!");
            }
        }else{//下载与登录为同一个人
            log.info("上传下载同一人");
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=\"" + FileUtils.getRightFileNameUseCode(fileName) + "\"");// 设置文件名
            boolean success = FileUtils.download(FileUtils.getFilePathByUrl(resource.getResUrl()),fileName,response);
            if(!success){
                return new BaseDto(404,"下载出错!");
            }
        }
        return new BaseDto(200,"下载已完成!");
    }
    /**
     * 更新资源信息
     * */
    public BaseDto updateResource(Resource resource){//更新一个resource
        log.info("update a resource:{}",resource.getId());
        Resource oriResource = resourceDao.findById(resource.getId());
        Long resOwnerId = oriResource.getUserId();
        if(!resOwnerId.equals(SecurityUtils.getCurrentUserId())){
            return new BaseDto(405,"无权修改!");
        }

        String resName = resource.getResName();
        if(resName!=null && !"".equals(resName)){
            oriResource.setResName(resName);
        }
        String resDesc = resource.getResDesc();
        if(resDesc!=null && !"".equals(resDesc)){
            oriResource.setResDesc(resDesc);
        }
        oriResource.setResGold(resource.getResGold());

        resourceDao.updateById(oriResource);

        //ResourceDTO resource = getOneResource(resourceDTO.getId());

        return new BaseDto(200,"更新成功!");
    }

    /**
     * 根据id获取资源列表（分页）
     */
    @Transactional(readOnly = true)
    public Map getAllMyResources(Integer pageNumber, Integer size, Long userId,String order,String sortType) {
        log.debug("getAllMyResources pageNumber:{},size:{}",pageNumber,size);
        userId = (userId==null)?SecurityUtils.getCurrentUserId():userId;
        if(userId==null)
            return null;
        pageNumber = (pageNumber==null || pageNumber<1)?1:pageNumber;
        size = (size==null || size<0)?10:size;
        if(order==null || "".equals(order)){//默认按照createTime降序排序
            order = "createTime";//字段
            sortType = "desc";
        }
        String orderBy = order + " " + sortType;

        PageHelper.startPage(pageNumber,size).setOrderBy(orderBy);

        List<Resource> resources = resourceDao.findByUserId(userId);//查询出列表（已经分页）
        Integer totalRow = resourceDao.getCountByUserId(userId);//查询出数据条数
        PageBean<Resource> pageBean = new PageBean<>(pageNumber,size,totalRow);//这里是为了计算页数，页码

        pageBean.setItems(resources);
        List<Resource> result = pageBean.getItems();


        Map data = new HashMap();//最终返回的map

        data.put("totalRow",totalRow);
        data.put("totalPage",pageBean.getTotalPage());
        data.put("currentPage",pageBean.getCurrentPage());//默认0就是第一页
        data.put("resourceList",result);
        return data;
    }
    /**
     * 资源大小：存入数据库的时候统一以byte为单位，取出来给前端的时候要做规范 -> 转换成以 B,KB,MB,GB为单位
     * */
/*    public List getFileSizeHaveUnit(List<Resource> resourceList){
        resourceList.forEach(resource -> {//这里必须捕获异常，否则如果size一样的话，会抛出异常
            try{
                String size = resource.getResSize();
                resource.setResSize(FileUtils.getFileSize(Long.parseLong(size)));
            }catch(Exception e){
                log.info("size相同，抛出Numberformat异常!");
                String size = resource.getResSize();
                resource.setResSize(size);
            }
        });
        return resourceList;
    }*/

    /**
     * 根据id查询资源信息
     */
    @Transactional(readOnly = true)
    public Resource getOneResource(Long id) {
        log.debug("Request to get Resource : {}", id);
        Resource resource = resourceDao.findById(id);
        if(resource==null)
            return null;
        return resource;
    }

    /**
     * 模糊检索资源
     * */
    @Transactional(readOnly = true)
    public Map getManyResourcesByFuzzy(Integer pageNumber,Integer size,String searchContent){//检索这里以后可能需要用到es

        pageNumber = (pageNumber==null || pageNumber<1)?1:pageNumber;
        size = (size==null || size<0)?10:size;

        String orderBy = "id desc";//默认按照id降序排序

        Integer totalRow = resourceDao.findManyResourcesByFuzzy(searchContent).size();//查询出数据条数
        log.info("totalRow:::::{}",totalRow);
        PageHelper.startPage(pageNumber,size).setOrderBy(orderBy);

        List<Resource> resources = resourceDao.findManyResourcesByFuzzy(searchContent);//查询出列表（已经分页）

        PageBean<Resource> pageBean = new PageBean<>(pageNumber,size,totalRow);//这里是为了计算页数，页码

        pageBean.setItems(resources);
        List<Resource> result = pageBean.getItems();


        Map data = new HashMap();//最终返回的map

        data.put("totalRow",totalRow);
        data.put("totalPage",pageBean.getTotalPage());
        data.put("currentPage",pageBean.getCurrentPage());//默认0就是第一页
        data.put("resourceList",result);
        return data;
    }

    /**
     * 删除资源（假删）
     */
    public BaseDto delResource(Long id) {
        log.debug("Request to delete Resource : {}", id);
        Resource tempRes = resourceDao.findById(id);
        if(tempRes==null){
            return new BaseDto(408,"资源不存在!");
        }
        if(!tempRes.getUserId().equals(SecurityUtils.getCurrentUserId())){
            return new BaseDto(406,"无权删除!");
        }

        Resource resource = new Resource();
        resource.setId(id);
        resource.setUpdateTime(ZonedDateTime.now());
        resource.setDelFlag(1);

        resourceDao.updateById(resource);
        return new BaseDto(200,"删除成功!");
    }
}
