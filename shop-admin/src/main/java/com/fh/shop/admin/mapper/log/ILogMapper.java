package com.fh.shop.admin.mapper.log;

import com.fh.shop.admin.param.log.LogSearchParam;
import com.fh.shop.admin.po.log.LogInfo;

import java.util.List;

public interface ILogMapper {
    void addLog(LogInfo logInfo);

    Long findLogByCount(LogSearchParam logSearchParam);

    List<LogInfo> findLogList(LogSearchParam logSearchParam);
}
