package senaisp.edu.br.TesteApi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import senaisp.edu.br.TesteApi.model.Product;
import senaisp.edu.br.TesteApi.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductRepository repository;
	
	@GetMapping("/listAll")
	public ResponseEntity<List<Product>> listAll(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Optional<Product>> findById(@PathVariable long id){
		return ResponseEntity.ok(repository.findById(id));
	}
	
	@PostMapping("/insert")
	public @Validated ResponseEntity<Product> insertNewValue(@RequestBody Product prod){
		return ResponseEntity.ok(repository.save(prod));
	}
	
	@PutMapping("/alter/{id}")
	public ResponseEntity<Product> alterValue(@RequestBody Product prod, @PathVariable long id){
		Optional<Product> prodData = repository.findById(id);
		
		if (prodData.isPresent()) {
			Product product = prodData.get();
			product.setName(prod.getName());
			product.setQuantity(prod.getQuantity());
			product.setValue(prod.getValue());
			
			return new ResponseEntity<>(repository.save(product), HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, String>> deleteById(@PathVariable long id){
		Map<String, String> response = new HashMap<>();
		
		try {
			repository.deleteById(id);
			response.put("message", "Deletedado com Sucesso!!");
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e){
			response.put("error", "ERRO: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
