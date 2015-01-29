/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Product;
import java.util.*;

/**
 *
 * @author brettfry
 */
public class Cart {

    //declare list to store items
    List<CartItems> items;
    int numberOfItems;
    double total;

    //Contructor for Cart
    public Cart() {
        items = new ArrayList<CartItems>();
        numberOfItems = 0;
        total = 0;
    }

    /*
     * Adds a CartItems to the Cart's items list. If item of a specified product
     * already exists in shopping cart list, the quantity of that item is incremented.
     */
    public synchronized void addItem(Product product) {

        boolean newItem = true;

        for (CartItems scItem : items) {

            if (scItem.getProduct().getId() == product.getId()) {

                newItem = false;
                scItem.incrementQuantity();
            }
        }

        if (newItem) {
            CartItems scItem = new CartItems(product);
            items.add(scItem);
        }
    }

    /**
     * Updates the CartItems of a specified product to the specified quantity.
     * If 0 is the given quantity, the CartItems is/are removed from the Cart's
     * items list.
     *
     * @param product
     * @param quantity
     */
    public synchronized void update(Product product, String quantity) {

        short qty = -1;

        // cast quantity as short
        qty = Short.parseShort(quantity);

        if (qty >= 0) {

            CartItems item = null;

            for (CartItems scItem : items) {

                if (scItem.getProduct().getId() == product.getId()) {

                    if (qty != 0) {
                        // set item quantity to new value
                        scItem.setQuantity(qty);
                    } else {
                        // if quantity equals 0, save item and break
                        item = scItem;
                        break;
                    }
                }
            }

            if (item != null) {
                // remove from cart
                items.remove(item);
            }
        }
    }

    /**
     * Returns the list of CartItems.
     *
     * @return
     */
    public synchronized List<CartItems> getItems() {

        return items;
    }

    /**
     * Returns the sum of quantities for all items maintained in cart items
     * list.
     *
     * @return
     */
    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (CartItems scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

    /**
     * Returns the sum of the product price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost excluding the tariff.
     *
     * @return the cost of all items times their quantities
     * @see CartItems
     */
    public synchronized double getSubtotal() {

        double amount = 0;

        for (CartItems scItem : items) {

            Product product = (Product) scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice().doubleValue());
        }

        return amount;
    }

    /**
     * Calculates the total cost of the order/purchase. This method adds the
     * subtotal to the designated tax rate and sets the total instance variable
     * with the result.
     *
     * @param tariff the designated tariff for all orders
     * @see CartItems
     */
    public synchronized void calculateTotal(String tariff) {

        double amount = 0;

        // cast tariff as double
        double s = Double.parseDouble(tariff);

        amount = this.getSubtotal();
        amount += s;

        total = amount;
    }

    /**
     * Returns the total cost of the order for the given Cart instance.
     *
     * @return the cost of all items times their quantities plus tariff
     */
    public synchronized double getTotal() {

        return total;
    }

    /**
     * Empties the shopping cart. All items are removed from the shopping cart
     * items list, numberOfItems and total are reset to '0'.
     *
     * @see CartItems
     */
    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}
