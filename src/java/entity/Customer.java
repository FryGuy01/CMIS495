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
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
    @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
    @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city"),
    @NamedQuery(name = "Customer.findByStateRegion", query = "SELECT c FROM Customer c WHERE c.stateRegion = :stateRegion"),
    @NamedQuery(name = "Customer.findByCountry", query = "SELECT c FROM Customer c WHERE c.country = :country"),
    @NamedQuery(name = "Customer.findByZip", query = "SELECT c FROM Customer c WHERE c.zip = :zip"),
    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByCreditCardNum", query = "SELECT c FROM Customer c WHERE c.cc = :cc")})
public class Customer implements Serializable {

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
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "state_region")
    private String stateRegion;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "zip")
    private String zip;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "cc")
    private String cc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Collection<CustomerOrder> customerOrderCollection;

    // no-arg constructor

    public Customer() {
    }

    //Contructor for Customer

    public Customer(Integer id) {
        this.id = id;
    }

    //Contructor for Customer

    public Customer(Integer id, String name, String email, String phone, String address, String city, String stateRegion, String country, String zip, String cc) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.stateRegion = stateRegion;
        this.country = country;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.cc = cc;
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

    //method (accessor method) that returns address

    public String getAddress() {
        return address;
    }

    //is a void method (mutator method) that allows for the driver to change the address

    public void setAddress(String address) {
        this.address = address;
    }

    //method (accessor method) that returns city

    public String getCity() {
        return city;
    }

    //is a void method (mutator method) that allows for the driver to change the ciy

    public void setCity(String city) {
        this.city = city;
    }

    //method (accessor method) that returns state/region

    public String getStateRegion() {
        return stateRegion;
    }

    //is a void method (mutator method) that allows for the driver to change the state/region

    public void setStateRegion(String stateRegion) {
        this.stateRegion = stateRegion;
    }

    //method (accessor method) that returns country

    public String getCountry() {
        return country;
    }

    //is a void method (mutator method) that allows for the driver to change the country

    public void setCountry(String country) {
        this.country = country;
    }

    //method (accessor method) that returns zip code

    public String getZipCode() {
        return zip;
    }

    //is a void method (mutator method) that allows for the driver to change the zip code

    public void setZipCode(String zip) {
        this.zip = zip;
    }

    //method (accessor method) that returns phone number

    public String getPhone() {
        return phone;
    }

    //is a void method (mutator method) that allows for the driver to change the phone number

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //method (accessor method) that returns email address

    public String getEmail() {
        return email;
    }

    //is a void method (mutator method) that allows for the driver to change the email address

    public void setEmail(String email) {
        this.email = email;
    }

    //method (accessor method) that returns credit card number

    public String getCreditCardNum() {
        return cc;
    }

    //is a void method (mutator method) that allows for the driver to change the credit card number

    public void setCreditCardNum(String cc) {
        this.cc = cc;
    }

    //method (accessor method) that returns customer Order Collection

    public Collection<CustomerOrder> getCustomerOrderCollection() {
        return customerOrderCollection;
    }

    //is a void method (mutator method) that allows for the driver to change the customer Order Collection

    public void setCustomerOrderCollection(Collection<CustomerOrder> customerOrderCollection) {
        this.customerOrderCollection = customerOrderCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    //method that returns formatted information

    @Override
    public String toString() {
        return "entity.Customer[id=" + id + "]";
    }

}
