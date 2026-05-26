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

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.darkphoenixs.pool.ConnectionFactory;
import org.darkphoenixs.pool.ConnectionPool;
import org.darkphoenixs.pool.PoolBase;
import org.darkphoenixs.pool.PoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Hashing;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>Title: RedisShardedConnPoolOld</p>
 * <p>Description: Redis分片连接池</p>
 *
 * @author Victor
 * @version 1.0
 * @see PoolBase
 * @see ConnectionPool
 * @since 2015年9月19日
 */
@Deprecated
public class RedisShardedConnPoolOld extends PoolBase<ShardedJedis> implements ConnectionPool<ShardedJedis> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -273019562990831722L;

    /**
     * <p>Title: RedisShardedConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param poolConfig 池配置
     * @param shards     分片列表
     */
    public RedisShardedConnPoolOld(final PoolConfig poolConfig, List<JedisShardInfo> shards) {
        this(poolConfig, shards, Hashing.MURMUR_HASH);
    }

    /**
     * <p>Title: RedisShardedConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param poolConfig 池配置
     * @param shards     分片列表
     * @param algo       数字哈希
     */
    public RedisShardedConnPoolOld(final PoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo) {
        this(poolConfig, shards, algo, null);
    }

    /**
     * <p>Title: RedisShardedConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param poolConfig    池配置
     * @param shards        分片列表
     * @param keyTagPattern 键表达式
     */
    public RedisShardedConnPoolOld(final PoolConfig poolConfig, List<JedisShardInfo> shards, Pattern keyTagPattern) {
        this(poolConfig, shards, Hashing.MURMUR_HASH, keyTagPattern);
    }

    /**
     * <p>Title: RedisShardedConnPoolOld</p>
     * <p>Description: 构造方法</p>
     *
     * @param poolConfig    池配置
     * @param shards        分片列表
     * @param algo          数字哈希
     * @param keyTagPattern 键表达式
     */
    public RedisShardedConnPoolOld(final PoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
        initPool(poolConfig, new RedisShardedConnFactory(shards, algo, keyTagPattern));
    }

    @Override
    public ShardedJedis getConnection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void returnConnection(ShardedJedis conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void invalidateConnection(ShardedJedis conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>Title: RedisShardedConnFactory</p>
     * <p>Description: Redis分片连接工厂</p>
     *
     * @author Victor
     * @version 1.0
     * @see ConnectionFactory
     * @since 2015年9月19日
     */
    protected class RedisShardedConnFactory implements ConnectionFactory<ShardedJedis> {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = -8620587542840002906L;

        /**
         * shards
         */
        private List<JedisShardInfo> shards;

        /**
         * algo
         */
        private Hashing algo;

        /**
         * keyTagPattern
         */
        private Pattern keyTagPattern;

        /**
         * <p>Title: RedisShardedConnFactory</p>
         * <p>Description: 构造方法</p>
         *
         * @param shards        分片列表
         * @param algo          数字哈希
         * @param keyTagPattern 键表达式
         */
        public RedisShardedConnFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
            this.shards = shards;
            this.algo = algo;
            this.keyTagPattern = keyTagPattern;
        }

        @Override
        public PooledObject<ShardedJedis> makeObject() throws Exception {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void destroyObject(PooledObject<ShardedJedis> pooledShardedJedis) throws Exception {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void activateObject(PooledObject<ShardedJedis> p) throws Exception {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void passivateObject(PooledObject<ShardedJedis> p) throws Exception {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public ShardedJedis createConnection() throws Exception {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
