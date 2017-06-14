using System;
using System.Linq;
namespace NorthwindSales.Models
{
    interface IProductRepository
    {
        ProductDetailsViewModel GetProduct(int productID);
        IQueryable<Product> GetProducts();
        IQueryable<ProductViewModel> GetProductsProjected();
    }
}
