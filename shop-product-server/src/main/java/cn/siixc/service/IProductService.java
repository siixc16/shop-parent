package cn.siixc.service;

import cn.siixc.domain.Product;

public interface IProductService{
    Product findByPid(long pid);

}
