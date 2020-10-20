package com.atguigu.mybatis_plus;

import com.atguigu.mybatis_plus.entity.Product;
import com.atguigu.mybatis_plus.entity.User;
import com.atguigu.mybatis_plus.mapper.ProductMapper;
import com.atguigu.mybatis_plus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class CRUDTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("Helen");
        user.setAge(18);
        user.setEmail("55317332@qq.com");
        int result = userMapper.insert(user);

        System.out.println("影响的行数：" + result); //影响的行数

        System.out.println("id：" + user); //id自动回填
    }

    @Test
    public void testUpdateById(){
        User user = new User();
        user.setId(1318023392602955778L);
        user.setAge(28);
        user.setName("Annie");
        int result = userMapper.updateById(user);
        System.out.println("影响的行数：" + result);
    }

    @Test  //丢失更新冲突模拟
    public void testConcurrentUpdate(){
        //1、小李获取数据
        Product p1 = productMapper.selectById(1l);
        System.out.println("小李取出的价格："+p1.getPrice());

        //2、小王获取数据
        Product p2 = productMapper.selectById(1l);
        System.out.println("小王取出的价格："+p2.getPrice());

        //3、小李加了50。存入数据库
        p1.setPrice(p1.getPrice()+50);
        productMapper.updateById(p1);

        //4、小王减了30元，存入数据库
        p2.setPrice(p2.getPrice()-30);
        int result = productMapper.updateById(p2);
        if(result == 0){
            System.out.println("小王更新失败！");
            //发起重试
            p2 = productMapper.selectById(1l);
            p2.setPrice(p2.getPrice() -30);
            productMapper.updateById(p2);
        }
        //最后的结果
        //用户看到的商品价格
        Product p3 = productMapper.selectById(1l);
        System.out.println("最后的结果："+p3.getPrice());
    }


    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Tom");
        map.put("age", 28);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1,5);
        Page<User> pageParam = userMapper.selectPage(page, null);
        pageParam.getRecords().forEach(System.out::println);
        System.out.println(pageParam.getCurrent());  //获取当前页码
        System.out.println(pageParam.getPages());  //获取总页数
        System.out.println(pageParam.getSize());  //每页记录数
        System.out.println(pageParam.getTotal()); //总记录数
        System.out.println(pageParam.hasNext());    //是否有下一页
        System.out.println(pageParam.hasPrevious()); //是否有上一页
    }

    @Test
    public void testSelectMapsPage() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name");

        Page<Map<String,Object>> page = new Page<>(1,5);
        Page<Map<String,Object>> pageParam = userMapper.selectMapsPage(page, queryWrapper);
        pageParam.getRecords().forEach(System.out::println);
        System.out.println(pageParam.getCurrent());  //获取当前页码
        System.out.println(pageParam.getPages());  //获取总页数
        System.out.println(pageParam.getSize());  //每页记录数
        System.out.println(pageParam.getTotal()); //总记录数
        System.out.println(pageParam.hasNext());    //是否有下一页
        System.out.println(pageParam.hasPrevious()); //是否有上一页
    }


    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(5L);
        System.out.println("删除了"+result+"行");
    }


    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(2, 3, 4));
        System.out.println(result);
    }


    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }


    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }

}
