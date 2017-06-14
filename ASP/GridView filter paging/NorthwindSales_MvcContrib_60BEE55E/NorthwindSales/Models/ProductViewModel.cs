using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace NorthwindSales.Models
{
    public class ProductViewModel
    {
        [ScaffoldColumn(false)]
        public int? ProductID { get; set; }

        public string ProductName { get; set; }

        [DisplayName("Unit Price")]
        [DisplayFormat(DataFormatString = "{0:c}")] 
        public System.Nullable<decimal> UnitPrice { get; set; }

        [DisplayName("Category Name")]
        public string CategoryName { get; set; }

        [ScaffoldColumn(false)]
        public int? CategoryID { get; set; }

        [ScaffoldColumn(false)]
        public int? SupplierID { get; set; }

        public bool Discontinued { get; set; }
    }
}
