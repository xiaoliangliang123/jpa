//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.boot.autoconfigure.orm.jpa;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

class DataSourceInitializedPublisher implements BeanPostProcessor {
    @Autowired
    private ApplicationContext applicationContext;
    private DataSource dataSource;
    private JpaProperties jpaProperties;
    private HibernateProperties hibernateProperties;
    private DataSourceInitializedPublisher.DataSourceSchemaCreatedPublisher schemaCreatedPublisher;

    DataSourceInitializedPublisher() {
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalContainerEntityManagerFactoryBean) {
            LocalContainerEntityManagerFactoryBean factory = (LocalContainerEntityManagerFactoryBean)bean;
            if (factory.getBootstrapExecutor() != null && factory.getJpaVendorAdapter() != null) {
                this.schemaCreatedPublisher = new DataSourceInitializedPublisher.DataSourceSchemaCreatedPublisher(factory);
                factory.setJpaVendorAdapter(this.schemaCreatedPublisher);
            }
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            this.dataSource = (DataSource)bean;
        }

        if (bean instanceof JpaProperties) {
            this.jpaProperties = (JpaProperties)bean;
        }

        if (bean instanceof HibernateProperties) {
            this.hibernateProperties = (HibernateProperties)bean;
            if(!cacheDatasourceDdlAuto()){
                System.exit(0);
            }
        }

        if (bean instanceof LocalContainerEntityManagerFactoryBean && this.schemaCreatedPublisher == null) {

            LocalContainerEntityManagerFactoryBean factoryBean = (LocalContainerEntityManagerFactoryBean)bean;
            EntityManagerFactory entityManagerFactory = factoryBean.getNativeEntityManagerFactory();
            this.publishEventIfRequired(factoryBean, entityManagerFactory);
        }

        return bean;
    }

    private boolean cacheDatasourceDdlAuto() {
        try {

            Environment environment = this.applicationContext.getEnvironment();
            String driverClass = environment.getProperty("spring.datasource.driverClassName");
            String ddlAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");

            if(!StringUtils.isEmpty(driverClass)&&driverClass.contains("hsqldb")){
                return true;
            }else {
                if(!ddlAuto.equals("none")){
                    return false;
                }else {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void publishEventIfRequired(LocalContainerEntityManagerFactoryBean factoryBean, EntityManagerFactory entityManagerFactory) {
        DataSource dataSource = this.findDataSource(factoryBean, entityManagerFactory);
        if (dataSource != null && this.isInitializingDatabase(dataSource)) {
            this.applicationContext.publishEvent(new DataSourceSchemaCreatedEvent(dataSource));
        }

    }

    private DataSource findDataSource(LocalContainerEntityManagerFactoryBean factoryBean, EntityManagerFactory entityManagerFactory) {
        Object dataSource = entityManagerFactory.getProperties().get("javax.persistence.nonJtaDataSource");
        if (dataSource == null) {
            dataSource = factoryBean.getPersistenceUnitInfo().getNonJtaDataSource();
        }

        return dataSource instanceof DataSource ? (DataSource)dataSource : this.dataSource;
    }

    private boolean isInitializingDatabase(DataSource dataSource) {
        if (this.jpaProperties != null && this.hibernateProperties != null) {
            Supplier<String> defaultDdlAuto = () -> {
                return EmbeddedDatabaseConnection.isEmbedded(dataSource) ? "create-drop" : "none";
            };
            Map<String, Object> hibernate = this.hibernateProperties.determineHibernateProperties(this.jpaProperties.getProperties(), (new HibernateSettings()).ddlAuto(defaultDdlAuto));
            return hibernate.containsKey("hibernate.hbm2ddl.auto");
        } else {
            return true;
        }
    }

    final class DataSourceSchemaCreatedPublisher implements JpaVendorAdapter {
        private final LocalContainerEntityManagerFactoryBean factoryBean;
        private final JpaVendorAdapter delegate;

        private DataSourceSchemaCreatedPublisher(LocalContainerEntityManagerFactoryBean factoryBean) {
            this.factoryBean = factoryBean;
            this.delegate = factoryBean.getJpaVendorAdapter();
        }

        public PersistenceProvider getPersistenceProvider() {
            return this.delegate.getPersistenceProvider();
        }

        public String getPersistenceProviderRootPackage() {
            return this.delegate.getPersistenceProviderRootPackage();
        }

        public Map<String, ?> getJpaPropertyMap(PersistenceUnitInfo pui) {
            return this.delegate.getJpaPropertyMap(pui);
        }

        public Map<String, ?> getJpaPropertyMap() {
            return this.delegate.getJpaPropertyMap();
        }

        public JpaDialect getJpaDialect() {
            return this.delegate.getJpaDialect();
        }

        public Class<? extends EntityManagerFactory> getEntityManagerFactoryInterface() {
            return this.delegate.getEntityManagerFactoryInterface();
        }

        public Class<? extends EntityManager> getEntityManagerInterface() {
            return this.delegate.getEntityManagerInterface();
        }

        public void postProcessEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
            this.delegate.postProcessEntityManagerFactory(entityManagerFactory);
            AsyncTaskExecutor bootstrapExecutor = this.factoryBean.getBootstrapExecutor();
            if (bootstrapExecutor != null) {
                bootstrapExecutor.execute(() -> {
                    DataSourceInitializedPublisher.this.publishEventIfRequired(this.factoryBean, entityManagerFactory);
                });
            }

        }
    }

    static class Registrar implements ImportBeanDefinitionRegistrar {
        private static final String BEAN_NAME = "dataSourceInitializedPublisher";

        Registrar() {
        }

        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            if (!registry.containsBeanDefinition("dataSourceInitializedPublisher")) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(DataSourceInitializedPublisher.class);
                beanDefinition.setRole(2);
                beanDefinition.setSynthetic(true);
                registry.registerBeanDefinition("dataSourceInitializedPublisher", beanDefinition);
            }

        }
    }
}
