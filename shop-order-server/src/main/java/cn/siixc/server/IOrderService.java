package cn.siixc.server;

import cn.siixc.domain.Order;

/**
 * @author siixc
 * @date 2023/6/5 14:37
 */
public interface IOrderService {
    Order createOrder (long productId ,Long userId);
}
