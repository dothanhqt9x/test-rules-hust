package vn.edu.hust.testrules.testruleshust.api.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hust.testrules.testruleshust.entity.ProductEntity;
import vn.edu.hust.testrules.testruleshust.service.ProductService;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final ProductService productService;

    @GetMapping("/api/sample")
    public ResponseEntity<ProductEntity> getProductById() {
        return ResponseEntity.ok().body(productService.getProductById(1));
    }
}
