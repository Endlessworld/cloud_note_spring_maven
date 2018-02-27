package com.endless.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SmartDataSource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.DefaultTransactionDefinition;
  
 
@EnableAutoConfiguration 
@Configuration
@Import({WebConfig.class})
public class RootConfig {
//	 @Bean(name = "OracleDataSource")
//	 public DriverManagerDataSource DataSource(){
//		 System.out.println("配置Oracle数据源");
//		 DriverManagerDataSource DataSource = new DriverManagerDataSource();
//		 DataSource.setUsername("root");
//		 DataSource.setPassword("root");
//		 DataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//		 DataSource.setUrl("dbc:oracle:thin:@192.168.1.250:1521:cloud_note");
//		 return DataSource;
//	 }
	
	 @Bean(name = "MySqlDataSource")
	 public  DataSource MySqlDataSource() {
		 System.out.println("配置MySql数据源");
		 
	/*	 SimpleDriverDataSource SimpleDriverDataSource = new SimpleDriverDataSource();
		 SimpleDriverDataSource.setPassword("root");
		 SimpleDriverDataSource.setUsername("root");
		 SimpleDriverDataSource.setUrl("jdbc:mysql://localhost:3306/cloud_note?useUnicode=true&characterEncoding=GBK");
		 SimpleDriverDataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		 return SimpleDriverDataSource;
		 
		 
			DriverManagerDataSource DataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/cloud_note",
 	   			"root", "root");
    		DataSource.setDriverClassName("com.mysql.jdbc.Driver");
    		return DataSource;*/
		 
		 DataSourceBuilder DataBuilder=  DataSourceBuilder.create();
		 DataBuilder.username("root");
		 DataBuilder.password("root");
		 DataBuilder.driverClassName("com.mysql.jdbc.Driver");
		 DataBuilder.url("jdbc:mysql://localhost:3306/cloud_note?useUnicode=true&characterEncoding=GBK");
	     return DataBuilder.build();
			
	 }
	 @Bean(name="transactionFactory")
	 public TransactionFactory TransactionFactory(){
		 System.out.println("配置transactionFactory");
		 TransactionFactory transactionFactory = new JdbcTransactionFactory();
		return transactionFactory;
	 }
	 @Bean()
	 public Transaction getTransaction(DataSource DataSource) {
		 System.out.println("配置Transaction");
		 Transaction transaction = TransactionFactory().newTransaction(DataSource, TransactionIsolationLevel.READ_COMMITTED , true);
		return transaction;
	 }
	 @Bean(name="configuration")
	 public org.apache.ibatis.session.Configuration getConfiguration(TransactionFactory transactionFactory ,DataSource DataSource) {
		 System.out.println("配置configuration");
		Environment environment = new Environment("development", transactionFactory, DataSource);
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
		//configuration.addMapper(UserMapper.class);
		configuration.addMappers("com.endless.dao");
		return configuration;
	 }
	 @Bean(name="SqlSession")
	 public DefaultSqlSession getDefaultSqlSession(org.apache.ibatis.session.Configuration config,Transaction transaction) {
		 System.out.println("配置SqlSession");
		  
		 Executor executor = config.newExecutor(transaction, ExecutorType.REUSE);  
		 DefaultSqlSession defaultSqlSession = new DefaultSqlSession(config, executor, true);
		// return defaultSqlSession==null?(DefaultSqlSession) new SqlSessionFactoryBuilder().build(config).openSession(ExecutorType.REUSE, true):defaultSqlSession;
		return defaultSqlSession;
	 }
     @Bean(name="sqlSessionFactory")
     public SqlSessionFactory getSqlSessionFactory(org.apache.ibatis.session.Configuration config) {
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
		return sqlSessionFactory;
     }
    //事务管理器  
    @Bean(name = "txManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(MySqlDataSource());
        dataSourceTransactionManager.setRollbackOnCommitFailure(true); 
        return dataSourceTransactionManager; 
    }
    @Bean
    @Scope("prototype")
    public TransactionStatus  getTransactionStatus(){
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = getDataSourceTransactionManager().getTransaction(defaultTransactionDefinition);
		return status;
    }

    //事务拦截器
    @Bean(name="transactionInterceptor")
    public TransactionInterceptor interceptor(){
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(getDataSourceTransactionManager());
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }
/*  
   @Bean
   	public SqlSessionFactory getSqlSessionFactory() {
   		System.out.println("配置数据源");
    		DriverManagerDataSource DataSource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/cloud_note",
    			"root", "root");
    		DataSource.setDriverClassName("com.mysql.jdbc.Driver");
   		System.out.println("加载数据库驱动");
   		TransactionFactory transactionFactory = new JdbcTransactionFactory();
   		Environment environment = new Environment("development", transactionFactory, DataSource);
   		org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration(environment);
   		System.out.println("配置sqlSessionFactory");
   		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
   		sqlSessionFactory.getConfiguration().addMappers("com.endless.dao");
   		return sqlSessionFactory;
   	}*/
//  public void testtxManager() {
//	DataSourceTransactionManager tx = getDataSourceTransactionManager();
//	TransactionStatus status = getTransactionStatus();
//    try {
//       userMapper.insertUser(user);
//    }catch (Exception ex) {
//    	tx.rollback(status);
//      throw ex;
//    }
//      tx.commit(status);
//}
   /* 3.##########
    4.# HSQLDB #
    5.##########
    6.
    7.#jdbc.driverClassName=org.hsqldb.jdbcDriver
    8.#jdbc.url=jdbc:hsqldb:hsql://localhost:9001/bookstore 
    9.#jdbc.username=sa
    10.#jdbc.password=
    11.
    12.###########
    13.# MySQL 5 #
    14.###########
    15.
    16.jdbc.driverClassName=com.mysql.jdbc.Driver
    17.jdbc.url=jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=GBK 
    18.
    19.jdbc.username=root
    20.jdbc.password=root
    21.
    22.
    23.##############
    24.# PostgreSQL #
    25.##############
    26.
    27.#jdbc.driverClassName=org.postgresql.Driver
    28.#jdbc.url=jdbc:postgresql://localhost/bookstore 
    29.#jdbc.username=
    30.#jdbc.password=
    31.
    32.##########
    33.# Oracle #
    34.##########
    35.
    36.#jdbc.driverClassName=oracle.jdbc.driver.OracleDriver
    37.#jdbc.url=jdbc:oracle:thin:@192.168.1.250:1521:devdb
    38.#jdbc.username=HFOSPSP
    39.#jdbc.password=HFOSPSP
    40.
    41.#############################
    42.# MS SQL Server 2000 (JTDS) #
    43.#############################
    44.
    45.#jdbc.driverClassName=net.sourceforge.jtds.jdbc.Driver
    46.#jdbc.url=jdbc:jtds:sqlserver://localhost:1433/bookstore 
    47.#jdbc.username=
    48.#jdbc.password=
    49.
    50.##################################
    51.# MS SQL Server 2000 (Microsoft) #
    52.##################################
    53.
    54.#jdbc.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
    55.#jdbc.url=jdbc:sqlserver://192.168.1.130:1433;database=ahos;user=sa;password=ahtec"; 
    56.#jdbc.username=sa
    57.#jdbc.password=ahtec
    58.
    59.########
    60.# ODBC #
    61.########
    62.
    63.#jdbc.driverClassName=sun.jdbc.odbc.JdbcOdbcDriver
    64.#jdbc.url=jdbc:odbc:bookstore
    65.#jdbc.username=
    66.#jdbc.password=

    */
 
}