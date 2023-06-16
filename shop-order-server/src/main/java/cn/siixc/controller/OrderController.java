package cn.siixc.controller;

import cn.siixc.domain.Order;
import cn.siixc.server.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author siixc
 * @date 2023/6/5 16:36
 */
@RestController
@Slf4j
public class OrderController {
    @Autowired
    private IOrderService orderService;
    @RequestMapping("/save")
    public Order order (Long pid, Long uid){
        return orderService.createOrder(pid,uid);
    }
}
