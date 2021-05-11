/*
package doc;

public class TranscationDoc {

40 @Transactional
	1 编程式事物：代码入侵性强，在代码中手动提交，回滚操作
	2 声明式事物：基于aop,业务与事物部分解耦：两种方式：1 基于TX和aop的XML 2 @Transactional注解
	3 @Transactional应用域：接口，类，方法
		a:作用类：表示该类所有的public方法都配置了相同事物属性信息
		b:作用方法：当类配置了@Transactional，方法也配置了@Transactional,方法会覆盖类上的，方法只使用自己的配置
		c:作用接口：不推荐这种使用方法，因为一旦标注在Interface上并且配置了Spring AOP 使用CGLib动态代理，
		将会导致@Transactional注解失效
	4 属性
		propagation属性
		propagation 代表事务的传播行为，默认值为 Propagation.REQUIRED，其他的属性信息如下：
		Propagation.REQUIRED：如果当前存在事务，则加入该事务，如果当前不存在事务，则创建一个新的事务。
		( 也就是说如果A方法和B方法都添加了注解，在默认传播模式下，A方法内部调用B方法，会把两个方法的事务合并为一个事务 ）
		Propagation.SUPPORTS：如果当前存在事务，则加入该事务；如果当前不存在事务，则以非事务的方式继续运行。
		Propagation.MANDATORY：如果当前存在事务，则加入该事务；如果当前不存在事务，则抛出异常。
		Propagation.REQUIRES_NEW：重新创建一个新的事务，如果当前存在事务，暂停当前的事务。( 当类A中的 a
		方法用默认Propagation.REQUIRED模式，类B中的 b方法加上采用 Propagation.REQUIRES_NEW模式，
		然后在 a 方法中调用 b方法操作数据库，然而 a方法抛出异常后，b方法并没有进行回滚，因为Propagation.REQUIRES_NEW会暂停 a方法的事务 )
		Propagation.NOT_SUPPORTED：以非事务的方式运行，如果当前存在事务，暂停当前的事务。
		Propagation.NEVER：以非事务的方式运行，如果当前存在事务，则抛出异常。
		Propagation.NESTED ：和 Propagation.REQUIRED 效果一样。
		isolation 属性
		isolation ：事务的隔离级别，默认值为 Isolation.DEFAULT。
		Isolation.DEFAULT：使用底层数据库默认的隔离级别。
		Isolation.READ_UNCOMMITTED
		Isolation.READ_COMMITTED
		Isolation.REPEATABLE_READ
		Isolation.SERIALIZABLE
		timeout 属性
		timeout ：事务的超时时间，默认值为 -1。如果超过该时间限制但事务还没有完成，则自动回滚事务。
		readOnly 属性
		readOnly ：指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。
		rollbackFor 属性
		rollbackFor ：用于指定能够触发事务回滚的异常类型，可以指定多个异常类型。
		noRollbackFor属性**
		noRollbackFor：抛出指定的异常类型，不回滚事务，也可以指定多个异常类型。
	5 失效场景
		1 应用在非public方法上
		2 属性propagation配置错误：
			TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
			TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
			TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
		3 rollbackFor 设置错误
			1 可以指定能够触发事物回滚的异常类型
			2 spring默认抛出了unchecked(继承RuntimeException)或error 才回滚事物
			3 自定义异常的子类异常抛出同样会触发回滚
		4 同一个类中方法调用，导致事物失效：aop好像也会失效
				开发中避免不了会对同一个类里面的方法调用，比如有一个类Test，它的一个方法A，A再调用本类的方法B
				（不论方法B是用public还是private修饰），但方法A没有声明注解事务，而B方法有。则外部调用方法A之后，
				方法B的事务是不会起作用的。这也是经常犯错误的一个地方。

				那为啥会出现这种情况？其实这还是由于使用Spring AOP代理造成的，因为只有当事务方法被当前类以外的代码调用时，
				才会由Spring生成的代理对象来管理。

				aop代理对象生成：只有aop被当前类以外代码调用才会生成
		5 异常被catch吃掉了
			1 UnexpectedRollbackException：事物A应该提交，事物B应该回滚，前后不一致，抛出回滚异常
		6 数据库引擎不支持事物
			1 常用的MySQL数据库默认使用支持事务的innodb引擎。一旦数据库引擎切换成不支持事务的myisam，那事务就从根本上失效了。
	6 事物4大特性
		1 原子性：一次事物，是一个动作
			1 原子性只保证了一个事物内的所有操作同一性，大家同生死，不会出现你死了，我还活着。
			但是，原子性并没有保证大家同一时刻一起生，一起死
		2 一致性：一次事物里的几件事，要么一起提交，要么一起回滚：基于原子性，原子性保证了事物的一致性？
			理解错了,这就是原子性，而不是一致性，原子性，决定以了一个事物原子包里所有的动作，同生同死
			是从事物动作的角度去看
			1:一致性：是从数据的状态角度去看
			2 一致性 是基于事物的隔离性，只有事物隔离，事物才不会取到混乱的数据，保证所有事物使用的数据是一致的？错误
			3 一致性 虽然是从数据的角度去看，但不是基于隔离性，因为只是保证当前事物所有数据的一致性
			一个事物保持了1
		3 隔离性：事物与事物互不干扰，我在占着茅坑，别人就进不来
			1 隔离级别：从事物的角度看，一定是大于1个事物
				1 读未提交 read uncommited：A事物未提交，B读取了A事物提交后的结果：隔离级别最差，不可避免脏读，不可重复读，幻读
				2 读提交  read commmited：A事物提交，B事物才能读取结果：可以避免脏读，不可以重复度，不能避免幻读
				3 可重复读 repeatable read -innodb(默认级别)：A 事物可以多次读，B事物在修改数据，A读到的都是B事物修改前的数据
					只有当A事物结束，再次查询才能看到B事物提交的结果 可以避免脏读，不可重复读，不可避免幻读
				  幻读影响最小，大不了再修改一次
				4 序列化 ：	事物顺序进行，可以避免脏读，不可重复读，幻读，但是效率低，A 事物执行，其它事物必须等待
			2 隔离级别问题：
				1 脏读：现象：读到脏数据，不对的数据，原因：因为数据被改动了，回滚
				2 不可重复度：现象：第一次读是A，第二次读就变成了B，原因：数据被改动了，回滚
				3 幻读：现象：A修改所有数据，B 提交数据，A 修改完查询还有数据没有被修改，原因：数据被改动了，事物提交
			3 传播特性：多个事物方法互相调用时，事物如何在这些方法间传播
				1 REQUIRED(spring默认特性)：如果当前没有事务，则自己新建一个事务，如果当前存在事务，则加入这个事务
					A,B没有事物则新增，A，B存在事物则合并，合并成C?
				2 SUPPORTS: A,B(SUPPORTS): 当前存在事务，则加入当前事务，如果当前没有事务，就以非事务方法执行
					A有事物，B就用A的，A没有事物，B也不要事物，无条件支持A，谁调用B，B就跟随
				3 MANDATORY：A,B(MANDATORY):当前存在事务，则加入当前事务，如果当前事务不存在，则抛出异常。
					A有事物，B就用A的，A没有事物，B就抛出异常，有条件支持A，谁调用B，就得给B饭吃
				4 REQUIRES_NEW：创建一个新事务，如果存在当前事务，则挂起该事务
					B不管A有没有事物，都新建一个事物B，并且B只用自己B事务，A，B两事务互相隔离，互不相关
				5 NOT_SUPPORTED：始终以非事务方式执行,如果当前存在事务，则挂起当前事务
					A 有事务，B 不要，任何人都管不了B，B不和任何人有关系，只做自己的事，不受外界影响
				6 NEVER：不使用事务，如果当前事务存在，则抛出异常
					A 有事务，B 拒绝A的调用，我不用，你也不准用，你要用，我就不给你用，霸道总裁
				7 NESTED：如果当前事务存在，则在嵌套事务中执行，否则REQUIRED的操作一样（开启一个事务）
					A 有事务，B 新建一个事务B，嵌套在A事务里，A没有事务，B 新建一个事务
					1 A 是老大，老大出事，B小弟都要倒霉，
					2 A 是老大，老大做幕后，B 小弟出事被抓，直接被抛弃，影响不了A老大
					3 A 老大做自己的事，把B小弟派出去单干，A,B不再有从属关系
						B小弟出事，影响不了找不到A老大，A 老大出事，B小弟直接跑路，A也影响不了B

		4 持久性：基于日志：日志保证了事物的持久性，保存到数据库，从数据的存储角度去看
	4 持久性：基于日志：日志保证了事物的持久性，保存到数据库，从数据的存储角度去看
	7 事物使用场景：大于一次数据库操作：必须
		1 一个动作修改两张表以上的数据
		2 一个动作有两次以上保存数据的动作
		3 只要一个动作，大于一次数据库操作，就要考虑到事务
		4 必须用事务，不然数据不一致，就等着炸吧
	8 乐观锁和悲观锁
		1 乐观锁(不做管控处理，标记预警)：相对于悲观锁而言，认为不会对数据库数据一致性有影响
			在关系数据库管理系统里，乐观并发控制（又名”乐观锁”，Optimistic Concurrency Control，缩写”OCC”）
			是一种并发控制的方法。它假设多用户并发的事务在处理时不会彼此互相影响，各事务能够在不产生锁的情况下处理各自影响的
			那部分数据。在提交数据更新之前，每个事务会先检查在该事务读取数据后，有没有其他事务又修改了该数据。
			如果其他事务有更新的话，正在提交的事务会进行回 滚。乐观事务控制最早是由孔祥重（H.T.Kung）教授提出

			乐观锁不是数据库自带的，需要我们自己去实现。乐观锁是指操作数据库时(更新操作)，想法很乐观，认为这次的操作不会导致冲突
			，在操作数据时，并不进行任何其他的特殊处理（也就是不加锁），而在进行更新后，再去判断是否有冲突了。

			1. 读取：事务将数据读入缓存，这时系统会给事务分派一个时间戳。
			2. 校验：事务执行完毕后，进行提交。这时同步校验所有事务，如果事务所读取的数据在读取之后又被其他事务修改，则产生冲突，事务被中断回滚。
			3. 写入：通过校验阶段后，将更新的数据写入数据库。

			乐观并发控制多数用于数据争用不大、冲突较少的环境中，这种环境中，偶尔回滚事务的成本会低于读取数据时锁定数据的成本，
			因此可以获得比其他并发控制方法更高的吞吐量。
			实现：1 数据库版本version 2 时间戳 更新时间,更新是判断version或时间戳是否一致来保证数据一致性
			现象：如果发现数据不一致，则处理，也就是允许数据不一致，乐观的认为不会出现，即使出现就处理
			特定：并发高，不会发生死锁	，满足并发量，又不会产生锁，又可以回滚事务，保证数据一致性
			优点与不足
　　		乐观并发控制相信事务之间的数据竞争(data race)的概率是比较小的，因此尽可能直接做下去，直到提交的时候才去锁定，
			所以不会产生任何锁和死锁。但如果直接简单这么做，还是有可能会遇到不可预 期的结果，例如两个事务都读取了数据库的某一行，
			经过修改以后写回数据库，这时就遇到了问题。
		2 悲观锁(严格控制，必须处理)：认为一定会对数据库数据一致性有影响，必须严格控制，不能有一点意外，处理
			悲观锁是由数据库自己实现了的，要用的时候，我们直接调用数据库的相关语句就可以了。
			具有强烈的独占和排他特性
			在关系数据库管理系统里，悲观并发控制（又名”悲观锁”，Pessimistic Concurrency Control，缩写”PCC”）
			是一种并发控制的方法。它可以阻止一个事务以影响其他用户的方式来修改数据。如果一个事务执行的操作读某行数据应用了 锁，
			那只有当这个事务把锁释放，其他事务才能够执行与该锁冲突的操作。

			悲观并发控制主要用于数据争用激烈的环境，以及发生并发冲突时使用锁保护数据的成本要低于回滚事务的成本的环境中。
			1 它指的是对数据被外界（包括本系统当前的其他事务，以及来自外部系统的事务处理）修改持保守态度，
			因此，在整个数据处理过程中，将数据处于锁定状态。悲观锁的实现，往往依靠数据库提供的锁机制
			（也只有数据库层提供的锁机制才能真正保证数据访问的排他性，否则，即使在本系统中实现了加锁机制，
			也无法保证外部系统不会修改数据
			实现：SELECT 的读取锁的两种方式:
				1. SELECT ... LOCK IN SHARE MODE
				2. SELECT ... FOR UPDATE
			两种方式在事务(Transaction) 进行当中SELECT 到同一个数据表时，都必须等待其它事务数据被提交(Commit)后才会执行。
			而主要的不同在于LOCK IN SHARE MODE 在有一方事务要Update 同一个表单时很容易造成死锁 。
			简单的说，如果SELECT 后面若要UPDATE 同一个表单，最好使用SELECT ... UPDATE。

			MySQL SELECT ... FOR UPDATE 的Row Lock(行锁)与Table Lock(表锁).
			由于InnoDB 预设是Row-Level Lock，所以只有「明确」的指定主键，MySQL 才会执行Row lock (只锁住被选取的数据) ，否则MySQL 将会执行Table Lock (将整个数据表单给锁住).
			1.明确指定主键，并且有此数据，row lock
			2.明确指定主键，若查无此数据，无lock
			3.无主键 /主键不明确，table lock
			除了主键外，使用索引也会影响数据库的锁定级别
			1.明确指定索引，并且有此数据，row lock
			2.明确指定索引，若查无此数据，无lock

			总是假设最坏的情况，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，
			这样别人想拿这个数据就会阻塞直到它拿到锁（共享资源每次只给一个线程使用，其它线程阻塞，用完后再把资源转让给其它线程）。
			传统的关系型数据库里边就用到了很多这种锁机制，比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁

			Java中synchronized和ReentrantLock等独占锁就是悲观锁思想的实现。
			刚刚说了，对于悲观锁，一般数据库已经实现了，共享锁也属于悲观锁的一种，那么共享锁在mysql中是通过什么命令来调用呢。
			通过查询资料，了解到通过在执行语句后面加上lock in share mode就代表对某些资源加上共享锁了。
			加上共享锁后，也提示错误信息了，通过查询资料才知道，对于update,insert,delete语句会自动加排它锁的原因
			排它锁
			排它锁与共享锁相对应，就是指对于多个不同的事务，对同一个资源只能有一把锁。
			与共享锁类型，在需要执行的语句后面加上for update就可以了


			行锁
			行锁，由字面意思理解，就是给某一行加上锁，也就是一条记录加上锁。

			比如之前演示的共享锁语句

			SELECT * from city where id = "1"  lock in share mode;

			由于对于city表中,id字段为主键，就也相当于索引。执行加锁时，会将id这个索引为1的记录加上锁，那么这个锁就是行锁。



			表锁
			表锁，和行锁相对应，给这个表加上锁

			特点：并发小，依靠数据实现

			应用：乐观锁多用于读，悲观锁多用于写




    一口气说出 6种 @Transactional 注解失效场景
    漫话编程  2020-04-06
    以下文章来源于程序员内点事 ，作者程序员内点事




            引言



    昨天公众号粉丝咨询了一个问题，说自己之前面试被问@Transactional注解哪些场景下会失效，一时语塞致使面试失败。所以今天简单的和大家分享一下@Transactional相关的知识。

    @Transactional 注解相信大家并不陌生，平时开发中很常用的一个注解，它能保证方法内多个数据库操作要么同时成功、要么同时失败。使用@Transactional注解时需要注意许多的细节，不然你会发现@Transactional总是莫名其妙的就失效了。


    一、事务


    事务管理在系统开发中是不可缺少的一部分，Spring提供了很好事务管理机制，主要分为编程式事务和声明式事务两种。

    编程式事务：是指在代码中手动的管理事务的提交、回滚等操作，代码侵入性比较强，如下示例：

            1try {
        2    //TODO something
        3     transactionManager.commit(status);
        4} catch (Exception e) {
        5    transactionManager.rollback(status);
        6    throw new InvoiceApplyException("异常失败");
        7}
    声明式事务：基于AOP面向切面的，它将具体业务与事务处理部分解耦，代码侵入性很低，所以在实际开发中声明式事务用的比较多。声明式事务也有两种实现方式，一是基于TX和AOP的xml配置文件方式，二种就是基于@Transactional注解了。

            1    @Transactional
2    @GetMapping("/test")
3    public String test() {
        4
        5        int insert = cityInfoDictMapper.insert(cityInfoDict);
        6    }





    二、@Transactional介绍

1、@Transactional注解可以作用于哪些地方？
    @Transactional 可以作用在接口、类、类方法。

    作用于类：当把@Transactional 注解放在类上时，表示所有该类的public方法都配置相同的事务属性信息。

    作用于方法：当类配置了@Transactional，方法也配置了@Transactional，方法的事务会覆盖类的事务配置信息。

    作用于接口：不推荐这种使用方法，因为一旦标注在Interface上并且配置了Spring AOP 使用CGLib动态代理，将会导致@Transactional注解失效

 1@Transactional
 2@RestController
 3@RequestMapping
 4public class MybatisPlusController {
 5    @Autowired
 6    private CityInfoDictMapper cityInfoDictMapper;
 7
         8    @Transactional(rollbackFor = Exception.class)
 9    @GetMapping("/test")
10    public String test() throws Exception {
            11        CityInfoDict cityInfoDict = new CityInfoDict();
            12        cityInfoDict.setParentCityId(2);
            13        cityInfoDict.setCityName("2");
            14        cityInfoDict.setCityLevel("2");
            15        cityInfoDict.setCityCode("2");
            16        int insert = cityInfoDictMapper.insert(cityInfoDict);
            17        return insert + "";
            18    }
19}
2、@Transactional注有哪些属性？
    propagation属性
    propagation 代表事务的传播行为，默认值为 Propagation.REQUIRED，其他的属性信息如下：

    Propagation.REQUIRED：如果当前存在事务，则加入该事务，如果当前不存在事务，则创建一个新的事务。( 也就是说如果A方法和B方法都添加了注解，在默认传播模式下，A方法内部调用B方法，会把两个方法的事务合并为一个事务 ）

    Propagation.SUPPORTS：如果当前存在事务，则加入该事务；如果当前不存在事务，则以非事务的方式继续运行。

    Propagation.MANDATORY：如果当前存在事务，则加入该事务；如果当前不存在事务，则抛出异常。

    Propagation.REQUIRES_NEW：重新创建一个新的事务，如果当前存在事务，暂停当前的事务。( 当类A中的 a 方法用默认Propagation.REQUIRED模式，类B中的 b方法加上采用 Propagation.REQUIRES_NEW模式，然后在 a 方法中调用 b方法操作数据库，然而 a方法抛出异常后，b方法并没有进行回滚，因为Propagation.REQUIRES_NEW会暂停 a方法的事务 )

    Propagation.NOT_SUPPORTED：以非事务的方式运行，如果当前存在事务，暂停当前的事务。

    Propagation.NEVER：以非事务的方式运行，如果当前存在事务，则抛出异常。

    Propagation.NESTED ：和 Propagation.REQUIRED 效果一样。

    isolation 属性
    isolation ：事务的隔离级别，默认值为 Isolation.DEFAULT。

    Isolation.DEFAULT：使用底层数据库默认的隔离级别。

    Isolation.READ_UNCOMMITTED

    Isolation.READ_COMMITTED

    Isolation.REPEATABLE_READ

    Isolation.SERIALIZABLE

    timeout 属性
    timeout ：事务的超时时间，默认值为 -1。如果超过该时间限制但事务还没有完成，则自动回滚事务。

    readOnly 属性
    readOnly ：指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。

    rollbackFor 属性
    rollbackFor ：用于指定能够触发事务回滚的异常类型，可以指定多个异常类型。

    noRollbackFor属性**
    noRollbackFor：抛出指定的异常类型，不回滚事务，也可以指定多个异常类型。




    二、@Transactional失效场景



    接下来我们结合具体的代码分析一下哪些场景下，@Transactional 注解会失效。

            1、@Transactional 应用在非 public 修饰的方法上
    如果Transactional注解应用在非public 修饰的方法上，Transactional将会失效。

    图片
            在这里插入图片描述
    之所以会失效是因为在Spring AOP 代理时，如上图所示 TransactionInterceptor （事务拦截器）在目标方法执行前后进行拦截，DynamicAdvisedInterceptor（CglibAopProxy 的内部类）的 intercept 方法或 JdkDynamicAopProxy 的 invoke 方法会间接调用 AbstractFallbackTransactionAttributeSource的 computeTransactionAttribute 方法，获取Transactional 注解的事务配置信息。

            1protected TransactionAttribute computeTransactionAttribute(Method method,
2    Class<?> targetClass) {
        3        // Don't allow no-public methods as required.
        4        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            5        return null;
            6}
        此方法会检查目标方法的修饰符是否为 public，不是 public则不会获取@Transactional 的属性配置信息。

        注意：protected、private 修饰的方法上使用 @Transactional 注解，虽然事务无效，但不会有任何报错，这是我们很容犯错的一点。

        2、@Transactional 注解属性 propagation 设置错误
        这种失效是由于配置错误，若是错误的配置以下三种 propagation，事务将不会发生回滚。

        TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
        TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
        TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。

        3、@Transactional 注解属性 rollbackFor 设置错误
        rollbackFor 可以指定能够触发事务回滚的异常类型。Spring默认抛出了未检查unchecked异常（继承自 RuntimeException的异常）或者 Error才回滚事务；其他异常不会触发回滚事务。如果在事务中抛出其他类型的异常，但却期望 Spring 能够回滚事务，就需要指定 rollbackFor属性。

        图片
                在这里插入图片描述
        1// 希望自定义的异常可以进行回滚
        2@Transactional(propagation= Propagation.REQUIRED,rollbackFor= MyException.class
                若在目标方法中抛出的异常是 rollbackFor 指定的异常的子类，事务同样会回滚。Spring源码如下：

                1private int getDepth(Class<?> exceptionClass, int depth) {
            2        if (exceptionClass.getName().contains(this.exceptionName)) {
                3            // Found it!
                4            return depth;
                5}
            6        // If we've gone as far as we can go and haven't found it...
            7        if (exceptionClass == Throwable.class) {
                8            return -1;
                9}
            10return getDepth(exceptionClass.getSuperclass(), depth + 1);
            11}
        4、同一个类中方法调用，导致@Transactional失效
        开发中避免不了会对同一个类里面的方法调用，比如有一个类Test，它的一个方法A，A再调用本类的方法B（不论方法B是用public还是private修饰），但方法A没有声明注解事务，而B方法有。则外部调用方法A之后，方法B的事务是不会起作用的。这也是经常犯错误的一个地方。

        那为啥会出现这种情况？其实这还是由于使用Spring AOP代理造成的，因为只有当事务方法被当前类以外的代码调用时，才会由Spring生成的代理对象来管理。

        1//@Transactional
        2    @GetMapping("/test")
        3    private Integer A() throws Exception {
            4        CityInfoDict cityInfoDict = new CityInfoDict();
            5        cityInfoDict.setCityName("2");
            6

*
             7         * B 插入字段为 3的数据
             8

            9        this.insertB();
            10

*
             11         * A 插入字段为 2的数据
             12

            13        int insert = cityInfoDictMapper.insert(cityInfoDict);
            14
            15        return insert;
            16    }
        17
        18    @Transactional()
        19    public Integer insertB() throws Exception {
            20        CityInfoDict cityInfoDict = new CityInfoDict();
            21        cityInfoDict.setCityName("3");
            22        cityInfoDict.setParentCityId(3);
            23
            24        return cityInfoDictMapper.insert(cityInfoDict);
            25    }
        5、异常被你的 catch“吃了”导致@Transactional失效
        这种情况是最常见的一种@Transactional注解失效场景

        1    @Transactional
        2    private Integer A() throws Exception {
            3        int insert = 0;
            4        try {
                5            CityInfoDict cityInfoDict = new CityInfoDict();
                6            cityInfoDict.setCityName("2");
                7            cityInfoDict.setParentCityId(2);
                8

*
                 9             * A 插入字段为 2的数据
                 10

                11            insert = cityInfoDictMapper.insert(cityInfoDict);
                12

*
                 13             * B 插入字段为 3的数据
                 14

                15            b.insertB();
                16        } catch (Exception e) {
                17            e.printStackTrace();
                18        }
            19    }
        如果B方法内部抛了异常，而A方法此时try catch了B方法的异常，那这个事务还能正常回滚吗？

        答案：不能！

        会抛出异常：

        1org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only
        因为当ServiceB中抛出了一个异常以后，ServiceB标识当前事务需要rollback。但是ServiceA中由于你手动的捕获这个异常并进行处理，ServiceA认为当前事务应该正常commit。此时就出现了前后不一致，也就是因为这样，抛出了前面的UnexpectedRollbackException异常。

        spring的事务是在调用业务方法之前开始的，业务方法执行完毕之后才执行commit or rollback，事务是否执行取决于是否抛出runtime异常。如果抛出runtime exception 并在你的业务方法中没有catch到的话，事务会回滚。

        在业务方法中一般不需要catch异常，如果非要catch一定要抛出throw new RuntimeException()，或者注解中指定抛异常类型@Transactional(rollbackFor=Exception.class)，否则会导致事务失效，数据commit造成数据不一致，所以有些时候try catch反倒会画蛇添足。

        6、数据库引擎不支持事务
        这种情况出现的概率并不高，事务能否生效数据库引擎是否支持事务是关键。常用的MySQL数据库默认使用支持事务的innodb引擎。一旦数据库引擎切换成不支持事务的myisam，那事务就从根本上失效了。



        总结

        @Transactional 注解的看似简单易用，但如果对它的用法一知半解，还是会踩到很多坑的。

        今天就说这么多，如果本文对您有一点帮助，希望能得到您一个点赞👍哦

设栈网
首页
软件教程
电子科技
语音教程
栈问
设计师
行业资讯
关于我们

输入搜索内容
首页 / 电子科技 / 综合教程
数据库事务的四大特性
日期：2020-12-18 14:32:13 所属：综合教程 浏览： 3849

数据库事务的四大特性

数据库事务的四大特性如下：1.原子性；2.一致性；3.隔离性；4.持久性。

操作步骤/方法
1 数据库事务的四大特性如下：
2 1.原子性
3 第一个原子性，这个是最简单的。说的是一个事物内所有操作共同组成一个原子包，要么全部成功，要么全部失败。这是最基本的特性，保证了因为一些其他因素导致数据库异常，或者宕机。
4 2.一致性
5 第二一致性，这个是大家误解最深的，很多博客都喜欢用银行转账的例子来讲一直性，所谓的一致性是基于原子性。
6 原子性只保证了一个事物内的所有操作同一性，大家同生死，不会出现你死了，我还活着。但是，原子性并没有保证大家同一时刻一起生，一起死。计算机指令是有先后顺序的，这样就决定了一个事物的提交，会经历一个时间过程，那么如果事物提交进行到了一半，我读取了数据库，会不会读到中间结果？
7 为了防止这样的情况，数据库事物的一致性就规定了事物提交前后，永远只可能存在事物提交前的状态和事物提交后的状态，从一个一致性的状态到另一个一致性状态，而不可能出现中间的过程态。也就是说事物的执行结果是量子化状态，而不是线性状态。
8 数据库提交事物会有一个过程，如果提交的时候，存在一个时间差，在提交的第一秒，一个删除过程还没完成到了第三秒才完成，会不会第一秒访问的人和第三秒访问的人得到不同的结果？出现不一致，状态的混沌？这就是一致性得保证的只会有前状态和后状态，绝不会出现中间态。
9 3.隔离性
10 事物的隔离性，基于原子性和一致性，因为事物是原子化，量子化的，所以，事物可以有多个原子包的形式并发执行，但是，每个事物互不干扰。
11 但是，由于多个事物可能操作同一个资源，不同的事物为了保证隔离性，会有很多锁方案，当然这是数据库的实现，他们怎么实现的，我们不必深究。
12 4.持久性
13 持久性，当一个事物提交之后，数据库状态永远的发生了改变，这个事物只要提交了，哪怕提交后宕机，他也确确实实的提交了，不会出现因为刚刚宕机了而让提交不生效，是要事物提交，他就像洗不掉的纹身，永远的固化了，除非你毁了硬盘。

继续阅读


抖音上传照片模糊
306848
如何让微信个性签名居中
222304
oppo 新机reno z是什么处理器
167022
苹果11后盖是玻璃的吗
127215
阅读更多文章


苹果6手机怎么截屏
1.同时按手机的电源键+主屏Home键，即可截图

所属：综合教程

华为麦芒5杂志锁屏怎么关闭
华为麦芒5杂志锁屏关闭方法如下：1.首先打开

所属：综合教程

搜狗搜索候选关闭
1.在手机中找到搜狗输入法，点击进入；2.进入搜

所属：综合教程

set协议和ssl协议的异同
1.两种都是应用于电子商务用的网络安全协议

所属：综合教程

苹果11后壳是什么材质
苹果11后壳是玻璃材质。iphone11是苹果公司

所属：综合教程
热门文章


苹果6有128g的嘛


set协议是指


快手关注取消后找回


苹果6有nfc功能吗


台式电脑连无线网步骤

相关文章

01苹果11九宫格怎么设置
02set协议又称为什么
03苹果6怎么开启nfc
04快手关注视频不按顺序
05oppoa1有呼吸灯吗
设栈-设计者的客栈,超过100万用户信赖的自学平台,海量设计教程、办公软件、职业技能、互动问答等视频教程在线学 ,设站网助你快乐学习，成就精彩人生！

设栈网
首页  软件教程电子科技语音教程栈问设计师行业资讯关于我们	最近更新  全部教程
电话：0552-5430258  QQ交流群：951592940

课程内容版权均归 设栈网 所有 皖ICP备19019105号-1

二、spring事务的四种隔离级别
1、事务的四大特性（ACID）
原子性
操作要么全部成功，要么全部失败回滚。

一致性
事务执行前和执行后处于一致性状态。例如，转账前A、B共5000元，A、B之间转账后，两者之和仍应该是5000元。

隔离性
事务之间互不干扰。

持久性
事务一旦提交，数据的改变是永久性的，即使这时候数据库发生故障，数据也不会丢失。

2、与事务隔离级别的相关问题
脏读
A事务对一条记录进行修改，尚未提交，B事务已经看到了A的修改结果。若A发生回滚，B读到的数据就是错误的，这就是脏读。

不可重复读
A事务对一条记录进行修改，尚未提交，B事务第一次查询该记录，看到的是修改之后的结果，此时A发生回滚，B事务又一次查询该记录，看到的是回滚后的结果。同一个事务内，B两次查询结果不一致，这就是不可重复读。

幻读
A事务对所有记录进行修改，尚未提交，此时B事务创建了一条新记录，A、B都提交。A查看所有数据，发现有一条数据没有被修改，因为这是B事务新增的，就想看到了幻象一样，这就是幻读。

3、事务的隔离级别
读未提交（read uncommitted）
事务尚未提交，其他事务即可以看到该事务的修改结果。隔离级别最差，脏读、不可重复读、幻读都不能避免。

读提交（read committed）
事务只能看到其他事务提交之后的数据。可避免脏读，不可重复读、幻读无法避免。

不可重复读原因：A事务修改，B事务查询，A提交前和提交后，B事务看到的数据是不一致的。

幻读原因：A事务修改，B事务新增，B事务提交前，A事务已经提交。B事务提交后，A发现仍有数据未修改。

可重复读（repeatable read）-------innodb默认隔离级别
一个事务多次查询，无论其他事务对数据如何修改  ，看到的数据都是一致的。因为A事务查询数据时，若B同时在修改数据，A事务看到的永远是B事务执行前的数据。只有当A提交或者回滚之后，看到的才是最新的被B修改知乎的数据。可避免脏读、不可重复读，幻读无法避免。

序列化（serializable）
事务顺序执行，可避免脏读、不可重复读、幻读，但效率最差。因为A事务执行时，其他事务必须等待。

首发于
锤子学习成长日记
写文章
带你读懂Spring 事务——事务的传播机制
带你读懂Spring 事务——事务的传播机制
爱做梦的锤子
爱做梦的锤子
公众号/微博/简书 同id，一个爱用手机拍照的程序猿
56 人赞同了该文章
一、什么是事务的传播？
简单的理解就是多个事务方法相互调用时,事务如何在这些方法间传播。

举个栗子，方法A是一个事务的方法，方法A执行过程中调用了方法B，那么方法B有无事务以及方法B对事务的要求不同都会对方法A的事务具体执行造成影响，同时方法A的事务对方法B的事务执行也有影响，这种影响具体是什么就由两个方法所定义的事务传播类型所决定。
二、Spring事务传播类型枚举Propagation介绍
在Spring中对于事务的传播行为定义了七种类型分别是：REQUIRED、SUPPORTS、MANDATORY、REQUIRES_NEW、NOT_SUPPORTED、NEVER、NESTED。

在Spring源码中这七种类型被定义为了枚举。源码在org.springframework.transaction.annotation包下的Propagation，源码中注释很多，对传播行为的七种类型的不同含义都有解释，后文中锤子我也会给大家分析，我在这里就不贴所有的源码，只把这个类上的注解贴一下，翻译一下就是：表示与TransactionDefinition接口相对应的用于@Transactional注解的事务传播行为的枚举。

也就是说枚举类Propagation是为了结合@Transactional注解使用而设计的，这个枚举里面定义的事务传播行为类型与TransactionDefinition中定义的事务传播行为类型是对应的，所以在使用@Transactional注解时我们就要使用Propagation枚举类来指定传播行为类型，而不直接使用TransactionDefinition接口里定义的属性。

在TransactionDefinition接口中定义了Spring事务的一些属性，不仅包括事务传播特性类型，还包括了事务的隔离级别类型（事务的隔离级别后面文章会详细讲解），更多详细信息，大家可以打开源码自己翻译一下里面的注释

package org.springframework.transaction.annotation;

import org.springframework.transaction.TransactionDefinition;

*/
/**
 * Enumeration that represents transaction propagation behaviors for use
 * with the {@link Transactional} annotation, corresponding to the
 * {@link TransactionDefinition} interface.
 *
 * @author Colin Sampaleanu
 * @author Juergen Hoeller
 * @since 1.2



三、七种事务传播行为详解与示例
        在介绍七种事务传播行为前，我们先设计一个场景，帮助大家理解，场景描述如下

        现有两个方法A和B，方法A执行会在数据库ATable插入一条数据，方法B执行会在数据库BTable插入一条数据，伪代码如下:
//将传入参数a存入ATable
        pubilc void A(a){
        insertIntoATable(a);
        }
//将传入参数b存入BTable
public void B(b){
        insertIntoBTable(b);
        }
        接下来，我们看看在如下场景下，没有事务，情况会怎样

public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }

public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        在这里要做一个重要提示：Spring中事务的默认实现使用的是AOP，也就是代理的方式，如果大家在使用代码测试时，同一个Service类中的方法相互调用需要使用注入的对象来调用，不要直接使用this.方法名来调用，this.方法名调用是对象内部方法调用，不会通过Spring代理，也就是事务不会起作用

        以上伪代码描述的一个场景，方法testMain和testB都没有事务，执行testMain方法，那么结果会怎么样呢？

        相信大家都知道了，就是a1数据成功存入ATable表，b1数据成功存入BTable表，而在抛出异常后b2数据存储就不会执行，也就是b2数据不会存入数据库，这就是没有事务的场景。

        可想而知，在上一篇文章（认识事务）中举例的转账操作，如果在某一步发生异常，且没有事务，那么钱是不是就凭空消失了，所以事务在数据库操作中的重要性可想而知。接下我们就开始理解七种不同事务传播类型的含义

        REQUIRED(Spring默认的事务传播类型)
        如果当前没有事务，则自己新建一个事务，如果当前存在事务，则加入这个事务

        源码说明如下：

*
         * Support a current transaction, create a new one if none exists.
         * Analogous to EJB transaction attribute of the same name.
         * <p>This is the default setting of a transaction annotation.


        REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED),
        (示例1)根据场景举栗子,我们在testMain和testB上声明事务，设置传播行为REQUIRED，伪代码如下：

@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }
@Transactional(propagation = Propagation.REQUIRED)
public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        该场景下执行testMain方法结果如何呢？

        数据库没有插入新的数据，数据库还是保持着执行testMain方法之前的状态，没有发生改变。testMain上声明了事务，在执行testB方法时就加入了testMain的事务（当前存在事务，则加入这个事务），在执行testB方法抛出异常后事务会发生回滚，又testMain和testB使用的同一个事务，所以事务回滚后testMain和testB中的操作都会回滚，也就使得数据库仍然保持初始状态

        (示例2)根据场景再举一个栗子,我们只在testB上声明事务，设置传播行为REQUIRED，伪代码如下：

public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }
@Transactional(propagation = Propagation.REQUIRED)
public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        这时的执行结果又如何呢？

        数据a1存储成功，数据b1和b2没有存储。由于testMain没有声明事务，testB有声明事务且传播行为是REQUIRED，所以在执行testB时会自己新建一个事务（如果当前没有事务，则自己新建一个事务），testB抛出异常则只有testB中的操作发生了回滚，也就是b1的存储会发生回滚，但a1数据不会回滚，所以最终a1数据存储成功，b1和b2数据没有存储

        SUPPORTS
        当前存在事务，则加入当前事务，如果当前没有事务，就以非事务方法执行

        源码注释如下(太长省略了一部分)，其中里面有一个提醒翻译一下就是：“对于具有事务同步的事务管理器，SUPPORTS与完全没有事务稍有不同，因为它定义了可能应用同步的事务范围”。这个是与事务同步管理器相关的一个注意项，这里不过多讨论。

*
         * Support a current transaction, execute non-transactionally if none exists.
         * Analogous to EJB transaction attribute of the same name.
         * <p>Note: For transaction managers with transaction synchronization,
         * {@code SUPPORTS} is slightly different from no transaction at all,
         * as it defines a transaction scope that synchronization will apply for.
         ...


        SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS),
        (示例3)根据场景举栗子，我们只在testB上声明事务，设置传播行为SUPPORTS，伪代码如下：

public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }
@Transactional(propagation = Propagation.SUPPORTS)
public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        这种情况下，执行testMain的最终结果就是，a1，b1存入数据库，b2没有存入数据库。由于testMain没有声明事务，且testB的事务传播行为是SUPPORTS，所以执行testB时就是没有事务的（如果当前没有事务，就以非事务方法执行），则在testB抛出异常时也不会发生回滚，所以最终结果就是a1和b1存储成功，b2没有存储。

        那么当我们在testMain上声明事务且使用REQUIRED传播方式的时候，这个时候执行testB就满足当前存在事务，则加入当前事务，在testB抛出异常时事务就会回滚，最终结果就是a1，b1和b2都不会存储到数据库

        MANDATORY
        当前存在事务，则加入当前事务，如果当前事务不存在，则抛出异常。

        源码注释如下：

*
         * Support a current transaction, throw an exception if none exists.
         * Analogous to EJB transaction attribute of the same name.


        MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),
        (示例4)场景举栗子，我们只在testB上声明事务，设置传播行为MANDATORY，伪代码如下：

public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }
@Transactional(propagation = Propagation.MANDATORY)
public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        这种情形的执行结果就是a1存储成功，而b1和b2没有存储。b1和b2没有存储，并不是事务回滚的原因，而是因为testMain方法没有声明事务，在去执行testB方法时就直接抛出事务要求的异常（如果当前事务不存在，则抛出异常），所以testB方法里的内容就没有执行。

        那么如果在testMain方法进行事务声明，并且设置为REQUIRED，则执行testB时就会使用testMain已经开启的事务，遇到异常就正常的回滚了。

        REQUIRES_NEW
        创建一个新事务，如果存在当前事务，则挂起该事务。

        可以理解为设置事务传播类型为REQUIRES_NEW的方法，在执行时，不论当前是否存在事务，总是会新建一个事务。

        源码注释如下

*
 * Create a new transaction, and suspend the current transaction if one exists.
 ...


        REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),
        (示例5)场景举栗子，为了说明设置REQUIRES_NEW的方法会开启新事务，我们把异常发生的位置换到了testMain，然后给testMain声明事务，传播类型设置为REQUIRED，testB也声明事务，设置传播类型为REQUIRES_NEW，伪代码如下

@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        throw Exception;     //发生异常抛出
        }
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void testB(){
        B(b1);  //调用B入参b1
        B(b2);  //调用B入参b2
        }
        这种情形的执行结果就是a1没有存储，而b1和b2存储成功，因为testB的事务传播设置为REQUIRES_NEW,所以在执行testB时会开启一个新的事务，testMain中发生的异常时在testMain所开启的事务中，所以这个异常不会影响testB的事务提交，testMain中的事务会发生回滚，所以最终a1就没有存储，而b1和b2就存储成功了。

        与这个场景对比的一个场景就是testMain和testB都设置为REQUIRED，那么上面的代码执行结果就是所有数据都不会存储，因为testMain和testMain是在同一个事务下的，所以事务发生回滚时，所有的数据都会回滚

        NOT_SUPPORTED
        始终以非事务方式执行,如果当前存在事务，则挂起当前事务

        可以理解为设置事务传播类型为NOT_SUPPORTED的方法，在执行时，不论当前是否存在事务，都会以非事务的方式运行。

        源码说明如下

*
 * Execute non-transactionally, suspend the current transaction if one exists.
 ...


        NOT_SUPPORTED(TransactionDefinition.PROPAGATION_NOT_SUPPORTED),
        (示例6)场景举栗子，testMain传播类型设置为REQUIRED，testB传播类型设置为NOT_SUPPORTED，且异常抛出位置在testB中，伪代码如下

@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        该场景的执行结果就是a1和b2没有存储，而b1存储成功。testMain有事务，而testB不使用事务，所以执行中testB的存储b1成功，然后抛出异常，此时testMain检测到异常事务发生回滚，但是由于testB不在事务中，所以只有testMain的存储a1发生了回滚，最终只有b1存储成功，而a1和b1都没有存储

        NEVER
        不使用事务，如果当前事务存在，则抛出异常

        很容易理解，就是我这个方法不使用事务，并且调用我的方法也不允许有事务，如果调用我的方法有事务则我直接抛出异常。

        源码注释如下：

*
         * Execute non-transactionally, throw an exception if a transaction exists.
         * Analogous to EJB transaction attribute of the same name.


        NEVER(TransactionDefinition.PROPAGATION_NEVER),
        (示例7)场景举栗子，testMain设置传播类型为REQUIRED，testB传播类型设置为NEVER，并且把testB中的抛出异常代码去掉，则伪代码如下

@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        }
@Transactional(propagation = Propagation.NEVER)
public void testB(){
        B(b1);  //调用B入参b1
        B(b2);  //调用B入参b2
        }
        该场景执行，直接抛出事务异常，且不会有数据存储到数据库。由于testMain事务传播类型为REQUIRED，所以testMain是运行在事务中，而testB事务传播类型为NEVER，所以testB不会执行而是直接抛出事务异常，此时testMain检测到异常就发生了回滚，所以最终数据库不会有数据存入。

        NESTED
        如果当前事务存在，则在嵌套事务中执行，否则REQUIRED的操作一样（开启一个事务）

        这里需要注意两点：

        和REQUIRES_NEW的区别
        REQUIRES_NEW是新建一个事务并且新开启的这个事务与原有事务无关，而NESTED则是当前存在事务时（我们把当前事务称之为父事务）会开启一个嵌套事务（称之为一个子事务）。
        在NESTED情况下父事务回滚时，子事务也会回滚，而在REQUIRES_NEW情况下，原有事务回滚，不会影响新开启的事务。
        和REQUIRED的区别
        REQUIRED情况下，调用方存在事务时，则被调用方和调用方使用同一事务，那么被调用方出现异常时，由于共用一个事务，所以无论调用方是否catch其异常，事务都会回滚
        而在NESTED情况下，被调用方发生异常时，调用方可以catch其异常，这样只有子事务回滚，父事务不受影响
        (示例8)场景举栗子，testMain设置为REQUIRED，testB设置为NESTED，且异常发生在testMain中，伪代码如下

@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
        A(a1);  //调用A入参a1
        testB();    //调用testB
        throw Exception;     //发生异常抛出
        }
@Transactional(propagation = Propagation.NESTED)
public void testB(){
        B(b1);  //调用B入参b1
        B(b2);  //调用B入参b2
        }
        该场景下，所有数据都不会存入数据库，因为在testMain发生异常时，父事务回滚则子事务也跟着回滚了，可以与(示例5)比较看一下，就找出了与REQUIRES_NEW的不同

        (示例9)场景举栗子，testMain设置为REQUIRED，testB设置为NESTED，且异常发生在testB中，伪代码如下

@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
        A(a1);  //调用A入参a1
        try{
        testB();    //调用testB
        }catch（Exception e){

        }
        A(a2);
        }
@Transactional(propagation = Propagation.NESTED)
public void testB(){
        B(b1);  //调用B入参b1
        throw Exception;     //发生异常抛出
        B(b2);  //调用B入参b2
        }
        这种场景下，结果是a1,a2存储成功，b1和b2存储失败，因为调用方catch了被调方的异常，所以只有子事务回滚了。

        同样的代码，如果我们把testB的传播类型改为REQUIRED，结果也就变成了：没有数据存储成功。就算在调用方catch了异常，整个事务还是会回滚，因为，调用方和被调方共用的同一个事务

        文章欢迎转载，转载请注明出处，个人公众号【爱做梦的锤子】，全网同id，个站 http://te-amo.site，欢迎关注，里面会分享更多有用知识，还有我的私密照片
        编辑于 2020-08-04
        Spring
        数据库事务
        ​赞同 56​
        ​17 条评论
        ​分享
        ​喜欢
        ​收藏
        ​申请转载
        ​

        赞同 56

        ​
        分享
        文章被以下专栏收录
        锤子学习成长日记
        锤子学习成长日记
        Java、大数据相关学习，不整标题党！
        推荐阅读
        Spring官方都推荐使用的@Transactional事务，为啥我不建议使用！
        事务管理在系统开发中是不可缺少的一部分，Spring提供了很好事务管理机制，主要分为编程式事务和声明式事务两种。 关于事务的基础知识，如什么是事务，数据库事务以及Spring事务的ACID、隔…

        Holli...
        发表于Java干...
        Spring Boot中的事务是如何实现的
        Spring Boot中的事务是如何实现的
        五月瑶风
        发表于技术家
        Spring 的事务实现原理和传播机制
        Spring 的事务实现原理和传播机制
        轻风
        Spring事务失效的 8 大原因，这次可以吊打面试官了！
        Spring事务失效的 8 大原因，这次可以吊打面试官了！
        栈长
        发表于Java技...

        17 条评论
        ​切换为时间排序
        写下你的评论...


        发布
        凡人
        凡人2020-11-11
        写的很好，把你加到我的友链了哈。 https://www.xjf666.xyz


        ​1
        ​回复
        ​踩
        ​ 举报
        Tiger
        Tiger回复凡人2020-11-11
        大神，加个好友

        ​赞
        ​回复
        ​踩
        ​ 举报
        steve
        steve03-13
        写的好，很清晰！

        ​赞
        ​回复
        ​踩
        ​ 举报
        黄焖鸡米饭
        黄焖鸡米饭03-08
        请教个问题，事务的传播机制不是只适用于不同类中的方法调用么，你这A和B两个方法都在同一个类中把

        ​赞
        ​回复
        ​踩
        ​ 举报
        Piao飘
        Piao飘02-22
        NESTED说法似乎存在错误

        ​赞
        ​回复
        ​踩
        ​ 举报
        爱做梦的锤子
        爱做梦的锤子 (作者) 回复Piao飘02-23
        还请指正，具体是那一些内容的错误
        ​赞
        ​回复
        ​踩
        ​ 举报
        格兰芬多拔草工
        格兰芬多拔草工02-04
        真不错！本来这几个概念有点模糊的，看完文章一下子明白了。感谢！


        ​赞
        ​回复
        ​踩
        ​ 举报
        Mathkey
        Mathkey01-28
        写得超好，大佬是怎么记住的啊。。感觉记不住。。

        ​赞
        ​回复
        ​踩
        ​ 举报
        隨筆塗鴉
        隨筆塗鴉2020-12-22
        写得很清晰易懂～谢谢分享啦～


        ​赞
        ​回复
        ​踩
        ​ 举报
        Young
        Young2020-12-16
public void testB(){
        B(b1); //调用B入参b1
        throw Exception; //发生异常抛出
        B(b2); //调用B入参b2
        }

        这样直接就编译不通过吧？ unreachable code : B(b2)

        ​赞
        ​回复
        ​踩
        ​ 举报
        爱做梦的锤子
        爱做梦的锤子 (作者) 回复Young2020-12-16
        这一段是伪代码，你按照JAVA编码的语法规范来写就好啦，祝你成功，加油
        ​赞
        ​回复
        ​踩
        ​ 举报
        Young
        Young回复爱做梦的锤子 (作者) 2020-12-16
        好

        ​赞
        ​回复
        ​踩
        ​ 举报
        每月一次
        每月一次2020-09-15
        通过实现，反推场景

        ​赞
        ​回复
        ​踩
        ​ 举报
        xuelan
        xuelan2020-08-10
        能把一个知识点用简单易懂的方式教会学生的人可称为老师，给老师点赞。另外能看出来你是结合工作编程实际来写的，所以很容易看懂

        ​赞
        ​回复
        ​踩
        ​ 举报
        爱做梦的锤子
        爱做梦的锤子 (作者) 回复xuelan2020-08-11
        感谢你的支持与鼓励[大笑]
        ​赞
        ​回复
        ​踩
        ​ 举报
        走走停停
        走走停停2020-08-03
        写的很好, 就是后面NESTED例子上隔离级别用的NEVER, 应该是拷贝急了,改一下吧

        ​赞
        ​回复
        ​踩
        ​ 举报
        爱做梦的锤子
        爱做梦的锤子 (作者) 回复走走停停2020-08-04
        感谢指出错误，已经修改


        ​赞
        ​回复
        ​踩
        ​ 举报


        选择语言

}

*/
