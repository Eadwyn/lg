package com.legrand.ftpserver.shell;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.concurrent.*;

/**
 * 项目名称：  legrand-cloud
 * <p>
 * 类名称：    RemoteShellExecutor.java
 * <p>
 * 功能描述：
 * <p>
 * <p>
 *
 * @author Eadwyn
 * <p>
 * 创建时间：  2019/1/2415:55
 * <p>
 * 修改人：
 * <p>
 * 修改时间：
 * <p>
 * 修改备注：
 * <p>
 * <p>
 * 版本号：    V1.0.0
 * <p>
 * 公司：      深圳视得安罗格朗电子有限公司
 */
public class RemoteShellExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RemoteShellExecutor.class);
    /** 线程池 */
    private static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    /** 错误代码 */
    private static final int ERROR_CODE = -1;
    /** 默认超时时间。单位：毫秒 */
    private static final int DEFAULT_TIMEOUT = 10000;

    private Connection conn;
    /** 远程机器IP */
    private String host;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;

    public RemoteShellExecutor(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    private boolean login() throws IOException {
        this.conn = new Connection(host);
        this.conn.connect();
        return this.conn.authenticateWithPassword(username, password);
    }

    /**
     * 执行shell脚本。采用默认超时时间3000ms
     *
     * @param command
     * @return
     */
    public ShellExecuteResult execute(String command) {
        return this.execute(command, DEFAULT_TIMEOUT);
    }

    /**
     * 执行shell脚本
     *
     * @param command shell脚本
     * @param timeout 超时时间。单位：毫秒
     * @return
     */
    public ShellExecuteResult execute(String command, long timeout) {
        if (null == command || 0 == command.trim().length()) {
            throw new IllegalArgumentException("command could not be null or empty");
        }
        InputStream outputInputStream = null; // 正常流
        InputStream errorInputStream = null; // 错误流
        StreamGobbler outputGobbler = null; // 执行结果信息
        StreamGobbler errorGobbler = null; // 错误信息
        Future<Integer> tasks = null;
        try {
            logger.info("begin to execute shell [" + command + "]");
            if (!login()) {
                String message = String.format("登录远程机器失败。host[%s], username[%s]", this.host, this.username);
                logger.error("execute shell failed [" + command + "] " + message);
                return new ShellExecuteResult(ERROR_CODE, message);
            }

            // Open a new {@link Session} on this connection
            final Session session = conn.openSession();
            session.execCommand(command);

            outputInputStream = session.getStdout();
            outputGobbler = new StreamGobbler(outputInputStream, "OUTPUT");
            outputGobbler.start();

            errorInputStream = session.getStderr();
            errorGobbler = new StreamGobbler(errorInputStream, "ERROR");
            errorGobbler.start();

            Callable<Integer> call = new Callable<Integer>() {
                public Integer call() throws Exception {
                    session.waitForCondition(ChannelCondition.EXIT_STATUS, DEFAULT_TIMEOUT);
                    return session.getExitStatus();
                }
            };

            tasks = pool.submit(call);
            int code = tasks.get(timeout, TimeUnit.MILLISECONDS);
            if (0 == code) {
                logger.info("execute shell successed [" + command + "]");
            } else {
                logger.error("execute shell failed [" + command + "]" + " " + errorGobbler.getContent());
            }
            return new ShellExecuteResult(code, outputGobbler.getContent());
        } catch (Exception ex) {
            logger.error("execute shell failed [" + command + "]", ex);
            return (null == errorGobbler) ? new ShellExecuteResult(ERROR_CODE, null) : new ShellExecuteResult(ERROR_CODE, errorGobbler.getContent());
        } finally {
            if (null != tasks) {
                try {
                    tasks.cancel(true);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
            if (null != outputInputStream) {
                this.close(outputInputStream);
                if (null != outputGobbler && !outputGobbler.isInterrupted()) {
                    outputGobbler.interrupt();
                }
            }
            if (null != errorInputStream) {
                this.close(errorInputStream);
                if (null != errorGobbler && !errorGobbler.isInterrupted()) {
                    errorGobbler.interrupt();
                }
            }
            if (null != this.conn) {
                this.conn.close();
            }
        }
    }

    /**
     * 执行多条shell脚本。采用默认超时时间3000ms
     *
     * @param commands
     * @return
     */
    public ShellExecuteResult execute(Collection<String> commands) {
        return this.execute(commands, DEFAULT_TIMEOUT);
    }

    /**
     * 执行多条shell脚本
     *
     * @param commands shell脚本
     * @param timeout  超时时间。单位：毫秒
     * @return
     */
    public ShellExecuteResult execute(Collection<String> commands, long timeout) {
        if (null == commands || 0 == commands.size()) {
            throw new IllegalArgumentException("command could not be null or empty");
        }
        StringBuffer sb = new StringBuffer();
        for (String cmd : commands) {
            if (null == cmd && 0 == cmd.trim().length()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(" ; " + cmd);
            } else {
                sb.append(cmd);
            }
        }
        if (0 == sb.length()) {
            throw new IllegalArgumentException("command could not be null or empty");
        }

        return this.execute(sb.toString(), timeout);
    }

    /**
     * 执行shell脚本。采用默认超时时间3000ms
     *
     * @param command
     * @return
     */
    public boolean executeForBoolean(String command) {
        return this.executeForBoolean(command);
    }

    /**
     * 执行shell脚本
     *
     * @param command shell脚本
     * @param timeout 超时时间。单位：毫秒
     * @return
     */
    public boolean executeForBoolean(String command, long timeout) {
        ShellExecuteResult result = this.execute(command, timeout);
        return 0 == result.getCode() ? true : false;
    }

    /**
     * 执行多条shell脚本。采用默认超时时间3000ms
     *
     * @param commands
     * @return
     */
    public boolean executeForBoolean(Collection<String> commands) {
        return this.executeForBoolean(commands, DEFAULT_TIMEOUT);
    }

    /**
     * 执行多条shell脚本
     *
     * @param commands shell脚本
     * @param timeout  超时时间。单位：毫秒
     * @return
     */
    public boolean executeForBoolean(Collection<String> commands, long timeout) {
        ShellExecuteResult result = this.execute(commands, timeout);
        return 0 == result.getCode() ? true : false;
    }

    private void close(Closeable c) {
        try {
            if (null != c) {
                c.close();
            }
        } catch (IOException e) {
            logger.error("exception", e);
        }
    }
}
