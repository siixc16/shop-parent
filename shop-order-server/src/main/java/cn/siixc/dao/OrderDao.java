package cn.siixc.dao;

import cn.siixc.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author siixc
 * {@code @date} 2023/6/5 14:34
 */
public interface OrderDao extends JpaRepository<Order,Long> {
}
