package org.darkphoenixs.pool.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.darkphoenixs.pool.ConnectionPool;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>Title: KafkaSharedConnPool</p>
 * <p>Description: Kafka共享连接池（单例）</p>
 *
 * @author Victor.Zxy
 * @version 1.2.3
 * @see ConnectionPool
 * @since 2016 /8/23
 */
public class KafkaSharedConnPool implements ConnectionPool<Producer<byte[], byte[]>> {

    private static final AtomicReference<KafkaSharedConnPool> pool = new AtomicReference<KafkaSharedConnPool>();

    private final Producer<byte[], byte[]> producer;

    private KafkaSharedConnPool(Properties properties) {
        this.producer = new KafkaProducer<byte[], byte[]>(properties);
    }

    /**
     * Gets instance.
     *
     * @param brokers the brokers
     * @param codec   the codec
     * @param keySer  the key ser
     * @param valSer  the val ser
     * @return the instance
     */
    public synchronized static KafkaSharedConnPool getInstance(final String brokers, final String codec, final String keySer, final String valSer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets instance.
     *
     * @param properties the properties
     * @return the instance
     */
    public synchronized static KafkaSharedConnPool getInstance(final Properties properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Producer<byte[], byte[]> getConnection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void returnConnection(Producer<byte[], byte[]> conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void invalidateConnection(Producer<byte[], byte[]> conn) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Close.
     */
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
