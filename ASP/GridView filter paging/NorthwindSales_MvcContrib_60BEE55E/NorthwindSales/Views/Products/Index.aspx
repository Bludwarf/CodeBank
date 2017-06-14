<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<NorthwindSales.Models.ProductListContainerViewModel>" %>

<asp:Content ID="Content1" ContentPlaceHolderID="TitleContent" runat="server">
    Products Search
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="MainContent" runat="server">
    <h2>
        Product Search</h2>
    <%Html.RenderPartial("SearchFilters", Model.ProductFilterViewModel); %>
    <% Html.RenderPartial("Pager", Model.ProductPagedList); %>
    <% Html.RenderPartial("SearchResults", Model); %>
    <% Html.RenderPartial("Pager", Model.ProductPagedList); %>
</asp:Content>
