package com.mercadolibre.pf_be_java_hisp_w20_g07.repository;

import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Batch;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.Product;
import com.mercadolibre.pf_be_java_hisp_w20_g07.entity.PurchaseOrderHasProduct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository{

    List<Product> products;

    public ProductRepository(){
        this.products = new ArrayList<>();
        products.add(new Product(1, "Banano", 1500.0, new ArrayList<Batch>(), new ArrayList<PurchaseOrderHasProduct>()));
        products.add(new Product(2, "Yogurt", 4000.0, new ArrayList<Batch>(), new ArrayList<PurchaseOrderHasProduct>()));
        products.add(new Product(3, "Pescado", 2000.0, new ArrayList<Batch>(), new ArrayList<PurchaseOrderHasProduct>()));
    }

    @Override
    public Product findById(int id){
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public  boolean save(Product product){
        products.add(product);
        return true;
    }

}
