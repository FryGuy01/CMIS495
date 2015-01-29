/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.Cart;
import entity.Category;
import entity.Product;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import session.CategoryFacade;
import session.OrderManager;
import session.ProductFacade;
import validate.Validator;

/**
 *
 * @author brettfry
 */
@WebServlet(name = "Controller",
        loadOnStartup = 1,
        urlPatterns = {"/category",
            "/addToCart",
            "/viewCart",
            "/updateCart",
            "/checkout",
            "/purchase",
            "/addProduct",
            "/confirmation",
            "/chooseLanguage"})
public class ControllerServlet extends HttpServlet {

    private String tariff;

    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private OrderManager orderManager;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        tariff = servletConfig.getServletContext().getInitParameter("tax");

        // store category list in servlet context
        getServletContext().setAttribute("categories", categoryFacade.findAll());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Category selectedCategory;
        Collection<Product> categoryProducts;

        // if category page is requested
        if (userPath.equals("/category")) {

            // get categoryId from request
            String categoryId = request.getQueryString();
            // if categoryId is not null
            if (categoryId != null) {

                // get selected category
                selectedCategory = categoryFacade.find(Short.parseShort(categoryId));

                // place selected category in session scope
                session.setAttribute("selectedCategory", selectedCategory);

                // get all products for selected category
                categoryProducts = selectedCategory.getProductCollection();

                // place category products in session scope
                session.setAttribute("categoryProducts", categoryProducts);
            }

            // if cart page is requested
        } else if (userPath.equals("/viewCart")) {

            String clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {

                Cart cart = (Cart) session.getAttribute("cart");
                cart.clear();
            }
            //set userPath to /cart
            userPath = "/cart";

            // if checkout page is requested forward to checkout page and switch to a secure channel
        } else if (userPath.equals("/checkout")) {

            Cart cart = (Cart) session.getAttribute("cart");

            // calculate total
            cart.calculateTotal(tariff);

            // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {

            // get language choice
            String language = request.getParameter("language");

            // place in request scope
            request.setAttribute("language", language);
            //set userView based on selection
            String userView = (String) session.getAttribute("view");

            if ((userView != null)
                    && (!userView.equals("/index"))) {

                userPath = userView;
            } else {

                // if previous view is index or cannot be determined, send user to home page
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");  // ensures that user input is interpreted as
        // 8-bit Unicode (e.g., for Russian characters)

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Validator validator = new Validator();

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {

            // if user is adding item to cart for first time  create cart object 
            if (cart == null) {

                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            // get user input from request
            String productId = request.getParameter("productId");

            if (!productId.isEmpty()) {

                Product product = productFacade.find(Integer.parseInt(productId));
                cart.addItem(product);
            }

            userPath = "/category";

            // if updateCart action is called
        } else if (userPath.equals("/updateCart")) {

            // get input from request
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            boolean invalidEntry = validator.validateQuantity(productId, quantity);

            if (!invalidEntry) {

                Product product = productFacade.find(Integer.parseInt(productId));
                cart.update(product, quantity);
            }

            userPath = "/cart";

            // if purchase action is called
        } else if (userPath.equals("/purchase")) {

            if (cart != null) {

                // extract user data from request
                String name = request.getParameter("name");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String stateRegion = request.getParameter("stateRegion");
                String country = request.getParameter("country");
                String zip = request.getParameter("zip");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String cc = request.getParameter("creditcard");

                // validate user data
                boolean validationErrorFlag = false;
                validationErrorFlag = validator.validateForm(name, address, city, stateRegion, country, zip, phone, email, cc, request);

                // if validation error found, return user to checkout
                if (validationErrorFlag == true) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/checkout";

                    // otherwise, save order to database
                } else {
                    //create record
                    int orderId = orderManager.placeOrder(name, address, city, stateRegion, country, zip, phone, email, cc, cart);

                    // if order processed successfully send user to confirmation page
                    if (orderId != 0) {

                        // in case language was set using toggle, get language choice before destroying session
                        Locale locale = (Locale) session.getAttribute("javax.servlet.jsp.jstl.fmt.locale.session");
                        String language = "";

                        if (locale != null) {

                            language = (String) locale.getLanguage();
                        }

                        // dissociate shopping cart from session
                        cart = null;

                        // end session
                        session.invalidate();

                        if (!language.isEmpty()) {
                            // if user changed language using the toggle,
                            // reset the language attribute - otherwise
                            // language will be switched on confirmation page!
                            request.setAttribute("language", language);
                        }

                        // get order details
                        Map orderedMap = orderManager.getOrderDetails(orderId);

                        // place order details in request scope
                        request.setAttribute("customer", orderedMap.get("customer"));
                        request.setAttribute("products", orderedMap.get("products"));
                        request.setAttribute("orderRecord", orderedMap.get("orderRecord"));
                        request.setAttribute("orderedProducts", orderedMap.get("orderedProducts"));

                        userPath = "/confirmation";

                        // otherwise, send back to checkout page and display error
                    } else {
                        userPath = "/checkout";
                        request.setAttribute("orderFailureFlag", true);
                    }
                }
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
