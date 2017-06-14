<%@ Control Language="C#" Inherits="System.Web.Mvc.ViewUserControl<NorthwindSales.Models.ProductListContainerViewModel>" %>
<%@ Import Namespace="MvcContrib.UI.Grid" %>
<%@ Import Namespace="NorthwindSales.Models" %>
<%= Html.Grid(Model.ProductPagedList).AutoGenerateColumns()
    .Columns(column => {
        column.For(a => Html.ActionLink("Details", "Details", new { id = a.ProductID })).InsertAt(0).Encode(false);
    })
    .Sort(Model.GridSortOptions)
    .Attributes(@class => "table-list")
%>
