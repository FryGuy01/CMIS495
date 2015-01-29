/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.Category;
import entity.Customer;
import entity.CustomerOrder;
import entity.Product;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;
import session.CustomerFacade;
import session.CustomerOrderFacade;
import session.OrderManager;
import session.ProductFacade;
import validate.Validator;

/**
 *
 * @author brettfry
 */
@WebServlet(name = "AdminServlet",
        urlPatterns = {"/admin/",
            "/admin/viewOrders",
            "/admin/viewCustomers",
            "/admin/viewProducts",
            "/admin/customerRecord",
            "/admin/orderRecord",
            "/admin/productUpdate",
            "/admin/add",
            "/admin/remove",
            "/admin/create",
            "/admin/logout"})
//uncomment if you wish to implement security
/*@ServletSecurity(
 @HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL,
 rolesAllowed = {"finalBeanAdmin"})
 )*/
public class AdminServlet extends HttpServlet {

    @EJB
    private OrderManager orderManager;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private CustomerOrderFacade customerOrderFacade;
    @EJB
    private ProductFacade productFacade;

    private String userPath;
    private String test;
    private Customer customer;
    private CustomerOrder order;
    private Product product;
    private List orderList = new ArrayList();
    private List customerList = new ArrayList();
    private List productList = new ArrayList();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        userPath = request.getServletPath();

        // if viewCustomers is requested
        if (userPath.equals("/admin/viewCustomers")) {
            customerList = customerFacade.findAll();
            request.setAttribute("customerList", customerList);
        }

        // if viewOrders is requested
        if (userPath.equals("/admin/viewOrders")) {
            orderList = customerOrderFacade.findAll();
            request.setAttribute("orderList", orderList);
        }

        // if viewProductss is requested
        if (userPath.equals("/admin/viewProducts")) {
            productList = productFacade.findAll();
            request.setAttribute("productList", productList);
        }

        // if customerRecord is requested
        if (userPath.equals("/admin/customerRecord")) {

            // get customer ID from request
            String customerId = request.getQueryString();

            // get customer details
            customer = customerFacade.find(Integer.parseInt(customerId));
            request.setAttribute("customerRecord", customer);

            // get customer order details
            order = customerOrderFacade.findByCustomer(customer);
            request.setAttribute("order", order);
        }

        // if orderRecord is requested
        if (userPath.equals("/admin/orderRecord")) {

            // get customer ID from request
            String orderId = request.getQueryString();

            // get order details
            Map orderedMap = orderManager.getOrderDetails(Integer.parseInt(orderId));

            // place order details in request scope
            request.setAttribute("customer", orderedMap.get("customer"));
            request.setAttribute("products", orderedMap.get("products"));
            request.setAttribute("orderRecord", orderedMap.get("orderRecord"));
            request.setAttribute("orderedProducts", orderedMap.get("orderedProducts"));
        }

        // if productRecord is requested
        if (userPath.equals("/admin/productRecord")) {

            // get customer ID from request
            String productId = request.getQueryString();

            // get customer details
            product = productFacade.find(Integer.parseInt(productId));
            request.setAttribute("productRecord", product);

        }

        // if logout is requested if active
        if (userPath.equals("/admin/logout")) {
            session = request.getSession();
            session.invalidate();   // terminate session
            response.sendRedirect("/WebStore/admin/");
            return;
        }

        // use RequestDispatcher to forward request internally
        userPath = "/admin/index.jsp";

        try {
            request.getRequestDispatcher(userPath).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();

        Collection<Product> categoryProducts;

        // if product page is requested
        if (userPath.equals("/admin/viewProducts")) {

            // get productId from request
            String productId = request.getQueryString();

            if (productId != null) {

                productList = productFacade.findAll();

                //request.setAttribute("productList", productList);
                categoryProducts = productList;
                // place category products in session scope
                session.setAttribute("categoryProducts", categoryProducts);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        userPath = request.getServletPath();
        Validator validator = new Validator();
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");  // ensures that user input is interpreted as
        // 8-bit Unicode (e.g., for Russian characters)
        try {

            // create product
            if (userPath.equals("/admin/create")) {

                // extract user data from request
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String price = request.getParameter("price");
                //for future use
                //String description = request.getParameter("description");
                String cat = request.getParameter("cat");
                Category category = new Category(Short.parseShort(cat));
                BigDecimal bd = new BigDecimal(price);

                // validate user data
                boolean validationErrorFlag = false;
                validationErrorFlag = validator.validateInput(Integer.parseInt(id), name, bd, category, request);

                // if validation error found, return user to create
                if (validationErrorFlag == true) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/admin/add";
                } else {
                    //create record
                    int productId = orderManager.placeProduct(Integer.parseInt(id), name, bd, category);
                    // description, 
                    System.out.println(id);
                    System.out.println(name);
                    System.out.println(price);
                    System.out.println(cat);
                    if (productId != 0) {

                        userPath = "/admin/index.jsp";

                        // otherwise, send back to checkout page and display error
                    } else {
                        userPath = "/admin/add";
                        request.setAttribute("orderFailureFlag", true);
                    }

                }
            }
        } catch (Exception e) {
            userPath = "/admin/add";
        }
        if (userPath.equals("/admin/remove")) {

            // get user input from request
            String productId = request.getParameter("productId");
            System.out.println(productId);
            if (!productId.isEmpty()) {

                product = productFacade.find(Integer.parseInt(productId));

                orderManager.removeProduct(product);
                userPath = "/admin/viewProducts";
            }

        }

        // use RequestDispatcher to forward request internally
        String url = userPath;

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
