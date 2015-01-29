/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cart;

import entity.Product;

/**
 *
 * @author brettfry
 */
public class CartItems {

    Product product;
    short quantity;
    
    //Contructor for CartItems
    public CartItems(Product product) {
        this.product = product;
        quantity = 1;
    }
    //method (accessor method) that returns product
    public Product getProduct() {
        return product;
    }
    //method (accessor method) that returns quanity
    public short getQuantity() {
        return quantity;
    }
    //is a void method (mutator method) that allows for the driver to change the quantity 
    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }
    //is a void method (mutator method) that allows for the driver to increment the quantity 
    public void incrementQuantity() {
        quantity++;
    }
    //is a void method (mutator method) that allows for the driver to decrement the quantity 
    public void decrementQuantity() {
        quantity--;
    }
    //method (accessor method) that returns Total after calculation of value of all items in cart
    public double getTotal() {
        double amount = 0;
        amount = (this.getQuantity() * product.getPrice().doubleValue());
        return amount;
    }

}
