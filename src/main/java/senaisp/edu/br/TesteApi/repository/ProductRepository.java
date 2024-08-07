package senaisp.edu.br.TesteApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senaisp.edu.br.TesteApi.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
