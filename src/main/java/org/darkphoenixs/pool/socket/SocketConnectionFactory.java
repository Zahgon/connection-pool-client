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
package org.darkphoenixs.pool.socket;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.darkphoenixs.pool.ConnectionException;
import org.darkphoenixs.pool.ConnectionFactory;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

/**
 * <p>SocketConnectionFactory</p>
 * <p>Socket连接工厂</p>
 *
 * @author Victor.Zxy
 * @see ConnectionFactory
 * @since 1.2.1
 */
class SocketConnectionFactory implements ConnectionFactory<Socket> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -727722488965747494L;

    /**
     * address
     */
    private final InetSocketAddress socketAddress;

    /**
     * receiveBufferSize
     */
    private final int receiveBufferSize;

    /**
     * sendBufferSize
     */
    private final int sendBufferSize;

    /**
     * connectionTimeout
     */
    private final int connectionTimeout;

    /**
     * soTimeout
     */
    private final int soTimeout;

    /**
     * keepAlive
     */
    private final boolean keepAlive;

    /**
     * tcpNoDelay
     */
    private final boolean tcpNoDelay;

    /**
     * performance
     */
    private final String[] performance;

    /**
     * linger
     */
    private int linger;

    /**
     * @param properties 连接属性
     */
    public SocketConnectionFactory(final Properties properties) {
        String address = properties.getProperty(SocketConfig.ADDRESS_PROPERTY);
        if (address == null)
            throw new ConnectionException("[" + SocketConfig.ADDRESS_PROPERTY + "] is required !");
        this.socketAddress = new InetSocketAddress(address.split(":")[0], Integer.parseInt(address.split(":")[1]));
        this.receiveBufferSize = Integer.parseInt(properties.getProperty(SocketConfig.RECE_BUFFERSIZE_PROPERTY, "0"));
        this.sendBufferSize = Integer.parseInt(properties.getProperty(SocketConfig.SEND_BUFFERSIZE_PROPERTY, "0"));
        this.connectionTimeout = Integer.parseInt(properties.getProperty(SocketConfig.CONN_TIMEOUT_PROPERTY, "0"));
        this.soTimeout = Integer.parseInt(properties.getProperty(SocketConfig.SO_TIMEOUT_PROPERTY, "0"));
        this.linger = Integer.parseInt(properties.getProperty(SocketConfig.LINGER_PROPERTY, "0"));
        this.keepAlive = Boolean.valueOf(properties.getProperty(SocketConfig.KEEPALIVE_PROPERTY, "false"));
        this.tcpNoDelay = Boolean.valueOf(properties.getProperty(SocketConfig.TCPNODELAY_PROPERTY, "false"));
        this.performance = (properties.getProperty(SocketConfig.PERFORMANCE_PROPERTY) != null) ? properties.getProperty(SocketConfig.PERFORMANCE_PROPERTY).split(",") : null;
    }

    /**
     * @param host              地址
     * @param port              端口
     * @param receiveBufferSize 接收缓存大小
     * @param sendBufferSize    发送缓存大小
     * @param connectionTimeout 连接超时
     * @param soTimeout         接收超时
     * @param linger            逗留时间
     * @param keepAlive         保持活动
     * @param tcpNoDelay        不延迟
     * @param performance       性能属性
     */
    public SocketConnectionFactory(final String host, final int port, final int receiveBufferSize, final int sendBufferSize, final int connectionTimeout, final int soTimeout, final int linger, final boolean keepAlive, final boolean tcpNoDelay, final String[] performance) {
        this.socketAddress = new InetSocketAddress(host, port);
        this.receiveBufferSize = receiveBufferSize;
        this.sendBufferSize = sendBufferSize;
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.linger = linger;
        this.keepAlive = keepAlive;
        this.tcpNoDelay = tcpNoDelay;
        this.performance = performance;
    }

    @Override
    public PooledObject<Socket> makeObject() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void destroyObject(PooledObject<Socket> p) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean validateObject(PooledObject<Socket> p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void activateObject(PooledObject<Socket> p) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void passivateObject(PooledObject<Socket> p) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Socket createConnection() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
