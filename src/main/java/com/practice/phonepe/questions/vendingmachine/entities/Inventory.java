package com.practice.phonepe.questions.vendingmachine.entities;

import com.practice.phonepe.questions.vendingmachine.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Tracks which products the machine carries and how many of each are left. */
public class Inventory {

    private final Map<String, Product> productsByCode = new LinkedHashMap<>();
    private final Map<String, Integer> quantityByCode = new LinkedHashMap<>();

    public void addOrRestock(Product product, int quantity) {
        productsByCode.put(product.getCode(), product);
        quantityByCode.merge(product.getCode(), quantity, Integer::sum);
    }

    public boolean exists(String productCode) {
        return productsByCode.containsKey(productCode);
    }

    public boolean hasStock(String productCode) {
        return quantityByCode.getOrDefault(productCode, 0) > 0;
    }

    public Product getProduct(String productCode) {
        Product product = productsByCode.get(productCode);
        if (product == null) {
            throw new ProductNotFoundException("No such product: " + productCode);
        }
        return product;
    }

    public int getQuantity(String productCode) {
        return quantityByCode.getOrDefault(productCode, 0);
    }

    public void decrement(String productCode) {
        quantityByCode.merge(productCode, -1, Integer::sum);
    }

    public List<Product> listAvailableProducts() {
        List<Product> available = new ArrayList<>();
        for (Product product : productsByCode.values()) {
            if (hasStock(product.getCode())) {
                available.add(product);
            }
        }
        return available;
    }
}
