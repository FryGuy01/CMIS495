/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author brettfry
 */
@Entity
@Table(name = "category")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id"),
    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Collection<Product> productCollection;

    // no-arg constructor

    public Category() {
    }

    //Contructor for Category

    public Category(Short id) {
        this.id = id;
    }

    //Contructor for Category

    public Category(Short id, String name) {
        this.id = id;
        this.name = name;
    }

    //method (accessor method) that returns id

    public Short getId() {
        return id;
    }

    //is a void method (mutator method) that allows for the driver to change the id

    public void setId(Short id) {
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

    //method (accessor method) that returns the product collection

    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    //is a void method (mutator method) that allows for the driver to change the product collection

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    //method that returns formatted information

    @Override
    public String toString() {
        return "entity.Category[id=" + id + "]";
    }

}
