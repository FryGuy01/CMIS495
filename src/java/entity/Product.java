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
@Table(name = "product")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price"),
    @NamedQuery(name = "Product.findByLastUpdate", query = "SELECT p FROM Product p WHERE p.lastUpdate = :lastUpdate")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;
    @Basic(optional = false)
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Category category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Collection<OrderedProduct> opc;

    // no-arg constructor

    public Product() {
    }

    //Contructor for Product

    public Product(Integer id) {
        this.id = id;
    }

    //Contructor for Product

    public Product(Integer id, String name, BigDecimal price, Date lastUpdate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    //method (accessor method) that returns id

    public Integer getId() {
        return id;
    }

    //is a void method (mutator method) that allows for the driver to change the id

    public void setId(Integer id) {
        this.id = id;
    }

    //method (accessor method) that returns name

    public String getName() {
        return name;
    }

    //is a void method (mutator method) that allows for the driver to change the name

    public void setName(String name) {
        this.name = name;
    }

    //method (accessor method) that returns price

    public BigDecimal getPrice() {
        return price;
    }

    //is a void method (mutator method) that allows for the driver to change the price

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //method (accessor method) that returns date last updated

    public Date getLastUpdate() {
        return lastUpdate;
    }

    //is a void method (mutator method) that allows for the driver to change the date last updated

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    //method (accessor method) that returns a Category Object

    public Category getCategory() {
        return category;
    }

    //is a void method (mutator method) that allows for the driver to change the Category

    public void setCategory(Category category) {
        this.category = category;
    }

    //method (accessor method) that returns a Ordered Product Collection

    public Collection<OrderedProduct> getOPC() {
        return opc;
    }

    //is a void method (mutator method) that allows for the driver to change the Ordered Product Collection

    public void setopc(Collection<OrderedProduct> opc) {
        this.opc = opc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    //method that returns formatted information

    @Override
    public String toString() {
        return "entity.Product[id=" + id + "]";
    }

}
