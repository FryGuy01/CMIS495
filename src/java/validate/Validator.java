/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import entity.Category;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author brettfry
 */
public class Validator {

    /* 
     ensures that quantity input is number between 0 and 99
     */
    public boolean validateQuantity(String productId, String quantity) {

        boolean errorFlag = false;

        if (!productId.isEmpty() && !quantity.isEmpty()) {

            int i = -1;

            try {

                i = Integer.parseInt(quantity);
            } catch (NumberFormatException nfe) {

                System.out.println("Number is empty");
            }

            if (i < 0 || i > 99) {

                errorFlag = true;
            }
        }

        return errorFlag;
    }

    // performs simple validation on checkout form
    public boolean validateForm(String name,
            String address,
            String city,
            String stateRegion,
            String country,
            String zip,
            String phone,
            String email,
            String cc,
            HttpServletRequest request) {

        boolean errorFlag = false;
        boolean nameError;
        boolean emailError;
        boolean phoneError;
        boolean addressError;
        boolean cityError;
        boolean stateRegionError;
        boolean countryError;
        boolean zipCodeError;
        boolean ccError;

        if (name == null
                || name.equals("")
                || name.length() > 45) {
            errorFlag = true;
            nameError = true;
            request.setAttribute("nameError", nameError);
        }
        if (address == null
                || address.equals("")
                || address.length() > 45) {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        }
        if (city == null
                || city.equals("")
                || city.length() > 45) {
            errorFlag = true;
            cityError = true;
            request.setAttribute("cityError", cityError);
        }
        if (stateRegion == null
                || stateRegion.equals("")
                || stateRegion.length() > 45) {
            errorFlag = true;
            stateRegionError = true;
            request.setAttribute("stateRegionError", stateRegionError);
        }
        if (country == null
                || country.equals("")
                || country.length() > 19) {
            errorFlag = true;
            countryError = true;
            request.setAttribute("countryError", countryError);
        }
        if (zip == null
                || zip.equals("")
                || zip.length() > 19) {
            errorFlag = true;
            zipCodeError = true;
            request.setAttribute("zipCodeError", zipCodeError);
        }
        if (phone == null
                || phone.equals("")
                || phone.length() < 9) {
            errorFlag = true;
            phoneError = true;
            request.setAttribute("phoneError", phoneError);
        }
        if (email == null
                || email.equals("")
                || !email.contains("@")) {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }
        if (cc == null
                || cc.equals("")
                || cc.length() < 9) {
            errorFlag = true;
            ccError = true;
            request.setAttribute("ccError", ccError);
        }

        return errorFlag;
    }

    // performs simple validation on add product
    public boolean validateInput(Integer id,
            String name,
            BigDecimal price,
            Category categoryID,
            HttpServletRequest request) {

        boolean errorFlag = false;
        boolean idError;
        boolean nameError;
        boolean priceError;
        boolean categoryError;

        if (id == null
                || id.equals("")
                || id > 99999) {
            errorFlag = true;
            idError = true;
            request.setAttribute("idError", idError);
        }
        if (name == null
                || name.equals("")
                || name.length() > 45) {
            errorFlag = true;
            nameError = true;
            request.setAttribute("nameError", nameError);
        }

        if (price == null
                || price.equals("")
                || price.toString().length() > 45) {
            errorFlag = true;
            priceError = true;
            request.setAttribute("priceError", priceError);
        }
        if (categoryID == null
                || categoryID.equals("")
                || categoryID.toString().length() > 45) {
            errorFlag = true;
            categoryError = true;
            request.setAttribute("categoryError", categoryError);
        }

        return errorFlag;
    }

}
