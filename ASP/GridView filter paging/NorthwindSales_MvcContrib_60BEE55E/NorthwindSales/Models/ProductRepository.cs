using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Globalization;
using System.ComponentModel;
using System.Linq.Expressions;
using System.Web.Mvc;

namespace NorthwindSales.Models
{
    public class ProductRepository : IProductRepository
    {
        #region IProductRepository Members

        /// <summary>
        /// IQueryable of all Products
        /// </summary>
        /// <returns></returns>
        public IQueryable<Product> GetProducts()
        {
            var dataContext = new NorthwindEntities();
            var products = from p in dataContext.Products
                           select p;
            return products;
        }

        /// <summary>
        /// IQueryable of Projects projected 
        /// into the ProductViewModel class
        /// </summary>
        /// <returns></returns>
        public IQueryable<ProductViewModel> GetProductsProjected()
        {
            var projectedProducts = from p in GetProducts()
                                select new ProductViewModel
                                {
                                    ProductID = p.ProductID,
                                    ProductName = p.ProductName,
                                    UnitPrice = p.UnitPrice,
                                    CategoryName = p.Category.CategoryName,
                                    CategoryID = p.CategoryID,
                                    SupplierID = p.SupplierID, 
                                    Discontinued = p.Discontinued
                                };

            return projectedProducts;
        }

        /// <summary>
        /// ProductDetailsViewModel for a given productID
        /// </summary>
        /// <param name="productID"></param>
        /// <returns></returns>
        public ProductDetailsViewModel GetProduct(int productID)
        {
            var productDetails = from p in GetProducts()
                                 where p.ProductID == productID
                                 select new ProductDetailsViewModel
                                 {
                                     ProductID = p.ProductID,
                                     ProductName = p.ProductName,
                                     QuantityPerUnit = p.QuantityPerUnit,
                                     UnitPrice = p.UnitPrice,
                                     CategoryName = p.Category.CategoryName,
                                     SupplierName = p.Supplier.CompanyName
                                 };

            return productDetails.SingleOrDefault();
        }

        #endregion
    }
}
