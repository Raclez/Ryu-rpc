package com.example.demo.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author ryu
 */
public class CuratorUtils {
    Logger logger = LoggerFactory.getLogger(CuratorUtils.class);

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final int sessionTimeoutMs = 60*1000;
    private static final int connectionTimeoutMs = 25000;

    private final CuratorFramework client;
    public CuratorUtils(String url) {

        try {

            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(url)
                    .retryPolicy(new RetryNTimes(1, 1000))
                    .connectionTimeoutMs(connectionTimeoutMs)
                    .sessionTimeoutMs(sessionTimeoutMs);
//            String userInformation = url.getUserInformation();
//            if (userInformation != null && userInformation.length() > 0) {
//                builder = builder.authorization("digest", userInformation.getBytes());
//                builder.aclProvider(new ACLProvider() {
//                    @Override
//                    public List<ACL> getDefaultAcl() {
//                        return ZooDefs.Ids.CREATOR_ALL_ACL;
//                    }
//
//                    @Override
//                    public List<ACL> getAclForPath(String path) {
//                        return ZooDefs.Ids.CREATOR_ALL_ACL;
//                    }
//                });
//            }
            client = builder.build();
            client.getConnectionStateListenable().addListener((client, newState) -> {
                if (newState == ConnectionState.CONNECTED) {
                    logger.info("连接成功！");
                }

            });
            logger.info("连接中......");
            client.start();
            boolean connected = client.blockUntilConnected(connectionTimeoutMs, TimeUnit.MILLISECONDS);

            if (!connected) {
                IllegalStateException illegalStateException = new IllegalStateException("zookeeper not connected, the address is: " + url);

                // 5-1 Failed to connect to configuration center.
                logger.error( "Zookeeper server offline", "",
                        "Failed to connect with zookeeper", illegalStateException);

                throw illegalStateException;
            }

        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public void createPersistent(String path, boolean faultTolerant) {
        try {
            client.create().forPath(path);
        } catch (KeeperException.NodeExistsException e) {
            if (!faultTolerant) {
                logger.warn("", "", "ZNode " + path + " already exists.", e);
                throw new IllegalStateException(e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public void createEphemeral(String path, boolean faultTolerant) {
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (KeeperException.NodeExistsException e) {
            if (faultTolerant) {
                logger.info("ZNode " + path + " already exists, since we will only try to recreate a node on a session expiration" +
                        ", this duplication might be caused by a delete delay from the zk server, which means the old expired session" +
                        " may still holds this ZNode and the server just hasn't got time to do the deletion. In this case, " +
                        "we can just try to delete and create again.");
                deletePath(path);
                createEphemeral(path, true);
            } else {
                logger.warn("", "", "ZNode " + path + " already exists.", e);
                throw new IllegalStateException(e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public void createPersistent(String path, String data, boolean faultTolerant) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.create().forPath(path, dataBytes);
        } catch (KeeperException.NodeExistsException e) {
            if (faultTolerant) {
                logger.info("ZNode " + path + " already exists. Will be override with new data.");
                try {
                    client.setData().forPath(path, dataBytes);
                } catch (Exception e1) {
                    throw new IllegalStateException(e.getMessage(), e1);
                }
            } else {
                logger.warn("", "", "ZNode " + path + " already exists.", e);
                throw new IllegalStateException(e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public void createEphemeral(String path, String data, boolean faultTolerant) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path, dataBytes);
        } catch (KeeperException.NodeExistsException e) {
            if (faultTolerant) {
                logger.info("ZNode " + path + " already exists, since we will only try to recreate a node on a session expiration" +
                        ", this duplication might be caused by a delete delay from the zk server, which means the old expired session" +
                        " may still holds this ZNode and the server just hasn't got time to do the deletion. In this case, " +
                        "we can just try to delete and create again.");
                deletePath(path);
                createEphemeral(path, data, true);
            } else {
                logger.warn("", "", "ZNode " + path + " already exists.", e);
                throw new IllegalStateException(e.getMessage(), e);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void update(String path, String data, int version) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.setData().withVersion(version).forPath(path, dataBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    protected void update(String path, String data) {
        byte[] dataBytes = data.getBytes(CHARSET);
        try {
            client.setData().forPath(path, dataBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public void createOrUpdatePersistent(String path, String data) {
        try {
            if (checkExists(path)) {
                update(path, data);
            } else {
                createPersistent(path, data, true);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

    public void createOrUpdateEphemeral(String path, String data) {
        try {
            if (checkExists(path)) {
                update(path, data);
            } else {
                createEphemeral(path, data, true);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }


    public void createOrUpdatePersistent(String path, String data, Integer version) {
        try {
            if (checkExists(path) && version != null) {
                update(path, data, version);
            } else {
                createPersistent(path, data, false);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    protected void createOrUpdateEphemeral(String path, String data, Integer version) {
        try {
            if (checkExists(path) && version != null) {
                update(path, data, version);
            } else {
                createEphemeral(path, data, false);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void deletePath(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (KeeperException.NoNodeException ignored) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }


    public List<String> getChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            return null;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public boolean checkExists(String path) {
        try {
            if (client.checkExists().forPath(path) != null) {
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean isConnected() {
        return client.getZookeeperClient().isConnected();
    }

    public String doGetContent(String path) {
        try {
            byte[] dataBytes = client.getData().forPath(path);
            return (dataBytes == null || dataBytes.length == 0) ? null : new String(dataBytes, CHARSET);
        } catch (KeeperException.NoNodeException e) {
            // ignore NoNode Exception.
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

//
//    public ConfigItem doGetConfigItem(String path) {
//        String content;
//        Stat stat;
//        try {
//            stat = new Stat();
//            byte[] dataBytes = client.getData().storingStatIn(stat).forPath(path);
//            content = (dataBytes == null || dataBytes.length == 0) ? null : new String(dataBytes, CHARSET);
//        } catch (KeeperException.NoNodeException e) {
//            return new ConfigItem();
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage(), e);
//        }
//        return new ConfigItem(content, stat);
//    }


    public void doClose() {
        client.close();
    }




   public CuratorFramework getClient() {
        return client;
    }

}
