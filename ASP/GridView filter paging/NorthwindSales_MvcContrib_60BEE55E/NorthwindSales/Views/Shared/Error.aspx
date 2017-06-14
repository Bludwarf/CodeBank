<%@ Page Language="C#" MasterPageFile="~/Views/Shared/Site.Master" Inherits="System.Web.Mvc.ViewPage<System.Web.Mvc.HandleErrorInfo>" %>

<asp:Content ID="errorTitle" ContentPlaceHolderID="TitleContent" runat="server">
    Error
</asp:Content>
<asp:Content ID="errorContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2 class="errorMessage">
        Sorry, an error occurred while processing your request.
    </h2>
    Exception message displayed for demo purposes<br />
    <%if (Model.Exception != null)
      { %>
    <%=Html.Encode(Model.Exception.Message)%>
    <%} %>
</asp:Content>
