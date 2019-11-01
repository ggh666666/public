package com.fh.shop.admin.biz.log;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.log.ILogMapper;
import com.fh.shop.admin.param.log.LogSearchParam;
import com.fh.shop.admin.po.log.LogInfo;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.vo.log.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("logService")
public class ILogServiceImpl implements ILogService {
    @Autowired
    private ILogMapper logMapper;


    @Override
    public void addLog(LogInfo logInfo) {
        logMapper.addLog(logInfo);
    }

    @Override
    public DataTableResult findLogList(LogSearchParam logSearchParam) {
        //查询总条数
        Long count = logMapper.findLogByCount(logSearchParam);
        //查询当前页数据
        List<LogInfo> logList = logMapper.findLogList(logSearchParam);
        //po转换vo
        List<LogVo> logVoList = buildLogVo(logList);
        DataTableResult dataTableResult = new DataTableResult(logSearchParam.getDraw(), count, count, logVoList);
        return dataTableResult;
    }

    private List<LogVo> buildLogVo(List<LogInfo> logList) {
        List<LogVo> logVoList = new ArrayList<>();
        for (LogInfo logInfo : logList) {
            LogVo logVo = new LogVo();
            logVo.setId(logInfo.getId());
            logVo.setUserName(logInfo.getUserName());
            logVo.setRealName(logInfo.getRealName());
            logVo.setCreateDate(DateUtil.date2str(logInfo.getCreateDate(), DateUtil.FULL_YEAR));
            logVo.setInfo(logInfo.getInfo());
            logVo.setErrorMsg(logInfo.getErrorMsg());
            logVo.setStatus(logInfo.getStatus());
            logVo.setDetail(logInfo.getDetail());
            logVo.setContent(logInfo.getContent());
            logVoList.add(logVo);
        }
        return logVoList;
    }
}
