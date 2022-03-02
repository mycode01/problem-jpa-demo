### JPA(hibernate) test

간단한 서비스입니다.  
현업에서 사용중인 persistance model 을 만들다보니,  
비즈니스 키로 사용되는 product_id 를 관련된 테이블에서 전부 공용으로 사용하고 싶다는 생각에  
product_id 로 join을 걸수 있게 하였고  
(순전히 운영업무를 위해서 비즈니스 키를 이용하고 싶습니다.)

이 중, product 와 detail 사이 @OneToOne 으로 관계가 맺어진 부분에서 select 시에   
가져온 detail 이 null로 리턴됨을 확인하였고,   
이유를 찾기 위해 로그를 보던 중 아래와 같은 로그를 발견하게 되었습니다.

![jpa log1](https://raw.githubusercontent.com/mycode01/linkimages/master/jpa/screenshot20220302.png)

풀 로그는 아래와 같습니다. 
```ruby
2022-03-02 14:46:11.775  INFO 8989 --- [    Test worker] c.e.demo.jpa.service.DefaultServiceTest  : Started DefaultServiceTest in 8.213 seconds (JVM running for 9.337)
2022-03-02 14:46:11.796  INFO 8989 --- [    Test worker] o.s.t.c.transaction.TransactionContext   : Began transaction (1) for test context [DefaultTestContext@7bf9b098 testClass = DefaultServiceTest, testInstance = com.example.demo.jpa.service.DefaultServiceTest@4c36250e, testMethod = testGetProduct@DefaultServiceTest, testException = [null], mergedContextConfiguration = [MergedContextConfiguration@389adf1d testClass = DefaultServiceTest, locations = '{}', classes = '{class com.example.demo.jpa.JpaApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.autoconfigure.OverrideAutoConfigurationContextCustomizerFactory$DisableAutoConfigurationContextCustomizer@63b1d4fa, org.springframework.boot.test.autoconfigure.actuate.metrics.MetricsExportContextCustomizerFactory$DisableMetricExportContextCustomizer@45efc20d, org.springframework.boot.test.autoconfigure.filter.TypeExcludeFiltersContextCustomizer@351584c0, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@654f77ce, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@502f1f4c, [ImportsContextCustomizer@77307458 key = [org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration, org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration, org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration, org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration, org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration, org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration, org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration, org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration, org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManagerAutoConfiguration]], org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@4bff1903, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@654d8173, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@0], contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map['org.springframework.test.context.event.ApplicationEventsTestExecutionListener.recordApplicationEvents' -> false]]; transaction manager [org.springframework.orm.jpa.JpaTransactionManager@416b681c]; rollback [true]
Hibernate: 
    /* select
        generatedAlias0 
    from
        Product as generatedAlias0 
    where
        generatedAlias0.productId=:param0 */ select
            product0_.no as no1_3_,
            product0_.user_no as user_no3_3_,
            product0_.product_id as product_2_3_ 
        from
            product product0_ 
        where
            product0_.product_id=?
Hibernate: 
    select
        user0_.no as no1_5_0_,
        user0_.name as name2_5_0_,
        products1_.user_no as user_no3_3_1_,
        products1_.no as no1_3_1_,
        products1_.no as no1_3_2_,
        products1_.user_no as user_no3_3_2_,
        products1_.product_id as product_2_3_2_,
        detail2_.no as no1_1_3_,
        detail2_.prod_desc as prod_des2_1_3_,
        detail2_.product_id as product_4_1_3_,
        detail2_.prod_title as prod_tit3_1_3_ 
    from
        user user0_ 
    left outer join
        product products1_ 
            on user0_.no=products1_.user_no 
    left outer join
        detail detail2_ 
            on products1_.no=detail2_.product_id 
    where
        user0_.no=?
```

테스트 클래스 DefaultServiceTest 를 Run 해보시면 위와 같은 로그를 확인하실수 있구요,  
로그를 따라가보자면,  
product_id로 product를 가져오기 위해 첫번째 쿼리가 실행이 되었고,  
@ManyToOne 으로 관계가 설정된 User를 읽어오는데(Eager),   
User 역시 (현재 설정으로는) Eager로 product를 가지고 있을수 있게 설정했기 때문에  
다시한번 쿼리를 하면서, @OneToOne 으로 엮인 detail을 함께 조회하는것으로 보입니다.

다만, detail이 product_id 를 가지고 있도록 하고, 양방향으로 관계를 설정하였고
(detail이 product_id를 가지는 1:1단방향은 지원하지 않기 때문에)  
어떤 컬럼으로 조회를 해야하는지 product나 user에서는 알수 없기 때문에  
기본키인 product.no를 가지고 쿼리를 한것으로 보입니다. 

물론 기본키를 이용해 1:1을 묶으면 해결되는 문제인것임을 알고 있으나,   
관리상 비즈니스 키를 이용해 관련 테이블을 묶는것이 편하기 때문에 이런 모양을 사용하였고,
detail만 기본키를 이용해 관계를 맺는것도 마음이 불편합니다.  
실제로는 이보다 많은 테이블이 있기 때문에 모두 기본키로 묶는것은 피하고 싶으며,  
현재 구조에서 product <> detail 관계를  
<b>product가 관계의 주인이 되며,  
detail이 product_id를 가지는 형태로 1:1을 엮는 방법이 있을까요?</b>

참고로 페치 모드나, eager<>lazy를 바꾸는 방법으로는 모두 실패했습니다. 