package com.shanhh.av.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 封装的HTTPComponent, 不依赖于任何框架
 *
 * @author jack.zhang
 */
public class CustomHttpComponent {

    private static final int MAX_TOTAL = 200;

    private static final int DEFAULT_TIMEOUT = 2000;

    private HttpClient httpClient;

    private static CustomHttpComponent instance = new CustomHttpComponent();
    public static CustomHttpComponent getInstance() {
        return instance;
    }


    /**
     * Instantiates a new Custom http component.
     * 单个站点最大允许连接:200
     * 单个站点最大允许连接数:200
     * 默认连接超时时间:200ms
     * 默认数据接收超时时间:200ms
     */
    private CustomHttpComponent() {
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(MAX_TOTAL);
        connectionManager.setMaxTotal(MAX_TOTAL);
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_TIMEOUT);
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_TIMEOUT);
        httpClient = new DefaultHttpClient(connectionManager, httpParams);
    }

    /**
     * Instantiates a new Custom http component.
     *
     * @param maxPerRoute    单个站点最大允许连接
     * @param maxTotal       单个站点最大允许连接数
     * @param connTimeout    连接超时时间
     * @param soTimeout      数据接收超时时间
     * @param staleConnCheck 是否进行陈旧连接检查, 如果不开启, 则启动陈旧连接关闭线程
     */
    private CustomHttpComponent(int maxPerRoute, int maxTotal, int connTimeout, int soTimeout, boolean staleConnCheck) {
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);
        connectionManager.setMaxTotal(maxTotal);
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeout);
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
        httpParams.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, staleConnCheck);
        if (!staleConnCheck) {
            new IdleConnectionMonitorThread(connectionManager).start();
        }
        httpClient = new DefaultHttpClient(connectionManager, httpParams);
    }

    /**
     * Execute t.
     *
     * @param httpHost       the http host
     * @param httpUriRequest the http uri request
     * @param handler        the handler
     * @return the t
     * @throws java.io.IOException the iO exception
     */
    public <T> T execute(HttpHost httpHost, HttpUriRequest httpUriRequest, ResponseHandler<T> handler) throws IOException {
        return httpClient.execute(httpHost, httpUriRequest, handler, new BasicHttpContext());
    }

    /**
     * Execute t.
     *
     * @param httpUriRequest the http uri request
     * @param handler        the handler
     * @return the t
     * @throws java.io.IOException the iO exception
     */
    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<T> handler) throws IOException {
        return httpClient.execute(httpUriRequest, handler, new BasicHttpContext());
    }

    public void shutdown() {
        System.out.println("Connection manager is shutting down");
        httpClient.getConnectionManager().shutdown();
        System.out.println("Connection manager shut down");
    }

    /**
     * The type Idle connection monitor thread.
     *
     * @author jack.zhang
     */
    public class IdleConnectionMonitorThread extends Thread {
        private final ClientConnectionManager connMgr;
        private volatile boolean shutdown;

        /**
         * Instantiates a new Idle connection monitor thread.
         *
         * @param connMgr the conn mgr
         */
        public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                System.out.println("exception occur, " + ex.getMessage());
            }
        }

        /**
         * Shutdown void.
         */
        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }
}


