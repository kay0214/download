package com.sandman.download.controller;

import com.sandman.download.dao.mysql.MysqlDataDao;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by renhuiyu on 2018/5/3.
 */
@RestController
@RequestMapping(value = "test")
public class TestController
{
	@Resource MysqlDataDao mysqlDataDao;

	@GetMapping(value = "testReplace")
	public void testInsert(int id)
	{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id",id);
		param.put("name","haha"+id);
		mysqlDataDao.replaceTestInfoById(param);
	}

	@GetMapping(value = "testSelect")
	public HashMap<String, Object> testSelect(int id)
	{
		return mysqlDataDao.seletTestInfoById(id);
	}


}
