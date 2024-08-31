package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Pay;

import java.util.List;

/**
 * ClassName:PayService
 * Package:com.atguigu.cloud.service
 * Description（介绍）:
 *
 * @Author 火土金
 * @Create 2024/7/28 13:53
 * @Version 1.2
 */
public interface PayService {
    public int add(Pay pay);
    public int delete(Integer id);
    public int update(Pay pay);
    public Pay getById(Integer id);
    public List<Pay> getAll();
}
