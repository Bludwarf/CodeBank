using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Mvc;

namespace NorthwindSales.Models
{
    /// <summary>
    /// This class contains all the data needed to render the search filter view. 
    /// It stores the state of the previously selected filters to 
    /// pass back to the view
    /// </summary>
    public class ProductFilterViewModel
    {
        private int selectedCategoryID = -1;
        private int selectedSupplierID = -1;

        public List<SelectListItem> Categories { get; set; }
        public List<SelectListItem> Suppliers { get; set; }
        public string ProductNameFilter { get; set; }
        public string ProductName { get; set; }
        public int SelectedCategoryID
        {
            get
            {
                return selectedCategoryID;
            }
            set
            {
                selectedCategoryID = value;
            }
        }
        public int SelectedSupplierID
        {
            get
            {
                return selectedSupplierID;
            }
            set
            {
                selectedSupplierID = value;
            }
        }
       

        public void Fill()
        {

            var dataContext = new NorthwindEntities();

            Categories = dataContext
                            .Categories
                            .Select(a =>
                                new
                                {
                                    a.CategoryName,
                                    a.CategoryID
                                }
                            )
                            .ToList()
                            .Select(a =>
                                new SelectListItem
                                {
                                    Text = a.CategoryName,
                                    Value = a.CategoryID.ToString(),
                                    Selected = a.CategoryID == SelectedCategoryID
                                }).ToList();

            Suppliers = dataContext
                            .Suppliers.
                            Select(a =>
                                new
                                {
                                    a.CompanyName,
                                    a.SupplierID
                                }
                            )
                            .ToList()
                            .Select(a =>
                               new SelectListItem
                               {
                                   Text = a.CompanyName,
                                   Value = Convert.ToString(a.SupplierID),
                                   Selected = a.SupplierID == SelectedSupplierID
                               }).ToList();
 
        }
    }
}
