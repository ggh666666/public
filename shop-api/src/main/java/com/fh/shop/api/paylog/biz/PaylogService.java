package com.fh.shop.api.paylog.biz;

import com.fh.shop.api.paylog.mapper.IPaylogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("payLogService")
public class PaylogService {
    @Autowired
    private IPaylogMapper paylogMapper;
}
