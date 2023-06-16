package cn.siixc.service.impl;

import cn.siixc.dao.ProductDao;
import cn.siixc.domain.Product;
import cn.siixc.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {
@Autowired
    private ProductDao productDao;
    public Product findByPid(long pid) {
        return productDao.findById(pid).get();
    }
}
