using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using NorthwindSales.Models;
using MvcContrib.Sorting;
using MvcContrib.UI.Grid;
using MvcContrib.Pagination;

namespace NorthwindSales.Controllers
{
    public class ProductsController : Controller
    {
        IProductRepository productRepository;

        public ProductsController()
        {
            productRepository = new ProductRepository();
        }

        /// <summary>
        /// This method takes in a filter list, paging/sort options and applies
        /// them to an IQueryable of type ProductViewModel
        /// </summary>
        /// <returns>
        /// The return object is a container that holds the sorted/paged list,
        /// state for the fiters and state about the current sorted column
        /// </returns>
        public ActionResult Index(
            string productName, 
            int? supplierID, 
            int? categoryID, 
            GridSortOptions gridSortOptions, 
            int? page)
        {

            var productList = productRepository.GetProductsProjected();

            // Set default sort column
            if (string.IsNullOrWhiteSpace(gridSortOptions.Column))
            {
                gridSortOptions.Column = "ProductID";
            }

            // Filter on SupplierID 
            if (supplierID.HasValue)
            {
                productList = productList.Where(a => a.SupplierID == supplierID);
            }

            // Filter on CategoryID 
            if (categoryID.HasValue)
            {
                productList = productList.Where(a => a.CategoryID == categoryID);
            }

            // Filter on ProductName
            if (!string.IsNullOrWhiteSpace(productName))
            {
                productList = productList.Where(a => a.ProductName.Contains(productName));
            }

            // Create all filter data and set current values if any
            // These values will be used to set the state of the select list and textbox
            // by sending it back to the view.
            var productFilterViewModel = new ProductFilterViewModel();
            productFilterViewModel.SelectedCategoryID = categoryID ?? -1;
            productFilterViewModel.SelectedSupplierID = supplierID ?? -1;
            productFilterViewModel.Fill();

            // Order and page the product list
            var productPagedList = productList
                   .OrderBy(gridSortOptions.Column, gridSortOptions.Direction)
                   .AsPagination(page ?? 1, 10);


            var productListContainer = new ProductListContainerViewModel
            {
                ProductPagedList = productPagedList,
                ProductFilterViewModel = productFilterViewModel,
                GridSortOptions = gridSortOptions
            };

            return View(productListContainer);
        }

        /// <summary>
        /// Returns details for a given product
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public ActionResult Details(int id)
        {
            ProductRepository pr = new ProductRepository();
            return View(pr.GetProduct(id));
        }
    }
}
