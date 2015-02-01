<%-- HTML markup starts below --%>
<c:set var="view" value="/add" scope="session"/>
<div id="singleColumn">

    <h2><fmt:message key="product"/></h2>

    <p><fmt:message key="addproductText"/></p>

    <script type="text/javascript">

        $(document).ready(function() {
            $("#newProductForm").validate({
                rules: {
                    id: "required",
                    name: {
                        required: true
                    },
                    price: {
                        required: true,
                        decimal: true,
                        minlength: 1
                    },
                    categoty: {
                        required: true,
                        category: true
                    }
                }
            });
        });
    </script>
    <c:if test="${!empty orderFailureFlag}">
        <p class="error"><fmt:message key="orderFailureError"/></p>
    </c:if>

    <form id="newProductForm" action="<c:url value='create'/>" method="post">
        <table id="newproductTable">

            <c:if test="${!empty validationErrorFlag}">
                <tr>
                    <td colspan="2" style="text-align:left">
                        <span class="error smallText"><fmt:message key="validationErrorMessage"/>

                            <c:if test="${!empty idError}">
                                <br><span class="indent"><fmt:message key="idError"/></span>
                            </c:if>
                            <c:if test="${!empty nameError}">
                                <br><span class="indent"><fmt:message key="nameError"/></span>
                            </c:if>
                            <c:if test="${!empty priceError}">
                                <br><span class="indent"><fmt:message key="priceError"/></span>
                            </c:if>
                            <c:if test="${!empty categoryError}">
                                <br><span class="indent"><fmt:message key="categoryError"/></span>
                            </c:if>
                        </span>
                    </td>
                </tr>
            </c:if>

            <tr>
                <td><label for="id"><fmt:message key="productID"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="id"
                           name="id"
                           value="${param.id}">
                </td>
            </tr>
            <tr>
                <td><label for="name"><fmt:message key="productName"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="name"
                           name="name"
                           value="${param.name}">
                </td>
            </tr>
            <tr>
                <td><label for="price"><fmt:message key="productPrice"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="16"
                           id="price"
                           name="price"
                           value="${param.price}">
                </td>
            </tr>
            <tr>
                <td><label for="cat"><fmt:message key="productCat"/>:</label></td>
                <td class="inputField">
                    <input type="text"
                           size="31"
                           maxlength="45"
                           id="cat"
                           name="cat"
                           value="${param.cat}">

            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value="create/update product">
                </td>
            </tr>
        </table>
    </form>


</div>