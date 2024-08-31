package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @auther htj
 * @create 2023-11-03 18:55
 */
@RestController
@Tag(name = "支付微服务模块",description = "支付CRUD")
public class PayController {
    @Resource
    private PayService payService;

    @PostMapping("/pay/add")
    public ResultData<String> addPay(@RequestBody Pay pay) {
        System.out.println(pay.toString());
        int i = payService.add(pay);
        return ResultData.success("成功插入记录，返回值："+i);
    }

    @DeleteMapping("pay/del/{id}")
    public ResultData<Integer> deletePay(@PathVariable("id") Integer id) {
        int i = payService.delete(id);
        return ResultData.success(i);
    }

    @PutMapping("/pay/update")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);  //把payDTO复制到pay,payDTO只允许在controller层操作
        int i = payService.update(pay);
        return ResultData.success("成功修改记录，返回值："+i);
    }

    @GetMapping(value = "/pay/get/{id}")
    public ResultData<Pay> getById(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("id不能为负数");
        }
        //测试feign的超时控制时间   测试结果默认为60s
        try {
            TimeUnit.SECONDS.sleep(62);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pay pay = payService.getById(id);
        return ResultData.success(pay);
    }

    @GetMapping("/pay/list")
    public ResultData<List<Pay>> getAll(){
        List<Pay> pays = payService.getAll();
        return ResultData.success(pays);
    }

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/pay/get/info")
    private String getInfoByConsul(@Value("${atguigu.info}") String atguiguInfo)
    {
        return "atguiguInfo: "+atguiguInfo+"\t"+"port: "+port;
    }

}