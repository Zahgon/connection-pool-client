/*
 * Copyright 2015-2016 Dark Phoenixs (Open-Source Organization).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.darkphoenixs.pool.redis;

import org.darkphoenixs.pool.ConnectionException;
import org.darkphoenixs.pool.ConnectionPool;
import org.darkphoenixs.pool.PoolBase;
import org.darkphoenixs.pool.PoolConfig;
import redis.clients.jedis.Client;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Title: RedisSentinelConnPoolOld</p>
 * <p>Description: Redis哨兵连接池</p>
 *
 * @author Victor
 * @version 1.0
 * @see PoolBase
 * @see ConnectionPool
 * @since 2015年9月19日
 */
@Deprecated
public class RedisSentinelConnPoolOld extends PoolBase<Jedis> implements ConnectionPool<Jedis> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2917559115197092907L;

    /**
     * masterName
     */
    protected String masterName;

    /**
     * sentinels
     */
    protected Set<String> sentinels;

    /**
     * poolConfig
     */
    protected PoolConfig poolConfig;

    /**
     * connectionTimeout
     */
    protected int connectionTimeout = RedisConfig.DEFAULT_TIMEOUT;

    /**
     * soTimeout
     */
    protected int soTimeout = RedisConfig.DEFAULT_TIMEOUT;

    /**
     * password
     */
    protected String password = RedisConfig.DEFAULT_PASSWORD;

    /**
     * database
     */
    protected int database = RedisConfig.DEFAULT_DATABASE;

    /**
     * clientName
     */
    protected String clientName = RedisConfig.DEFAULT_CLIENTNAME;

    /**
     * masterListeners
     */
    protected Set<RedisMasterListener> masterListeners = new HashSet<RedisMasterListener>();

    /**
     * log
     */
    protected Logger log = Logger.getLogger(getClass().getName());

    /**
     * factory
     */
    private volatile RedisConnectionFactoryOld factory;

    /**
     * currentHostMaster
     */
    private volatile HostAndPort currentHostMaster;

    /**
     * <p>RedisSentinelConnPoolOld</p>
     * <p>默认构造方法</p>
     */
    protected RedisSentinelConnPoolOld() {
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels) {
        this(masterName, sentinels, new PoolConfig());
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig) {
        this(masterName, sentinels, poolConfig, RedisConfig.DEFAULT_TIMEOUT);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     * @param timeout    超时
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, final int timeout) {
        this(masterName, sentinels, poolConfig, timeout, RedisConfig.DEFAULT_PASSWORD);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param password   密码
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, String password) {
        this(masterName, sentinels, new PoolConfig(), password);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     * @param password   密码
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, final String password) {
        this(masterName, sentinels, poolConfig, RedisConfig.DEFAULT_TIMEOUT, password);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     * @param timeout    超时
     * @param password   密码
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, int timeout, final String password) {
        this(masterName, sentinels, poolConfig, timeout, password, RedisConfig.DEFAULT_DATABASE);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     * @param timeout    超时
     * @param password   密码
     * @param database   数据库
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, int timeout, final String password, final int database) {
        this(masterName, sentinels, poolConfig, timeout, password, database, RedisConfig.DEFAULT_CLIENTNAME);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     * @param timeout    超时
     * @param password   密码
     * @param database   数据库
     * @param clientName 客户端名称
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, int timeout, final String password, final int database, final String clientName) {
        this(masterName, sentinels, poolConfig, timeout, timeout, password, database, clientName);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName 主机名称
     * @param sentinels  哨兵列表
     * @param poolConfig 池配置
     * @param timeout    连接超时
     * @param soTimeout  超时时间
     * @param password   密码
     * @param database   数据库
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, final int timeout, final int soTimeout, final String password, final int database) {
        this(masterName, sentinels, poolConfig, timeout, soTimeout, password, database, RedisConfig.DEFAULT_CLIENTNAME);
    }

    /**
     * <p>Title: RedisSentinelConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param masterName        主机名称
     * @param sentinels         哨兵列表
     * @param poolConfig        池配置
     * @param connectionTimeout 连接超时
     * @param soTimeout         超时时间
     * @param password          密码
     * @param database          数据库
     * @param clientName        客户端名称
     */
    public RedisSentinelConnPoolOld(String masterName, Set<String> sentinels, final PoolConfig poolConfig, final int connectionTimeout, final int soTimeout, final String password, final int database, final String clientName) {
        this.masterName = masterName;
        this.sentinels = sentinels;
        this.poolConfig = poolConfig;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.password = password;
        this.database = database;
        this.clientName = clientName;
    }

    /**
     * <p>RedisSentinelConnPoolOld</p>
     * <p>构造方法</p>
     *
     * @param poolConfig 池配置
     * @param properties 参数配置
     * @since 1.2.1
     */
    public RedisSentinelConnPoolOld(final PoolConfig poolConfig, final Properties properties) {
        this.poolConfig = poolConfig;
        if (null != properties.getProperty(RedisConfig.CONN_TIMEOUT_PROPERTY))
            this.connectionTimeout = Integer.parseInt(properties.getProperty(RedisConfig.CONN_TIMEOUT_PROPERTY));
        if (null != properties.getProperty(RedisConfig.SO_TIMEOUT_PROPERTY))
            this.soTimeout = Integer.parseInt(properties.getProperty(RedisConfig.SO_TIMEOUT_PROPERTY));
        if (null != properties.getProperty(RedisConfig.DATABASE_PROPERTY))
            this.database = Integer.parseInt(properties.getProperty(RedisConfig.DATABASE_PROPERTY));
        this.password = properties.getProperty(RedisConfig.PASSWORD_PROPERTY);
        this.clientName = properties.getProperty(RedisConfig.CLIENTNAME_PROPERTY);
        String masterName = properties.getProperty(RedisConfig.MASTERNAME_PROPERTY);
        if (masterName == null)
            throw new ConnectionException("[" + RedisConfig.MASTERNAME_PROPERTY + "] is required !");
        this.masterName = masterName;
        String sentinels = properties.getProperty(RedisConfig.SENTINELS_PROPERTY);
        if (sentinels == null)
            throw new ConnectionException("[" + RedisConfig.SENTINELS_PROPERTY + "] is required !");
        this.sentinels = new HashSet<String>(Arrays.asList(sentinels.split(",")));
    }

    /**
     * <p>Title: getCurrentHostMaster</p>
     * <p>Description: 获得当前主机</p>
     *
     * @return 当前主机
     */
    public HostAndPort getCurrentHostMaster() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>init</p>
     * <p>初始化方法</p>
     *
     * @since 1.2.1
     */
    public void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>Title: initPool</p>
     * <p>Description: 初始化连接池</p>
     *
     * @param master 主机
     */
    protected void initPool(HostAndPort master) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>Title: initSentinels</p>
     * <p>Description: 初始化哨兵列表</p>
     *
     * @param sentinels  哨兵列表
     * @param masterName 主机名称
     * @return 主机
     */
    protected HostAndPort initSentinels(Set<String> sentinels, final String masterName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>initListeners</p>
     * <p>初始化监听器</p>
     *
     * @param master     主机
     * @param sentinels  哨兵列表
     * @param masterName 主机名称
     * @since 1.2.1
     */
    protected void initListeners(HostAndPort master, Set<String> sentinels, final String masterName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>Title: toHostAndPort</p>
     * <p>Description: 主机地址转换</p>
     *
     * @param getMasterAddrByNameResult 主机地址
     * @return 主机
     */
    protected HostAndPort toHostAndPort(List<String> getMasterAddrByNameResult) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Jedis getResource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Jedis getConnection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void returnConnection(Jedis conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void invalidateConnection(Jedis conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>Title: RedisMasterListener</p>
     * <p>Description: Redis主机监听器</p>
     *
     * @author Victor
     * @version 1.0
     * @see Thread
     * @since 2015年9月19日
     */
    protected class RedisMasterListener extends Thread {

        /**
         * masterName
         */
        protected String masterName;

        /**
         * host
         */
        protected String host;

        /**
         * port
         */
        protected int port;

        /**
         * subscribeRetryWaitTimeMillis
         */
        protected long subscribeRetryWaitTimeMillis = 5000;

        /**
         * jedis
         */
        protected volatile Jedis j;

        /**
         * running
         */
        protected AtomicBoolean running = new AtomicBoolean(false);

        /**
         * <p>Title: RedisMasterListener</p>
         * <p>Description: 默认构造方法</p>
         */
        protected RedisMasterListener() {
        }

        /**
         * <p>Title: RedisMasterListener</p>
         * <p>Description: 构造方法</p>
         *
         * @param masterName 主机名称
         * @param host       地址
         * @param port       端口
         */
        public RedisMasterListener(String masterName, String host, int port) {
            super(String.format("MasterListener-%s-[%s:%d]", masterName, host, port));
            this.masterName = masterName;
            this.host = host;
            this.port = port;
        }

        /**
         * <p>Title: RedisMasterListener</p>
         * <p>Description: 构造方法</p>
         *
         * @param masterName                   主机名称
         * @param host                         地址
         * @param port                         端口
         * @param subscribeRetryWaitTimeMillis 订阅重试等待时间
         */
        public RedisMasterListener(String masterName, String host, int port, long subscribeRetryWaitTimeMillis) {
            this(masterName, host, port);
            this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
        }

        @Override
        public void run() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * <p>Title: shutdown</p>
         * <p>Description: 关闭监听器</p>
         */
        public void shutdown() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * <p>Title: RedisMasterPubSub</p>
     * <p>Description: Redis主机状态订阅</p>
     *
     * @author Victor
     * @version 1.0
     * @see JedisPubSub
     * @since 2015年9月19日
     */
    protected class RedisMasterPubSub extends JedisPubSub {

        /**
         * masterName
         */
        protected String masterName;

        /**
         * host
         */
        protected String host;

        /**
         * port
         */
        protected int port;

        /**
         * <p>Title: RedisMasterListener</p>
         * <p>Description: 默认构造方法</p>
         */
        protected RedisMasterPubSub() {
        }

        /**
         * <p>Title: RedisMasterListener</p>
         * <p>Description: 构造方法</p>
         *
         * @param masterName 主机名称
         * @param host       地址
         * @param port       端口
         */
        public RedisMasterPubSub(String masterName, String host, int port) {
            this.masterName = masterName;
            this.host = host;
            this.port = port;
        }

        @Override
        public void onMessage(String channel, String message) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
