/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author brettfry
 */
@Entity
@Table(name = "customer_order")
@NamedQueries({
    @NamedQuery(name = "CustomerOrder.findAll", query = "SELECT c FROM CustomerOrder c"),
    @NamedQuery(name = "CustomerOrder.findById", query = "SELECT c FROM CustomerOrder c WHERE c.id = :id"),
    @NamedQuery(name = "CustomerOrder.findByCustomer", query = "SELECT c FROM CustomerOrder c WHERE c.customer = :customer"), // manually created
    @NamedQuery(name = "CustomerOrder.findByAmount", query = "SELECT c FROM CustomerOrder c WHERE c.amount = :amount"),
    @NamedQuery(name = "CustomerOrder.findByDateCreated", query = "SELECT c FROM CustomerOrder c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "CustomerOrder.findByConfirmationNumber", query = "SELECT c FROM CustomerOrder c WHERE c.confirmationNumber = :confirmationNumber")})
public class CustomerOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @Column(name = "confirmation_number")
    private int confirmationNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerOrder")
    private Collection<OrderedProduct> opc;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Customer customer;

    // no-arg constructor
    public CustomerOrder() {
    }

    //Contructor for CustomerOrder
    public CustomerOrder(Integer id) {
        this.id = id;
    }

    //Contructor for CustomerOrder
    public CustomerOrder(Integer id, BigDecimal amount, Date dateCreated, int confirmationNumber) {
        this.id = id;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.confirmationNumber = confirmationNumber;
    }

    //method (accessor method) that returns id
    public Integer getId() {
        return id;
    }

    //is a void method (mutator method) that allows for the driver to change the quantity
    public void setId(Integer id) {
        this.id = id;
    }

    //method (accessor method) that returns dollar amount
    public BigDecimal getAmount() {
        return amount;
    }

    //is a void method (mutator method) that allows for the driver to change the dollar amount
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    //method (accessor method) that returns dateCreated
    public Date getDateCreated() {
        return dateCreated;
    }

    //is a void method (mutator method) that allows for the driver to change the dateCreated
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    //method (accessor method) that returns confirmation number
    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    //is a void method (mutator method) that allows for the driver to change the confirmation number
    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    //method (accessor method) that returns ordered Product Collection
    public Collection<OrderedProduct> getOPC() {
        return opc;
    }

    //is a void method (mutator method) that allows for the driver to change the ordered Product Collection
    public void setopc(Collection<OrderedProduct> opc) {
        this.opc = opc;
    }

    //method (accessor method) that returns Customer object
    public Customer getCustomer() {
        return customer;
    }

    //is a void method (mutator method) that allows for the driver to change the Customer object
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //method that returns formatted information
    @Override
    public String toString() {
        return "entity.CustomerOrder[id=" + id + "]";
    }

}
