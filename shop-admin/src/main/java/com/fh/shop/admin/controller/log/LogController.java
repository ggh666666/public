package com.fh.shop.admin.controller.log;

import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.param.log.LogSearchParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/log")
public class LogController {

    @Resource(name = "logService")
    private ILogService logService;

    /**
     * 跳转到查询日志页面
     *
     * @return
     */
    @RequestMapping("/toList")
    private String toList() {
        return "/log/logList";
    }

    /**
     * 查询日志信息 /  条件查询
     *
     * @return
     */
    @RequestMapping("/findLogList")
    @ResponseBody
    private DataTableResult findLogList(LogSearchParam logSearchParam) {
        DataTableResult dataTableResult = logService.findLogList(logSearchParam);
        return dataTableResult;
    }
}
