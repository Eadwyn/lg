package com.legrand.ftpserver.controller;

import com.legrand.common.service.model.ResponseInfo;
import com.legrand.ftpserver.modal.FtpUser;
import com.legrand.ftpserver.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目名称：  legrand-cloud
 * <p>
 * 类名称：    FtpController.java
 * <p>
 * 功能描述：
 * <p>
 * <p>
 *
 * @author Eadwyn
 * <p>
 * 创建时间：  2019/1/2415:17
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
@RequestMapping("/ftps")
public class FtpController {
    @Autowired
    private FtpService ftpService;

    @GetMapping("/add")
    public ResponseInfo add() {
        return new ResponseInfo(200, this.ftpService.add());
    }

    @GetMapping("/delete")
    public ResponseInfo delete(@RequestParam String username, @RequestParam String password) {
        FtpUser user = new FtpUser(username, password);
        boolean result = this.ftpService.delete(user);
        return result ? new ResponseInfo(200) : new ResponseInfo(500, "删除失败");
    }
}
