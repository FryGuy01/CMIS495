/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.*;
import entity.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brettfry
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrderManager {

    @PersistenceContext(unitName = "WebStoreBeanPU")
    private EntityManager em;
    @Resource
    private SessionContext context;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private OrderedProductFacade orderedProductFacade;

    // handle orders transactions
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String address, String city, String stateRegion, String country, String zip, String phone, String email, String cc, Cart cart) {

        try {
            Customer customer = addCustomer(name, address, city, stateRegion, country, zip, phone, email, cc);

            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    // initiate creation of product
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeProduct(Integer id, String name, BigDecimal price, Category categoryID) {

        try {
            //set date
            Date last_update = new Date();
            //create new Product 
            Product product = addProduct(id, name, price, last_update, categoryID);

            return product.getId();

        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    //add Customer to database
    private Customer addCustomer(String name, String address, String city, String stateRegion, String country, String zip, String phone, String email, String cc) {
        // set up Customer
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setStateRegion(stateRegion);
        customer.setCountry(country);
        customer.setZipCode(zip);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setCreditCardNum(cc);
        em.persist(customer);
        return customer;
    }

    // add Product to database
    private Product addProduct(Integer id, String name, BigDecimal price, Date lastUpdate, Category categoryID) {
        // set up Product
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setLastUpdate(lastUpdate);
        product.setCategory(categoryID);
        em.persist(product);
        return product;
    }

    // remove Product from database
    public void removeProduct(Product product) {

        em.remove(em.merge(product));

    }

    // add CustomerOrder to database
    private CustomerOrder addOrder(Customer customer, Cart cart) {

        // set up CustomerOrder
        CustomerOrder order = new CustomerOrder();

        order.setDateCreated(new Date());
        order.setCustomer(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        // create random confirmation number
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);

        em.persist(order);
        return order;
    }

    private void addOrderedItems(CustomerOrder order, Cart cart) {

        em.flush();

        List<CartItems> items = cart.getItems();

        // iterate through shopping cart and create OrderedProducts
        for (CartItems cartItems : items) {

            int productId = cartItems.getProduct().getId();

            // set up primary key object
            OrderedProductPK oProductPK = new OrderedProductPK();
            oProductPK.setCustomerOrderId(order.getId());
            oProductPK.setProductId(productId);

            // create ordered item using PK object
            OrderedProduct orderedItem = new OrderedProduct(oProductPK);

            // set quantity
            orderedItem.setQuantity(cartItems.getQuantity());

            em.persist(orderedItem);
        }
    }

    public Map getOrderDetails(int orderId) {

        Map orderedMap = new HashMap();

        // get order
        CustomerOrder order = customerOrderFacade.find(orderId);

        // get customer
        Customer customer = order.getCustomer();

        // get all ordered products
        List<OrderedProduct> orderedProducts = orderedProductFacade.findByOrderId(orderId);

        // get product details for ordered items
        List<Product> products = new ArrayList<Product>();

        for (OrderedProduct op : orderedProducts) {

            Product p = (Product) productFacade.find(op.getOPPK().getProductId());
            products.add(p);
        }

        // add each item to orderedMap
        orderedMap.put("orderRecord", order);
        orderedMap.put("customer", customer);
        orderedMap.put("orderedProducts", orderedProducts);
        orderedMap.put("products", products);

        return orderedMap;
    }

}
