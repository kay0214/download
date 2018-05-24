package com.sandman.download.configuration.dbconfig;

import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by sunpeikai on 2018/5/24.
 */
@Configuration
// 指定dao的地址，指定sqlSessionFactory的名称
@MapperScan(basePackages = "com.sandman.download.dao.mysql.system", sqlSessionFactoryRef = "mysqlSqlSessionSystemFactory")
public class MysqlDataDaoSystemConfig {
    @Bean(name = "mysqlDataSystemSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql.system") // application.properteis中对应属性的前缀
    public DataSource mysqlDataSystemSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "mysqlSqlSessionSystemFactory")
    public SqlSessionFactory mysqlSqlSessionSystemFactory() throws Exception {
        /*@Qualifier("mysqlDataSystemSource") DataSource mysqlDataSystemSource*/
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mysqlDataSystemSource());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/system/*.xml"));
        return factoryBean.getObject();
    }
    @Bean(name = "sqlSessionSystemTemplate")
    public SqlSessionTemplate sqlSessionSystemTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(mysqlSqlSessionSystemFactory()); // 使用上面配置的Factory
        return template;
    }
}
