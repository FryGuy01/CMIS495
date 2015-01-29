/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author brettfry
 */
@Embeddable
public class OrderedProductPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "customer_order_id")
    private int customerOrderId;
    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;

    // no-arg constructor

    public OrderedProductPK() {
    }

    //Contructor for OrderedProductPK

    public OrderedProductPK(int customerOrderId, int productId) {
        this.customerOrderId = customerOrderId;
        this.productId = productId;
    }

    //method (accessor method) that returns customerOrderId

    public int getCustomerOrderId() {
        return customerOrderId;
    }

    //is a void method (mutator method) that allows for the driver to change the customerOrderId

    public void setCustomerOrderId(int customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    //method (accessor method) that returns productId

    public int getProductId() {
        return productId;
    }

    //is a void method (mutator method) that allows for the driver to change the productId

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customerOrderId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof OrderedProductPK)) {
            return false;
        }
        OrderedProductPK other = (OrderedProductPK) object;
        if (this.customerOrderId != other.customerOrderId) {
            return false;
        }
        return this.productId == other.productId;
    }

    //method that returns formatted information

    @Override
    public String toString() {
        return "entity.OrderedProductPK[customerOrderId=" + customerOrderId + ", productId=" + productId + "]";
    }

}
