package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
    //public static final String PaymentSrv_URL = "http://localhost:8001"; // 8001是服务提供者payment8001的端口号
    public static final String PaymentSrv_URL = "http://cloud-payment-service"; // cloud-payment-service是注册的服务名
    @Resource
    private RestTemplate restTemplate; // 注入RestTemplate
    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO) {
        return  restTemplate.postForObject(PaymentSrv_URL + "/pay/add", payDTO, ResultData.class);
    }

    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/"+id, ResultData.class);
    }

    @GetMapping("/consumer/pay/del/{id}") // 删除订单
    public ResultData delOrder(@PathVariable("id") Integer id) { // 通过id删除订单
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/del/" + id, ResultData.class);
    }
    @GetMapping("/consumer/pay/list")
    public ResultData listOrder(){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/list",ResultData.class);
    }
    @GetMapping("/consumer/pay/update")
    public ResultData updateOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/update",payDTO,ResultData.class);
    }

    @GetMapping(value = "/consumer/pay/get/info")
    private String getInfoByConsul()
    {
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");
        //获取所有的服务实例
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }
}
