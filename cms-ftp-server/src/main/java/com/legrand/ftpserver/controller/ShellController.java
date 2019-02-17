package com.legrand.ftpserver.controller;

import com.legrand.ftpserver.shell.LocalShellExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目名称：  legrand-cloud
 * <p>
 * 类名称：    ShellController.java
 * <p>
 * 功能描述：
 * <p>
 * <p>
 *
 * @author Eadwyn
 * <p>
 * 创建时间：  2019/1/2410:25
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
@RestController
@RequestMapping("/shell")
public class ShellController {

    @GetMapping("/execute")
    public Object execute(@RequestParam String cmd) {
        return LocalShellExecutor.getInstance().execute(cmd);
    }
}
