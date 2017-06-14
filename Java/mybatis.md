# TypeHandler

Déclaration

    @MappedJdbcTypes(JdbcType.BLOB)
    @MappedTypes(MyType.class)
    public class MyTypeHandler extends org.apache.ibatis.type.BaseTypeHandler<MyType> {
        ...
    }

## Utilisation dans IProfilMapper

Utilisation dans des `@Results` :

    @Results({
        ...
        @Result(property = "java_field", column = "sql_col", typeHandler = MyTypeHandler.class),
        ...
    })
    HashMap<String, MyType> select(String id);
    
Utilisation dans des `@Insert`, `@Update`, etc... :

    @Insert("Insert INTO another_table(sql_col) "
        + "VALUES(#{sql_col, typeHandler=package.MyTypeHandler})")
    void insert(AnotherType entry);
    
**Ne pas utiliser** pour les  `@Select` (sinon Exception) :

    @Select("SELECT col1, col2 FROM table")
    @Results({
        @Result(property = "javaField1OfMyType", column = "col1"),
        @Result(property = "javaField2OfMyType", column = "col2"),
    })
    List<MyType> select();

Erreur si on l'utilise pour les `@Select` : 

    at com.hazelcast.partition.impl.InternalPartitionServiceImpl.getPartitionId(InternalPartitionServiceImpl.java:1477)
    at com.hazelcast.map.impl.MapKeyLoaderUtil$2.apply(MapKeyLoaderUtil.java:113)
    at com.hazelcast.map.impl.MapKeyLoaderUtil$2.apply(MapKeyLoaderUtil.java:110)
    at com.hazelcast.util.IterableUtil$2.next(IterableUtil.java:60)
    at com.hazelcast.map.impl.MapKeyLoaderUtil.nextBatch(MapKeyLoaderUtil.java:86)
    at com.hazelcast.map.impl.MapKeyLoaderUtil$1.next(MapKeyLoaderUtil.java:76)
    at com.hazelcast.map.impl.MapKeyLoaderUtil$1.next(MapKeyLoaderUtil.java:65)
    at com.hazelcast.map.impl.MapKeyLoader.sendKeysInBatches(MapKeyLoader.java:294)
    at com.hazelcast.map.impl.MapKeyLoader.access$200(MapKeyLoader.java:70)
    at com.hazelcast.map.impl.MapKeyLoader$1.call(MapKeyLoader.java:181)
    at com.hazelcast.map.impl.MapKeyLoader$1.call(MapKeyLoader.java:178)
    at com.hazelcast.util.executor.CompletableFutureTask.run(CompletableFutureTask.java:67)
    at com.hazelcast.util.executor.CachedExecutorServiceDelegate$Worker.run(CachedExecutorServiceDelegate.java:212)
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
    at java.lang.Thread.run(Thread.java:745)
    at com.hazelcast.util.executor.HazelcastManagedThread.executeRun(HazelcastManagedThread.java:76)
    at com.hazelcast.util.executor.HazelcastManagedThread.run(HazelcastManagedThread.java:92)

