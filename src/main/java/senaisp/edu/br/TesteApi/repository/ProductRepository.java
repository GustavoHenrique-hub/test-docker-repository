package senaisp.edu.br.TesteApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import senaisp.edu.br.TesteApi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	@Query(value = "SELECT * FROM TB_PRODUCT ORDER BY id", nativeQuery = true)
    List<Product> findAllProductsOrderedById();
}
