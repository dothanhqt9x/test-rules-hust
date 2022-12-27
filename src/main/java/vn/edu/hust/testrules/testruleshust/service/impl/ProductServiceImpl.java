package vn.edu.hust.testrules.testruleshust.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.entity.ProductEntity;
import vn.edu.hust.testrules.testruleshust.repository.ProductRepository;
import vn.edu.hust.testrules.testruleshust.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public ProductEntity getProductById(Integer id) {
    return productRepository.findById(id).get();
  }
}
