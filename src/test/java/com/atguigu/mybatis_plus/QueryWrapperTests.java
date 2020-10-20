package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.entity.User;
import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueryWrapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("age", 18)
                    .isNotNull("email")
                    .isNotNull("name");
        int result = userMapper.delete(queryWrapper);
        System.out.println("删除了"+result+"行");

    }

}
