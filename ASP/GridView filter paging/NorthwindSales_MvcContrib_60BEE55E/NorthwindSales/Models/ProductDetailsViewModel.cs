using System.ComponentModel;

namespace NorthwindSales.Models
{
    public class ProductDetailsViewModel : ProductViewModel
    {
        [DisplayName("Quantity Per Unit")]
        public string QuantityPerUnit { get; set; }
        [DisplayName("Supplier Name")]
        public string SupplierName { get; set; }
    }
}
