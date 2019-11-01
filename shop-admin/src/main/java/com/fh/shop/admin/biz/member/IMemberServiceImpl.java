package com.fh.shop.admin.biz.member;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.member.IMemberMapper;
import com.fh.shop.admin.param.member.MemberSearchParam;
import com.fh.shop.admin.po.member.Member;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class IMemberServiceImpl implements IMemberService {

    @Autowired
    private IMemberMapper memberMapper;

    @Override
    public DataTableResult list(MemberSearchParam param) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        String username = param.getUsername();
        if (!StringUtils.isEmpty(username))
            memberQueryWrapper.like("username", username);
        String realName = param.getRealName();
        if (!StringUtils.isEmpty(realName))
            memberQueryWrapper.like("realName", realName);
        String mobile = param.getMobile();
        if (!StringUtils.isEmpty(mobile))
            memberQueryWrapper.like("mobile", mobile);
        int area1 = param.getArea1();
        if (area1 > 0)
            memberQueryWrapper.eq("area1", area1);
        int area2 = param.getArea2();
        if (area2 > 0)
            memberQueryWrapper.eq("area2", area2);
        int area3 = param.getArea3();
        if (area3 > 0)
            memberQueryWrapper.eq("area3", area3);
        int area4 = param.getArea4();
        if (area4 > 0)
            memberQueryWrapper.eq("area4", area4);
        //
        Integer start = param.getStart();
        Integer length = param.getLength();
        Page<Member> page = new Page<>((start / length) + 1, length);//页数从1开始 记得+1
        page.setSearchCount(true);
        IPage<Member> page1 = memberMapper.selectPage(page, memberQueryWrapper);

        long total = page1.getTotal();
        DataTableResult dataTableResult = new DataTableResult(param.getDraw(), total, total, page1.getRecords());
        return dataTableResult;
    }
}
