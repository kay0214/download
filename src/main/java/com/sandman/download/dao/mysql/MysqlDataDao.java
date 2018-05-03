package com.sandman.download.dao.mysql;

import java.util.HashMap;
import org.springframework.stereotype.Repository;

/**
 * Created by renhuiyu on 2018/5/3.
 * 方法接口，查询语句在xml中写
 */
@Repository
public interface MysqlDataDao
{
	// 插入或者更新
	public void replaceTestInfoById(HashMap<String, Object> id);
	// 查询
	public HashMap<String,Object> seletTestInfoById(int id);
}
