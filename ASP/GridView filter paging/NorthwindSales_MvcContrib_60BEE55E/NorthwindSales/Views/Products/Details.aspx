<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<NorthwindSales.Models.ProductDetailsViewModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
    GetProduct
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    <h2>
        Product Details</h2>
    <div id="searchFilter">
        <%: Html.ActionLink("« Back to List", "Index", null, new { style = "font-weight:bold" })%>
    </div>
    <div id="detailsContainer">
        <%:Html.DisplayFor(a=> a) %>
    </div>
    <p />
    <div class="pagination">
    </div>
</asp:Content>
