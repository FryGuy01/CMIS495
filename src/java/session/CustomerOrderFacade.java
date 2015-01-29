/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CustomerOrder;
//import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brettfry
 */
@Stateless
public class CustomerOrderFacade extends AbstractFacade<CustomerOrder> {

    @PersistenceContext(unitName = "WebStoreBeanPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerOrderFacade() {
        super(CustomerOrder.class);
    }

    // refresh method called to retrieve and order id from database
    @Override
    public CustomerOrder find(Object id) {
        CustomerOrder order = em.find(CustomerOrder.class, id);
        em.refresh(order);
        return order;
    }

    // allows there to be only one order per customer
    /*uncomment below to implement security*/
    // @RolesAllowed("finalBeanAdmin")
    public CustomerOrder findByCustomer(Object customer) {
        return (CustomerOrder) em.createNamedQuery("CustomerOrder.findByCustomer").setParameter("customer", customer).getSingleResult();
    }

}
