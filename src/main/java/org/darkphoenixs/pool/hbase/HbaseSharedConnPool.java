package org.darkphoenixs.pool.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.darkphoenixs.pool.ConnectionException;
import org.darkphoenixs.pool.ConnectionPool;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>Title: HbaseSharedConnPool</p>
 * <p>Description: Hbase共享连接池（单例）</p>
 *
 * @author Victor.Zxy
 * @version 1.2.3
 * @see ConnectionPool
 * @since 2016 /8/25
 */
public class HbaseSharedConnPool implements ConnectionPool<Connection> {

    private static final AtomicReference<HbaseSharedConnPool> pool = new AtomicReference<HbaseSharedConnPool>();

    private final Connection connection;

    private HbaseSharedConnPool(Configuration configuration) throws IOException {
        this.connection = ConnectionFactory.createConnection(configuration);
    }

    /**
     * Gets instance.
     *
     * @param host    the host
     * @param port    the port
     * @param master  the master
     * @param rootdir the rootdir
     * @return the instance
     */
    public synchronized static HbaseSharedConnPool getInstance(final String host, final String port, final String master, final String rootdir) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets instance.
     *
     * @param properties the properties
     * @return the instance
     */
    public synchronized static HbaseSharedConnPool getInstance(final Properties properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets instance.
     *
     * @param configuration the configuration
     * @return the instance
     */
    public synchronized static HbaseSharedConnPool getInstance(final Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection getConnection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void returnConnection(Connection conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void invalidateConnection(Connection conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Close.
     */
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
