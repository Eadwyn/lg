package com.legrand.ftpserver.service;

import com.legrand.common.exception.BusinessException;
import com.legrand.ftpserver.modal.FtpUser;
import com.legrand.ftpserver.shell.LocalShellExecutor;
import com.legrand.ftpserver.shell.RemoteShellExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 项目名称：  legrand-cloud
 * <p>
 * 类名称：    FtpService.java
 * <p>
 * 功能描述： 远程执行里，需要用户为 root用户，否则ftp登录时会报500 OOPS: config file not owned by correct user, or not a file错误<br />
 *            本地执行里，也需要root用户执行该jar包
 * <p>
 * <p>
 *
 * @author Eadwyn
 * <p>
 * 创建时间：  2019/1/2410:56
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
@Service
public class FtpService {
    /* db_load -T -t hash -f /etc/vsftpd/virtusers /etc/vsftpd/virtusers.db */
    private static final String REFRESH_DB_SCRIPT_TEMPLATE = "db_load -T -t hash -f %s %s";
    /** 虚拟用户信息文件virtusers的路径 */
    @Value("${ftp.vsftpd.virtusers-config-dir}")
    private String virtusersConfigDir;
    /** 虚拟用户信息文件virtusers的路径 */
    @Value("${ftp.vsftpd.virtusers-file-path}")
    private String virtusersFilePath;
    /** 虚拟用户数据库文件virtusers.db文件的路径 */
    @Value("${ftp.vsftpd.virtusers-db-file-path}")
    private String virtusersDbFilePath;
    /** 虚拟用户上传文件的根目录 */
    @Value("${ftp.vsftpd.virtusers-home-dir}")
    private String virtusersHomeDir;

    /**
     * 创建一个新的ftp用户
     *
     * @return
     */
    public FtpUser add() {
        FtpUser user = new FtpUser("u" + UUID.randomUUID().toString().replace("-", ""), UUID.randomUUID().toString().replace("-", ""));
        boolean result = this.createUser(user.getUsername(), user.getPassword());
        if (!result) {
            throw new BusinessException("500", "创建用户失败");
        }
        return user;
    }

    /**
     * 删除FTP用户
     *
     * @param user
     * @return
     */
    public boolean delete(FtpUser user) {
        return this.deleteUser(user.getUsername(), user.getPassword());
    }

    private boolean createUser(String username, String password) {
        List<String> commands = new ArrayList<String>();
        /* 添加虚拟用户 */
        /* echo 'username' >> '/etc/vsftpd/virtusers'
        /* echo 'password' >> '/etc/vsftpd/virtusers' */
        commands.add(String.format("echo '%s' >> '%s'", username, this.virtusersFilePath)); // 用户名
        commands.add(String.format("echo '%s' >> '%s'", password, this.virtusersFilePath)); // 密码

        /* 生成用户数据文件 */
        /* db_load -T -t hash -f /etc/vsftpd/virtusers /etc/vsftpd/virtusers.db */
        commands.add(String.format("db_load -T -t hash -f %s %s", this.virtusersFilePath, this.virtusersDbFilePath));

        /* 建立虚拟用户个人配置文件 */
        String configFilePath = String.format("%s/%s", this.virtusersConfigDir, username);
        commands.add(String.format("touch %s", configFilePath));
        /* 虚拟用户配置文件内容：开放上传下载的权限 */
        commands.add(String.format("echo 'local_root=%s' >> '%s'", this.virtusersHomeDir, configFilePath));
        commands.add(String.format("echo 'anon_world_readable_only=NO' >> '%s'", configFilePath));
        commands.add(String.format("echo 'anon_upload_enable=YES' >> '%s'", configFilePath));

//        return new RemoteShellExecutor("172.16.222.136", "nic", "root").executeForBoolean(commands);
        return LocalShellExecutor.getInstance().executeForBoolean(commands);
    }

    private boolean deleteUser(String username, String password) {
        List<String> commands = new ArrayList<String>();
        /* 删除虚拟用户个人配置文件 */
        commands.add(String.format("rm %s/%s", this.virtusersConfigDir, username));

        /* 删除虚拟用户 */
        /* sed -i '/^username$/d' '/etc/vsftpd/virtusers' */
        /* echo 'password' >> '/etc/vsftpd/virtusers' */
        commands.add(String.format("sed -i '/^%s/d' '%s'", username, this.virtusersFilePath)); // 用户名
        commands.add(String.format("sed -i '/^%s/d' '%s'", password, this.virtusersFilePath)); // 密码

        /* 生成用户数据文件 */
        /* db_load -T -t hash -f /etc/vsftpd/virtusers /etc/vsftpd/virtusers.db */
        commands.add(String.format("db_load -T -t hash -f %s %s", this.virtusersFilePath, this.virtusersDbFilePath));

//        return new RemoteShellExecutor("172.16.222.136", "nic", "root").executeForBoolean(commands);
        return LocalShellExecutor.getInstance().executeForBoolean(commands);
    }
}
