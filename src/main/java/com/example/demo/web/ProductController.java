//package com.example.demo.web;
//
//import com.example.demo.entities.Product;
//import com.example.demo.services.ServiceProduct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//public class ProductContoller {
//    private final ServiceProduct serviceProduct;
//
//    public ProductContoller(ServiceProduct serviceProduct) {
//        this.serviceProduct = serviceProduct;
//    }
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        try{
//            List<Product> products = serviceProduct.findAll();
//            return ResponseEntity.ok(products);
//        } catch(Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getProductById(@PathVariable Long id) {
//        try{
//            return serviceProduct.findById(id)
//                    .map(ResponseEntity::ok)
//                    .orElse(ResponseEntity.notFound().build());
//        } catch(IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<?> searchProductsByName(@RequestParam String name){
//        try{
//            List<Product> products = serviceProduct.findByNameContainingIgnoreCase(name);
//            return ResponseEntity.ok(products);
//        } catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//
//    @GetMapping("/stock/low")
//    public ResponseEntity<List<Product>> getLowStockProducts() {
//        try{
//            List<Product> products = serviceProduct.findLowStockProducts();
//            return ResponseEntity.ok(products);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PatchMapping("/{id}/add-stock")
//    public ResponseEntity<?> addStock(@PathVariable Long id, @RequestParam Integer quantity){
//        try{
//            Product updateProduct = serviceProduct.addStock(id,quantity);
//            return ResponseEntity.ok(updateProduct);
//        } catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout de stock");
//        }
//    }
//
//    @PatchMapping("/{id}/remove-stock")
//    public ResponseEntity<?> removeStock(@PathVariable Long id, @RequestParam Integer quantity){
//        try{
//            Product updateProduct = serviceProduct.removeStock(id,quantity);
//            return ResponseEntity.ok(updateProduct);
//        } catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du retrait de stock");
//        }
//    }
//}
//
package com.example.demo.web;
import com.example.demo.entities.Product;
import com.example.demo.services.ServiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ServiceProduct productService;
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }
    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id).orElse(new Product()));
        return "products/form";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}