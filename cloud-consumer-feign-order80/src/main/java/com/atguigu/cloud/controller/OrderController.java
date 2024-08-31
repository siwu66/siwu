package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * ClassName:OrderController
 * Package:com.atguigu.cloud.controller
 * Description（介绍）:
 *
 * @Author 火土金
 * @Create 2024/8/22 10:24
 * @Version 1.2
 */
@RestController
public class OrderController {
    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping (value = "/feign/pay/add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){
        System.out.println("第一步：模拟本地addOrder新增订单成功(省略sql操作)，第二步：再开启addPay支付微服务远程调用");
        ResultData resultData = payFeignApi.addPay(payDTO);
        return resultData;
    }

//    @GetMapping(value = ("/feign/pay/get/{id}"))
//    public ResultData getPay(@PathVariable("id") Integer id){
//        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
//        return payFeignApi.getPayInfo(id);
//    }

    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id) {
        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;
        try {
            System.out.println("调用开始-----:" + DateUtil.now());
            resultData = payFeignApi.getPayInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-----:" + DateUtil.now());
            ResultData.fail(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }
        return resultData;
    }
    @GetMapping(value = "/feign/pay/get/info")
    public String getPayInfo(){
        String mylb = payFeignApi.mylb();
        return mylb;
    }
}
