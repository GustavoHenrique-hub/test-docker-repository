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
	
	@GetMapping("/findAll")
	public ResponseEntity<List<Product>> listAll(){
		return ResponseEntity.ok(repository.findAllProductsOrderedById());
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Optional<Product>> findById(@PathVariable long id){
		return ResponseEntity.ok(repository.findById(id));
	}
	
	@PostMapping("/insert")
	public @Validated ResponseEntity<Map<String, String>> insertNewValue(@RequestBody Product prod){
		Map<String, String> response = new HashMap<>();
		
		try {
			repository.save(prod);
			response.put("message", "Inserido com sucesso!!");
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e){
			response.put("error", "Produto não inserido!!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, String>> alterValue(@RequestBody Product prod, @PathVariable long id){
		Map<String, String> response = new HashMap<>();
		Optional<Product> prodData = repository.findById(id);
		
		try {
			if (prodData.isPresent()) {
				Product product = prodData.get();
				product.setName(prod.getName());
				product.setQuantity(prod.getQuantity());
				product.setPrice(prod.getPrice());
				new ResponseEntity<>(repository.save(product), HttpStatus.OK);
				
				//MENSAGEM DE RESPOSTA DA API QUANDO DER CÓDIGO 200
				response.put("message", "Alterado com sucesso!!");
			}
			return ResponseEntity.ok(response);
			
		}catch(Exception e) {
			//MENSAGEM DE RESPOSTA DA API QUANDO DER QUALQUER OUTRO CÓDIGO 
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
