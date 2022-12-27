package vn.edu.hust.testrules.testruleshust.service;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.entity.ProductEntity;

@Service
public interface ProductService {
  ProductEntity getProductById(Integer id);
}
