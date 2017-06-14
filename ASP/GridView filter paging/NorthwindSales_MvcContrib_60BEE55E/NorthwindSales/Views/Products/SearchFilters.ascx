<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<NorthwindSales.Models.ProductFilterViewModel>" %>
<%
    var htmlAttributes = new Dictionary<string, object> { { "data-autopostback", "true" } }; 
%>
<%
    using (Html.BeginForm("Index", "Products", FormMethod.Get, new { id = "productSearch" }))
    { %>
<div id="searchFilter">
    <div>
        <label>
            Product Name</label>
        <%:Html.TextBox("productName", Model.ProductName, new { size = 30, maxlength = "40" })%>
    </div>
    <div>
        <label>
            Supplier</label>
        <%:Html.DropDownList("supplierId", Model.Suppliers, "-- All --", htmlAttributes)%>
    </div>
    <div>
        <label>
            Category</label>
        <%:Html.DropDownList("categoryId", Model.Categories, "-- All --", htmlAttributes)%>
    </div>
    <span>
        <input type="submit" value="Search" class="btnNeutral" />
    </span>
</div>
<%} %>