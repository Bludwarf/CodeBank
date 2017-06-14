using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using MvcContrib.Pagination;
using MvcContrib.UI.Grid;

namespace NorthwindSales.Models
{
    public class ProductListContainerViewModel
    {
        public IPagination<ProductViewModel> ProductPagedList { get; set; }
        public ProductFilterViewModel ProductFilterViewModel { get; set; }
        public GridSortOptions GridSortOptions { get; set; }
    }

   
}
