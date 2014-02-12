package com.smart.mis.client.cube;

import com.smartgwt.client.widgets.cube.Facet;  
import com.smartgwt.client.widgets.cube.FacetValue; 

public class SaleVolumnFacets {
	
	public static Facet[] getSaleVolumnFacets() {  
        // Zone  
        Facet zone = new Facet();  
        zone.setId("zone"); // *
        zone.setTitle("Zones");  
        zone.setIsTree(true);  
  
        FacetValue allZone = new FacetValue("sum", "All Zones");  
        allZone.setCollapsed(false);  
  
        zone.setValues(  
        		allZone,  
                new FacetValue("Asia", "Asia", "sum"),  
                new FacetValue("Europe", "Europe", "sum"),  
                new FacetValue("North America", "North America", "sum")
        );  
  
//        // salePerson  
//        Facet salePerson = new Facet();  
//        salePerson.setId("salePerson");  // *
//        salePerson.setTitle("Sale persons");  
//        salePerson.setValues(  
//                new FacetValue("301", "สมศรี"),  
//                new FacetValue("302", "สมใจ"), 
//                new FacetValue("303", "สมมุติ")  
//        );  
  
        // Time  
        Facet time = new Facet();  
        time.setId("time");  
        time.setTitle("Time");  
        time.setIsTree(true);  
        time.setCollapsed(true);  
        time.setWidth(150);  
  
        FacetValue allYears = new FacetValue("sum", "All Years");  
        allYears.setCollapsed(false);  
  
        time.setValues(  
                allYears,  
                new FacetValue("2011", "2011", "sum"),  
                new FacetValue("2012", "2012", "sum"),  
                new FacetValue("2013", "2013", "sum"),
//                {{  
//                    setCollapsed(false);  
//                }},  
                new FacetValue("Q1-2011", "Q1-2011", "2011"),  
                new FacetValue("Q2-2011", "Q2-2011", "2011"),  
                new FacetValue("Q3-2011", "Q3-2011", "2011"),  
                new FacetValue("Q4-2011", "Q4-2011", "2011"),  
                new FacetValue("Q1-2012", "Q1-2012", "2012"),  
                new FacetValue("Q2-2012", "Q2-2012", "2012"),  
                new FacetValue("Q3-2012", "Q3-2012", "2012"),  
                new FacetValue("Q4-2012", "Q4-2012", "2012"),  
                new FacetValue("Q1-2013", "Q1-2013", "2013"),  
                new FacetValue("Q2-2013", "Q2-2013", "2013"),  
                new FacetValue("Q3-2013", "Q3-2013", "2013"),  
                new FacetValue("Q4-2013", "Q4-2013", "2013"),  
                new FacetValue("1/1/2011", "1/1/2011", "Q1-2011"),  
                new FacetValue("2/1/2011", "2/1/2011", "Q1-2011"),  
                new FacetValue("3/1/2011", "3/1/2011", "Q1-2011"),  
                new FacetValue("4/1/2011", "4/1/2011", "Q2-2011"),  
                new FacetValue("5/1/2011", "5/1/2011", "Q2-2011"),  
                new FacetValue("6/1/2011", "6/1/2011", "Q2-2011"),  
                new FacetValue("7/1/2011", "7/1/2011", "Q3-2011"),  
                new FacetValue("8/1/2011", "8/1/2011", "Q3-2011"),  
                new FacetValue("9/1/2011", "9/1/2011", "Q3-2011"),  
                new FacetValue("10/1/2011", "10/1/2011", "Q4-2011"),  
                new FacetValue("11/1/2011", "11/1/2011", "Q4-2011"),  
                new FacetValue("12/1/2011", "12/1/2011", "Q4-2011"),  
                new FacetValue("1/1/2012", "1/1/2012", "Q1-2012"),  
                new FacetValue("2/1/2012", "2/1/2012", "Q1-2012"),  
                new FacetValue("3/1/2012", "3/1/2012", "Q1-2012"),  
                new FacetValue("4/1/2012", "4/1/2012", "Q2-2012"),  
                new FacetValue("5/1/2012", "5/1/2012", "Q2-2012"),  
                new FacetValue("6/1/2012", "6/1/2012", "Q2-2012"),  
                new FacetValue("7/1/2012", "7/1/2012", "Q3-2012"),  
                new FacetValue("8/1/2012", "8/1/2012", "Q3-2012"),  
                new FacetValue("9/1/2012", "9/1/2012", "Q3-2012"),  
                new FacetValue("10/1/2012", "10/1/2012", "Q4-2012"),  
                new FacetValue("11/1/2012", "11/1/2012", "Q4-2012"),  
                new FacetValue("12/1/2012", "12/1/2012", "Q4-2012"),  
                new FacetValue("1/1/2013", "1/1/2013", "Q1-2013"),  
                new FacetValue("2/1/2013", "2/1/2013", "Q1-2013"),  
                new FacetValue("3/1/2013", "3/1/2013", "Q1-2013"),  
                new FacetValue("4/1/2013", "4/1/2013", "Q2-2013"),  
                new FacetValue("5/1/2013", "5/1/2013", "Q2-2013"),  
                new FacetValue("6/1/2013", "6/1/2013", "Q2-2013"),  
                new FacetValue("7/1/2013", "7/1/2013", "Q3-2013"),  
                new FacetValue("8/1/2013", "8/1/2013", "Q3-2013"),  
                new FacetValue("9/1/2013", "9/1/2013", "Q3-2013"),  
                new FacetValue("10/1/2013", "10/1/2013", "Q4-2013"),  
                new FacetValue("11/1/2013", "11/1/2013", "Q4-2013"),  
                new FacetValue("12/1/2013", "12/1/2013", "Q4-2013")  
        );  
  
        // Products  
        Facet product = new Facet();  
        product.setId("product");  
        product.setTitle("Products");  
        product.setIsTree(true);  
        product.setCollapsed(true);  
        product.setWidth(175);  
  
        product.setValues(  
                new FacetValue("sum", "All Products"),
//                {{  
//                    setCollapsed(false);  
//                }},  
                new FacetValue("ProdFamily1", "Rings", "sum"),  
                new FacetValue("ProdFamily2", "Earrings", "sum"),  
                new FacetValue("ProdFamily3", "Necklaces", "sum"),  
                new FacetValue("ProdFamily4", "Toe Ring", "sum"),
                new FacetValue("ProdFamily5", "Pendants", "sum"),  
                new FacetValue("ProdFamily6", "Bracelets", "sum"),  
                new FacetValue("ProdFamily7", "Anklets", "sum"), 
                new FacetValue("ProdFamily8", "Bangles", "sum"),
//                {{  
//                    setCollapsed(false);  
//                }}, 
                
//                new FacetValue("ProdGroup1", "Photocopy / Lasercopy", "ProdFamily1") {{  
//                    setCollapsed(false);  
//                }},  
//                new FacetValue("ProdGroup2", "Post-it Products", "ProdFamily1"),  
//                new FacetValue("ProdGroup3", "Folders", "ProdFamily2"),  
//                new FacetValue("ProdGroup4", "Overhead Projectors", "ProdFamily3"),  
//                new FacetValue("ProdGroup5", "Printing Supplies", "ProdFamily4"),  
//                new FacetValue("ProdGroup6", "Storage / Accessories", "ProdFamily4"),  
                new FacetValue("Prod01", "Diamond cut silver ring", "ProdFamily1"),  
                new FacetValue("Prod02", "Thin plain silver ring", "ProdFamily1"),  
                new FacetValue("Prod03", "Dense plain silver ring", "ProdFamily1"), 
                
                new FacetValue("Prod04", "Spiral silver earrings", "ProdFamily2"),  
                new FacetValue("Prod05", "Scorpion silver ear cuffs", "ProdFamily2"),
                
                new FacetValue("Prod06", "Silver necklace with star pendant", "ProdFamily3"),  
                new FacetValue("Prod07", "Plain silver necklaces", "ProdFamily3"),  
                
                new FacetValue("Prod08", "Diamond cut toe ring", "ProdFamily4"),  
                new FacetValue("Prod09", "Thin plain toe ring", "ProdFamily4"),  
                new FacetValue("Prod10", "Dense plain toe ring", "ProdFamily4"), 
                
                new FacetValue("Prod11", "Spiral pendant", "ProdFamily5"),  
                new FacetValue("Prod12", "Scorpion pendant", "ProdFamily5"), 
                
                new FacetValue("Prod13", "Spiral bracelet", "ProdFamily6"),  
                new FacetValue("Prod14", "Scorpion anklet", "ProdFamily7"),  
                new FacetValue("Prod15", "Plain silver bangle", "ProdFamily8")
                //,  
//                new FacetValue("Prod16", "654 Green", "ProdGroup2"),  
//                new FacetValue("Prod17", "654 Neon Yellow", "ProdGroup2"),  
//                new FacetValue("Prod18", "Manilla Legal Buff", "ProdGroup3"),  
//                new FacetValue("Prod19", "Manilla Legal Blue", "ProdGroup3"),  
//                new FacetValue("Prod20", "Manilla Legal Red", "ProdGroup3"),  
//                new FacetValue("Prod21", "Manilla Legal Green", "ProdGroup3"),  
//                new FacetValue("Prod22", "Manilla Legal Yellow", "ProdGroup3"),  
//                new FacetValue("Prod23", "OHP Deltascreen", "ProdGroup4"),  
//                new FacetValue("Prod24", "Label Copier 98x38", "ProdGroup5"),  
//                new FacetValue("Prod25", "Label Copier 63x25", "ProdGroup5"),  
//                new FacetValue("Prod26", "Label Laser 63x25", "ProdGroup5"),  
//                new FacetValue("Prod27", "Label Copier Avery", "ProdGroup5"),  
//                new FacetValue("Prod28", "Label Laser Avery", "ProdGroup5"),  
//                new FacetValue("Prod29", "Label Laser/Copier Avery", "ProdGroup5"),  
//                new FacetValue("Prod30", "Labels Inkjet J8666", "ProdGroup5"),  
//                new FacetValue("Prod31", "Labels L7675 Video", "ProdGroup5"),  
//                new FacetValue("Prod32", "Comp. Paper Bhs7GSM", "ProdGroup5"),  
//                new FacetValue("Prod33", "Comp. Paper 15x11", "ProdGroup5"),  
//                new FacetValue("Prod34", "InkJet Cartridge", "ProdGroup5"),  
//                new FacetValue("Prod35", "BubbleJet Cartridge", "ProdGroup5"),  
//                new FacetValue("Prod36", "Laserjet Cartidge", "ProdGroup5"),  
//                new FacetValue("Prod37", "Toner Canon C-f554", "ProdGroup5"),  
//                new FacetValue("Prod38", "OHP Film 3M pp2900", "ProdGroup5"),  
//                new FacetValue("Prod39", "OHP Film Blk/Clr", "ProdGroup5"),  
//                new FacetValue("Prod40", "OHP Film 3M (laser)", "ProdGroup5"),  
//                new FacetValue("Prod41", "Label Floppy 3.5", "ProdGroup5"),  
//                new FacetValue("Prod42", "Label CD", "ProdGroup5"),  
//                new FacetValue("Prod43", "Label Zip", "ProdGroup5"),  
//                new FacetValue("Prod44", "Diskette Box", "ProdGroup6"),  
//                new FacetValue("Prod45", "Label Holders", "ProdGroup6"),  
//                new FacetValue("Prod46", "Keyboard Cover", "ProdGroup6"),  
//                new FacetValue("Prod47", "Pen tidy", "ProdGroup6"),  
//                new FacetValue("Prod48", "Screen Filter 15''", "ProdGroup6"),  
//                new FacetValue("Prod49", "Screen Filter 18''", "ProdGroup6"),  
//                new FacetValue("Prod50", "Screen Mesh 15''", "ProdGroup6")  
        );  
        
        Facet metric = new Facet();  
        metric.setId("metric"); // *
        //metric.setTitle("Zones");  
        //metric.setIsTree(true);  
  
        metric.setValues(  
                new FacetValue("Amount (Baht)", "Amount (Baht)"),  
                new FacetValue("Quantity (unit)", "Quantity (unit)")
        );
        
        //salePerson, metric
        return new Facet[]{zone, time, product, metric};  
    }  
}
