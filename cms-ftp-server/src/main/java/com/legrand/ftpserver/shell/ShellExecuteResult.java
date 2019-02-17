package com.legrand.ftpserver.shell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 项目名称：  legrand-cloud
 * <p>
 * 类名称：    ShellExecuteResult.java
 * <p>
 * 功能描述：
 * <p>
 * <p>
 *
 * @author Eadwyn
 * <p>
 * 创建时间：  2019/1/249:14
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
@AllArgsConstructor
public class ShellExecuteResult {
    /** 执行退出代码 */
    private int code;
    /** 提示信息 */
    private String message;
}
