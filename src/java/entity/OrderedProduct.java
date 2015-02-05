/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author brettfry
 */
@Entity
@Table(name = "ordered_product")
@NamedQueries({
    @NamedQuery(name = "OrderedProduct.findAll", query = "SELECT o FROM OrderedProduct o"),
    @NamedQuery(name = "OrderedProduct.findByCustomerOrderId", query = "SELECT o FROM OrderedProduct o WHERE o.oProductPK.customerOrderId = :customerOrderId"),
    @NamedQuery(name = "OrderedProduct.findByProductId", query = "SELECT o FROM OrderedProduct o WHERE o.oProductPK.productId = :productId"),
    @NamedQuery(name = "OrderedProduct.findByQuantity", query = "SELECT o FROM OrderedProduct o WHERE o.quantity = :quantity")})
public class OrderedProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedProductPK oProductPK;
    @Basic(optional = false)
    @Column(name = "quantity")
    private short quantity;
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
    @JoinColumn(name = "customer_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;

    // no-arg constructor
    public OrderedProduct() {
    }

    //Contructor for OrderedProduct
    public OrderedProduct(OrderedProductPK oProductPK) {
        this.oProductPK = oProductPK;
    }

    //Contructor for OrderedProduct
    public OrderedProduct(OrderedProductPK oProductPK, short quantity) {
        this.oProductPK = oProductPK;
        this.quantity = quantity;
    }

    //Contructor for OrderedProduct
    public OrderedProduct(int customerOrderId, int productId) {
        this.oProductPK = new OrderedProductPK(customerOrderId, productId);
    }

    //method (accessor method) that returns ordered Product
    public OrderedProductPK getOPPK() {
        return oProductPK;
    }

    //is a void method (mutator method) that allows for the driver to change the orderedProduct
    public void setOrderedProductPK(OrderedProductPK oProductPK) {
        this.oProductPK = oProductPK;
    }

    //method (accessor method) that returns quantity 
    public short getQuantity() {
        return quantity;
    }

    //is a void method (mutator method) that allows for the driver to change the quantity
    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    //method (accessor method) that returns Product object
    public Product getProduct() {
        return product;
    }

    //is a void method (mutator method) that allows for the driver to change the Product object
    public void setProduct(Product product) {
        this.product = product;
    }

    //method (accessor method) that returns customerOrder object
    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    //is a void method (mutator method) that allows for the driver to change the CustomerOrder object
    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oProductPK != null ? oProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof OrderedProduct)) {
            return false;
        }
        OrderedProduct other = (OrderedProduct) object;
        return (this.oProductPK != null || other.oProductPK == null) && (this.oProductPK == null || this.oProductPK.equals(other.oProductPK));
    }

    //method that returns formatted information
    @Override
    public String toString() {
        return "entity.OrderedProduct[oProductPK=" + oProductPK + "]";
    }

}
