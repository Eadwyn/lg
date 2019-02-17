package com.legrand.ftpserver.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目名称：  legrand-cloud
 * <p>
 * 类名称：    FtpUser.java
 * <p>
 * 功能描述：
 * <p>
 * <p>
 *
 * @author Eadwyn
 * <p>
 * 创建时间：  2019/1/2415:03
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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FtpUser implements Serializable {
    private String username;
    private String password;
}
