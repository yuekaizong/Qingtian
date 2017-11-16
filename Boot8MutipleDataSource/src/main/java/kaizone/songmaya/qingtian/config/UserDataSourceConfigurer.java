package kaizone.songmaya.qingtian.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryUser",  //实体管理引用
        transactionManagerRef = "transactionManagerUser",
        basePackages = {"kaizone.songmaya.qingtian.user"})  //设置数据源所应用的包
public class UserDataSourceConfigurer {

    @Autowired
    @Qualifier("userDataSource")
    private DataSource userDataSource;

    //配置EntityManager实体
    @Bean(name = "entityManagerUser")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryUser(builder).getObject().createEntityManager();
    }

    //配置EntityManager工厂实体
    @Bean(name = "entityManagerFactoryUser")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryUser(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userDataSource)
                .properties(getVendorProperties(userDataSource))
                .packages(new String[]{"kaizone.songmaya.qingtian.user"})
                .persistenceUnit("userPersistenceUnit")
                .build();
    }

    @Autowired
    private JpaProperties jpaProperties;

    //获取ip配置信息
    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    //配置事务
    @Bean(name = "transactionManagerUser")
    public PlatformTransactionManager transactionManagerUser(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryUser(builder).getObject());
    }
}
