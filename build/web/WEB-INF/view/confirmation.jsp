<%-- HTML markup starts below --%>

<div id="singleColumn">

    <p id="confirmationText">
        <strong><fmt:message key="successMessage"/></strong>
        <br><br>
        <fmt:message key="confirmationNumberMessage"/>
        <strong>${orderRecord.confirmationNumber}</strong>
        <br>
        <fmt:message key="contactMessage"/>
        <br><br>
        <fmt:message key="thankYouMessage"/>
    </p>

    <div class="summaryColumn" >

        <table id="orderSummaryTable" class="detailsTable">
            <tr class="header">
                <th colspan="3"><fmt:message key="orderSummary"/></th>
            </tr>

            <tr class="tableHeading">
                <td><fmt:message key="product"/></td>
                <td><fmt:message key="quantity"/></td>
                <td><fmt:message key="price"/></td>
            </tr>

            <c:forEach var="orderedProduct" items="${orderedProducts}" varStatus="iter">

                <tr class="${((iter.index % 2) != 0) ? 'lightBlue' : 'white'}">
                    <td>
                        <fmt:message key="${products[iter.index].name}"/>
                    </td>
                    <td class="quantityColumn">
                        ${orderedProduct.quantity}
                    </td>
                    <td class="confirmationPriceColumn">
                        <fmt:formatNumber type="currency" currencySymbol="$ "
                                          value="${products[iter.index].price * orderedProduct.quantity}"/>
                    </td>
                </tr>

            </c:forEach>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="2" id="taxCellLeft"><strong><fmt:message key="tax"/>:</strong></td>
                <td id="taxCellRight">
                    <fmt:formatNumber type="currency"
                                      currencySymbol="$ "
                                      value="${initParam.tax}"/></td>
            </tr>

            <tr class="lightBlue">
                <td colspan="2" id="totalCellLeft"><strong><fmt:message key="total"/>:</strong></td>
                <td id="totalCellRight">
                    <fmt:formatNumber type="currency"
                                      currencySymbol="$ "
                                      value="${orderRecord.amount}"/></td>
            </tr>

            <tr class="lightBlue"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="lightBlue">
                <td colspan="3" id="dateProcessedRow"><strong><fmt:message key="dateProcessed"/>:</strong>
                    <fmt:formatDate value="${orderRecord.dateCreated}"
                                    type="both"
                                    dateStyle="short"
                                    timeStyle="short"/></td>
            </tr>
        </table>

    </div>

    <div class="summaryColumn" >

        <table id="deliveryAddressTable" class="detailsTable">
            <tr class="header">
                <th colspan="3"><fmt:message key="deliveryAddress"/></th>
            </tr>

            <tr>
                <td colspan="3" class="lightBlue">
                    <strong><fmt:message key="name"/>:</strong>${customer.name}
                    <br>
                    <strong><fmt:message key="address"/>:</strong>${customer.address}
                    <br>
                    <strong><fmt:message key="city"/>:</strong> ${customer.city}
                    <br>
                    <strong><fmt:message key="stateRegion"/>:</strong> ${customer.stateRegion}
                    <br>
                    <strong><fmt:message key="country"/>:</strong> ${customer.country}
                    <br>
                    <hr>
                    <strong><fmt:message key="email"/>:</strong> ${customer.email}
                    <br>
                    <strong><fmt:message key="phone"/>:</strong> ${customer.phone}
                </td>
            </tr>
        </table>
    </div>
</div>