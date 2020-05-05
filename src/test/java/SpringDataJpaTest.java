import jpa.dao.ResumeDao;
import jpa.pojo.Resume;
import org.hibernate.boot.spi.MetadataImplementor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.config.*;
import org.springframework.data.repository.config.RepositoryBeanDefinitionParser;
import org.springframework.data.repository.config.RepositoryConfiguration;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.jpa.repository.query.*;
import org.hibernate.metamodel.internal.*;
import org.hibernate.mapping.*;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * @author chris
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-data-jpa.xml"})
@Ignore
public class SpringDataJpaTest {
    
    
    // 要测试IOC哪个对象注入即可
    @Autowired
    private ResumeDao resumeDao;
    
    
    /**
     * dao层接口调用，分成两块：
     * 1、基础的增删改查
     * 2、专门针对查询的详细分析使用
     */
    
    /**
     * <jpa:repositories/>注解对应自定义解析器
     * {@link JpaRepositoryNameSpaceHandler}
     * {@link RepositoryBeanDefinitionParser}
     * <p>
     * {@link JpaRepositoryConfigExtension}Repository配置扩展类，
     * 包括配置{@link JpaRepositoryFactoryBean},对每个BeanDefinition配置
     * transactionManagerRef，和entityManagerFactoryRef
     * <p>
     * 对于每个Repository，主要build为{@link RepositoryBeanDefinitionBuilder#build(RepositoryConfiguration)}
     * 给每个Repository注入transactionManagerRef,entityManagerFactoryRef
     * {@link JpaRepositoryConfigExtension#postProcess(BeanDefinitionBuilder, RepositoryConfigurationSource)}
     * <p>
     * <p>
     * {@link RepositoryFactorySupport#getRepository}
     * {@link JpaRepositoryFactory#getTargetRepository}
     * {@link JpaRepositoryFactory#getRepositoryBaseClass}
     * 生成Repository逻辑
     *
     * 这些创建过程在容器bean实例化时创建
     * 接口方法代理实现类{@link SimpleJpaRepository}
     * 接口方法{@link JpaQueryMethod}
     *
     * 此过程在执行方法invoke时执行
     * sql{@link SimpleJpaQuery#doCreateQuery}
     * sql{@link NativeJpaQuery#doCreateQuery}
     *
     * sql执行{@link JpaQueryExecution}
     * {@link JpaQueryExecution.CollectionExecution#doExecute(AbstractJpaQuery, JpaParametersParameterAccessor)}
     *
     * 实体类解析{@link PersistentClass}
     *
     * 实体类在LocalContainerEntityManagerFactoryBean.afterPropertiesSet中
     * SessionFactoryImpl实例化时实例化
     * 实体类创建{@link MetamodelImpl#initialize(MetadataImplementor, JpaMetaModelPopulationSetting)}
     * {@link MetamodelImpl.entityPersisterMap}
     */
    @Test
    public void testFindById() {
        // 早期的版本 dao.findOne(id);

        /*
            select resume0_.id as id1_0_0_,
                resume0_.address as address2_0_0_, resume0_.name as name3_0_0_,
                 resume0_.phone as phone4_0_0_ from tb_resume resume0_ where resume0_.id=?
         */
        
        Optional<Resume> optional = resumeDao.findById(4L);
        Resume resume = optional.get();
        System.out.println("testFindById:" + resume);
    }
    
    
    @Test
    public void testFindOne() {
        Resume resume = new Resume();
        resume.setId(4L);
        resume.setName("chris");
        Example<Resume> example = Example.of(resume);
        Optional<Resume> one = resumeDao.findOne(example);
        Resume resume1 = one.get();
        System.out.println("testFindOne:" + resume1);
    }
    
    
    @Test
    public void testSave() {
        // 新增和更新都使用save方法，通过传入的对象的主键有无来区分，没有主键信息那就是新增，有主键信息就是更新
        Resume resume = new Resume();
        resume.setId(5L);
        resume.setName("赵六六");
        resume.setAddress("成都");
        resume.setPhone("132000000");
        Resume save = resumeDao.save(resume);
        System.out.println("testSave:" + save);
        
    }
    
    
    @Test
    public void testDelete() {
        resumeDao.deleteById(10L);
    }
    
    
    @Test
    public void testFindAll() {
        List<Resume> list = resumeDao.findAll();
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println("testFindAll:" + resume);
        }
    }
    
    
    @Test
    public void testSort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Resume> list = resumeDao.findAll(sort);
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println("testSort:" + resume);
        }
    }
    
    
    @Test
    public void testPage() {
        /**
         * 第一个参数：当前查询的页数，从0开始
         * 第二个参数：每页查询的数量
         */
        Pageable pageable = PageRequest.of(0, 2);
        //Pageable pageable = new PageRequest(0,2);
        Page<Resume> all = resumeDao.findAll(pageable);
        System.out.println("testPage:" + all);
        /*for (int i = 0; i < list.size(); i++) {
            Resume resume =  list.get(i);
            System.out.println(resume);
        }*/
    }
    
    
    /**
     * ========================针对查询的使用进行分析=======================
     * 方式一：调用继承的接口中的方法  findOne(),findById()
     * 方式二：可以引入jpql（jpa查询语言）语句进行查询 (=====>>>> jpql 语句类似于sql，只不过sql操作的是数据表和字段，jpql操作的是对象和属性，比如 from Resume where id=xx)  hql
     * 方式三：可以引入原生的sql语句
     * 方式四：可以在接口中自定义方法，而且不必引入jpql或者sql语句，这种方式叫做方法命名规则查询，也就是说定义的接口方法名是按照一定规则形成的，那么框架就能够理解我们的意图
     * 方式五：动态查询
     * service层传入dao层的条件不确定，把service拿到条件封装成一个对象传递给Dao层，这个对象就叫做Specification（对条件的一个封装）
     * <p>
     * <p>
     * // 根据条件查询单个对象
     * Optional<T> findOne(@Nullable Specification<T> var1);
     * // 根据条件查询所有
     * List<T> findAll(@Nullable Specification<T> var1);
     * // 根据条件查询并进行分页
     * Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
     * // 根据条件查询并进行排序
     * List<T> findAll(@Nullable Specification<T> var1, Sort var2);
     * // 根据条件统计
     * long count(@Nullable Specification<T> var1);
     * <p>
     * interface Specification<T>
     * toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);用来封装查询条件的
     * Root:根属性（查询所需要的任何属性都可以从根对象中获取）
     * CriteriaQuery 自定义查询方式 用不上
     * CriteriaBuilder 查询构造器，封装了很多的查询条件（like = 等）
     */
    
    
    @Test
    public void testJpql() {
        List<Resume> list = resumeDao.findByJpql(4L, "chris");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println("testJpql:" + resume);
        }
    }
    
    
    @Test
    public void testSql() {
        List<Resume> list = resumeDao.findBySql("李%", "上海%");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println("testSql:" + resume);
        }
    }
    
    
    @Test
    public void testMethodName() {
        List<Resume> list = resumeDao.findByNameLikeAndAddress("李%", "上海");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println("testMethodName:" + resume);
        }
        
    }
    
    
    // 动态查询，查询单个对象
    @Test
    public void testSpecfication() {
        
        /**
         * 动态条件封装
         * 匿名内部类
         *
         * toPredicate：动态组装查询条件
         *
         *      借助于两个参数完成条件拼装，，， select * from tb_resume where name='张三'
         *      Root: 获取需要查询的对象属性
         *      CriteriaBuilder：构建查询条件，内部封装了很多查询条件（模糊查询，精准查询）
         *
         *      需求：根据name（指定为"张三"）查询简历
         */
        
        Specification<Resume> specification = (Specification<Resume>) (root, criteriaQuery, criteriaBuilder) -> {
            // 获取到name属性
            Path<Object> name = root.get("name");
            
            // 使用CriteriaBuilder针对name属性构建条件（精准查询）
            Predicate predicate = criteriaBuilder.equal(name, "张三");
            return predicate;
        };
        
        
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
        
    }
    
    
    @Test
    public void testSpecficationMultiCon() {
        
        /**
         *      需求：根据name（指定为"张三"）并且，address 以"北"开头（模糊匹配），查询简历
         */
        
        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获取到name属性
                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");
                // 条件1：使用CriteriaBuilder针对name属性构建条件（精准查询）
                Predicate predicate1 = criteriaBuilder.equal(name, "张三");
                // 条件2：address 以"北"开头（模糊匹配）
                Predicate predicate2 = criteriaBuilder.like(address.as(String.class), "北%");
                
                // 组合两个条件
                Predicate and = criteriaBuilder.and(predicate1, predicate2);
                
                return and;
            }
        };
        
        
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }
    
    
}
