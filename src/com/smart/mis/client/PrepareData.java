package com.smart.mis.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smart.mis.datastore.DataStoreService;
import com.smart.mis.datastore.DataStoreServiceAsync;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PrepareData {
	

	private final static DataStoreServiceAsync dataStoreService = GWT.create(DataStoreService.class);
	
	
	public static void insertAll() {
		
		//For test only
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean result) {
				//if (result) System.out.println("Create Customer successful ---");		
				if (!result) System.out.println("Insert fail ---");	
			}
			
		};
		
		dataStoreService.insertCustomer("CU10001","บริษัท พี.เจ.เจ.จำกัด","(02) 258-8888", "มานิส แสงเทพ", "(089) 222-2244", "webmaster@woeldmark.co.th", "392/21", "สุขุมวิท 20", "คลองเตย", "กรุงเทพ", 10110, "TH", "ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10002","ธำรง แสงน้อย","(089) 477-4443", "ธำรง แสงน้อย", "(089) 477-4443", "tamrong@hotmail.com", "56/12-6 อาคารบิสโก้ ซอยทรัพย์", "ทรัพย์", "บางรัก", "กรุงเทพ", 10110, "TH", "ลูกค้าทั่วไป", "ค้าปลีกผ่านเว็บไซต์", "บุคคลทั่วไป", "http://test1.com", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10001","บริษัท พี.เจ.เจ.จำกัด","(02) 258-8888", "มานิส แสงเทพ", "(089) 222-2244", "webmaster@woeldmark.co.th", "392/21", "สุขุมวิท 20", "คลองเตย", "กรุงเทพ", 10110, "TH", "ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10002","ธำรง แสงน้อย","(089) 477-4443", "ธำรง แสงน้อย", "(089) 477-4443", "tamrong@hotmail.com", "56/12-6 อาคารบิสโก้ ซอยทรัพย์", "ทรัพย์", "บางรัก", "กรุงเทพ", 10110, "TH", "ลูกค้าทั่วไป", "ค้าปลีกผ่านเว็บไซต์", "บุคคลทั่วไป", "http://test1.com", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10003","ร้าน หลิน เครื่องประดับ","(081) 558-2721", "ไกรทอง นุ่มนนต์", "(081) 558-2122", "taechiraya@gmail.com", "90/156 หมู่ 14", "บางกรวย-ไทรน้อย", "บางบัวทอง", "นนทบุรี", 11110, "TH", "ลูกค้าประจำ", "ค้าปลีกผ่านเว็บไซต์", "ร้านค้า", "http://test2.com", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10004","มายด์ แอนด์ ลีน่าเครื่องประดับ","(07) 473-2040", "Andre R.", "(089) 977-7702", "mljewelry_stones@hotmail.com", "2 ซอยบรรจบ สตูลธานี พิมาน", "", "เมืองสตูล", "สตูล", 91000, "TH", "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10005","บริษัท เครื่องประดับอิรอส จำกัด","(02)749-5044", "กนธี วิสุทธิ์", "(02) 749-5044", "", "67/23 หมู่ 12 ซอยอุดมสุข 23", "สุขุมวิท 103","บางนา","กรุงเทพ" ,10260, "TH", "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10006","ร้าน วรรณเครื่องประดับ","(086) 220-0088", "วรรณสา เรืองรอง", "(086) 220-0088", "", "456/7-8 มิตรภาพ", "เมือง", "เมืองขอนแก่น", "ขอนแก่น", 40000, "TH", "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10007","ไอ-ดีไซน์ จิวเวลรี่","(085) 217-8698", "อิทธิพร เลิศพิพัฒน์", "", "itit@gmail.com", "สหกรณ์พระนคร สามเสนใน","พหลโยธินซอย 7","พญาไท","กรุงเทพ", 10400, "TH", "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10008","ประทีปเจมส์","(05) 327-8930", "นิพัทธ์ ครองศักดิ์", "(089) 999-7159", "nipat@gmail.com", "143/3", "หายยา", "เมืองเชียงใหม่", "เชียงใหม่", 50100, "TH", "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10009","สงขลาจิวเวลรี่","(07) 431-3458", "กฤตย์ อดิลักษณ์ ", "(081) 702-4757", "krit@hotmail.com", "1 ซอยทรัพย์สิน บ่อยาง","เพชรคีรี", "เมืองสงขลา", "สงขลา", 90000, "TH", "ลูกค้าทั่วไป", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10010","บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด","(02) 898-0098", "มาโนช แย้มยิ้ม", "", "", "9 ซอยเอกชัย 63","","บางบอน", "กรุงเทพ", 10150, "TH","ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10012","Ying Ge Hai","(089) 284-3499", "ศุภณัฐ กลกานต์", "", "supanut@yahoo.com", "No.2", "TuHua Road", "Pazhou Exhibition Center Area", "Guangzhou", 510000, "CH", "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		dataStoreService.insertCustomer("CU10013","Husa Paseo Del Arte","(02) 374-5566", "Mr. A", "", "", "C/Atocha 123","", "Centro", "Madrid", 28012, "SP", "ลูกค้าทั่วไป", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "ยุโรป", callback);
		dataStoreService.insertCustomer("CU10014","Quatro Puerta del Sol","(02) 374-5566", "Mr. B", "", "", "4", "Sevilla Street", "Centro", "Madrid", 28014, "SP", "ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "ยุโรป", callback);
		dataStoreService.insertCustomer("CU10015","Wyndham Santa Monica","(02) 3745-566", "Mr. C", "", "", "120 Colorado Avenue", "", "Santa Monica / Venice Beach", "Los Angeles (CA)", 90401, "US", "ลูกค้าทั่วไป", "ค้าส่งผ่านเว็บไซต์", "ร้านค้า", "http://test3.com", "อเมริกาเหนือ", callback);
		dataStoreService.insertCustomer("CU10016","Doubletree","(02) 374-5566", "Mr. D", "", "", "21333 Hawthorne Blvd.", "","Torrance / Carson", "Los Angeles (CA)", 90503, "US", "ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "", "อเมริกาเหนือ", callback);
		dataStoreService.insertCustomer("CU10017","Flora Creek","(02) 374-5566", "Ms. Marina White", "", "", "777 Near Deira City Centre", "Port Saeed Road", "Dubai Creek", "Dubai", 3371243 , "TC", "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "", "เอเซีย", callback);
		
		dataStoreService.insertProduct("PD10001","Diamond cut silver ring", "แหวนเงิน คัดลายน้ำ", 6.8, 55.0, "ring" , 300 , 120, "http://lh4.ggpht.com/ktQRyQLXmC37vvDVOs0PedH5fvqQzLHDHzD6rOT7Fwhz-fswBliGY3qP0Ai79h8f_lDBXMrbrMgB5KP2zIrxKLE", 5.0,null,null,null,null,3.0, false, callback);
		dataStoreService.insertProduct("PD10002","Thin plain silver ring", "แหวนเงินเกลี้ยง แบบบาง", 6.8, 50.0, "ring", 200, 200, "http://lh5.ggpht.com/OEqPZYGFLhfbKg_TjXXY2G4LlnZgV8Ydasn_xtE2Ag1ve1YTAycIi6lHPBKDmZKOJj0CWBOyb6acxXuJypwHTJp1", 5.0,null,null,null,null,3.0, false, callback);
		dataStoreService.insertProduct("PD10003","Dense plain silver ring", "แหวนเงินเกลี้ยง แบบหนา", 6.8, 62.0, "ring", 0 , 0, "http://lh4.ggpht.com/eUlY9K1Zedw858kxmkx6g_Atcx8STAm3RbYLcBHMDIx8KwLRWSlwA0YGvbRsDPoodbP9y0mqe8BpeBgZDlgsilEM", 5.0,null,null,null,null,4.0, false, callback);
		dataStoreService.insertProduct("PD10004","Spiral silver earrings", "ต่างหู ตีเกลียวคู่", 6.32, 55.0, "earring", 250, 0, "http://lh3.ggpht.com/QA3XN4rm-YUJa5cYTDrlSTmltS78CUc-c7bQWxFhiilZu130TXLeT6QtJ6Z_A4HnJmLEZcPEKnuiqDll5fggkwOj", null, 0.7, 2.6, null, null, 3.0, true, callback);
		dataStoreService.insertProduct("PD10005","Scorpion silver ear cuffs", "ต่างหู ลงดำ ลายแมงป่อง", 6.32, 50.0, "earring", 200, 50, "http://lh3.ggpht.com/S6dsgFS2BomMuNBIU8DAThTm83-mu3Wr50_EHWbpqxDaEIbXHAvbYS6gAh9S5CxlVdvAsw7pUsciQAh3jPmj2VU", null, null, null, 7.0, 10.0, 3.0, false, callback);
		dataStoreService.insertProduct("PD10006","Silver necklace with star pendant", "สร้อยคอ พร้อมจี้รูปดาว", 6.32, 55.0, "necklace" , 100, 0, "http://lh4.ggpht.com/9mW7YapodtKfhcJQwIAXvHRulEmU0LP0CEVsICh8Ykijw-yIV5UvqfHp03zlubSDSUu0K4T7FcPSb5n5C4ggby0", null, null, 45.5, 7.0, 10.0, 3.0, true, callback);
		dataStoreService.insertProduct("PD10007","Plain silver necklaces", "สายสร้อย โซ่คร้อง", 6.32, 50.0, "necklace", 100, 0, "http://lh3.ggpht.com/3MowBFjEbea2M1eVBBoM1q1LC_t-mvjmBDcDMDxnMnnOR7PFOvQ4dfLUKPNSYHam9VvwzAhr5ULzjFbKtUMkdQ", null, null, 50.0, null, null, 3.0, false, callback);
		dataStoreService.insertProduct("PD10008","Diamond cut toe ring", "แหวนนิิ้วเท้า คัดลายน้ำ", 6.8, 55.0, "toe ring" , 50 , 10, "http://lh3.ggpht.com/R0tT50QBJQ2vORBpBlsUcIw1wjszdCqPL8ASJlb85qknBmU7JFsPi-gUrp745SkvmdU0VM89SOmAfKFeUv9xebY", 5.0,null,null,null,null,3.0, false, callback);
		dataStoreService.insertProduct("PD10009","Thin plain toe ring", "แหวนนิิ้วเท้า แบบบาง", 6.8, 50.0, "toe ring", 50, 10, "http://lh3.ggpht.com/mll6ZrBykEcu6ESETgvRbbVMOZaerpxTHTWqEeAhgRDaPjf0SCIPWfSe_4d5k997JvmYlFfAF9cwFrJoDqGEQ9o", 5.0,null,null,null,null,3.0, false, callback);
		dataStoreService.insertProduct("PD10010","Dense plain toe ring", "แหวนนิิ้วเท้า แบบหนา", 6.8, 62.0, "toe ring", 50 , 0, "http://lh4.ggpht.com/RIOqiQeSbobLeRCgfqbnych23cRhSldySsd51XdtPl9yeH6DBPitZusqAaUuRDFVeEXEV1g0OSAoFvCQ-keUenE", 5.0,null,null,null,null,4.0, true, callback);
		dataStoreService.insertProduct("PD10011","Spiral pendant", "จี้ ตีเกลียวคู่", 6.32, 55.0, "pendant", 100, 0, "http://lh3.ggpht.com/kR6VmXML7n9PZ7PxLw9BJ8FROHjGBGacMvXIu2lBYOIFK5hk4MozyDYGKA_QMF7pJQ0oQQktfflY5QrrIsQaxds", null, 0.7, 2.6, null, null, 3.0, false, callback);
		dataStoreService.insertProduct("PD10012","Scorpion pendant", "จี้ ลงดำ ลายแมงป่อง", 6.32, 50.0, "pendant", 100, 50, "http://lh5.ggpht.com/ZRAXpCSF4r34-xksmN1B2TkhFUu11GJjwnDjqHHiuqm00b18VMuH_3reOpbeW19JV-GV539m8CQ_-4Hop3yCQTs", null, null, null, 7.0, 10.0, 3.0, false, callback);
		dataStoreService.insertProduct("PD10013","Spiral bracelet", "กำไลข้อมือ ตีเกลียวคู่", 6.32, 55.0, "bracelet", 150, 0, "http://lh4.ggpht.com/uBtxtIxrBpBIm50rf3T30lNDihTwOLnWkCZaCtu1nwQ7tzFGoE3pzyzfEnR4nLVq2cRyBFssmZCA3WC3jKXlD-k", null, 0.7, 2.6, null, null, 3.0, false, callback);
		dataStoreService.insertProduct("PD10014","Scorpion anklet", "กำไลข้อเท้า ลงดำ ลายแมงป่อง", 6.32, 50.0, "anklet", 120, 50, "http://lh4.ggpht.com/qWy1lqJeh1YcsRYZzyM-u55z5e1hHtasreeaRvZd8qo7zRrBm65zpZqJCF8-RlpAb9j-Dw2Pyu2i8E8QLJSPjj-2", null, null, null, 7.0, 10.0, 3.0, false, callback);
		dataStoreService.insertProduct("PD10015","Plain silver bangle", "สร้อยข้อเท้า ธรรมดา", 6.32, 50.0, "bangle", 75, 0, "http://lh5.ggpht.com/odtKYjc-2oxEzapmNHo9zLEymSr6mzyGYJ-T6uX7oQ0CapusDZg9C2ilX5VlMgzdIFaQf24SJIJa0hzNevljSfo", null, null, 50.0, null, null, 3.0, true, callback);
		
		dataStoreService.insertSmith("SM10001","จำนงค์ คำเงิน","(02) 258-8888", "", "test_1@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "แต่งและฝังพลอยประดับ", callback);
		dataStoreService.insertSmith("SM10002","อุดมพร แสงคำ","(02) 258-7777", "", "test_2@smith.co.th", "453", "สุขุมวิท 46","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์", callback);
		dataStoreService.insertSmith("SM10003","โรงหล่อ บุษราคัม จิวเวอรี่","(02) 454-4963", "(081) 843-3075", "test_3@smith.co.th", "777/123", "พระรามสี่","คลองเตย", "กรุงเทพ",10110 , "หล่อขึ้นรูป", callback);
		dataStoreService.insertSmith("SM10004","มณี แสงดาว","(02) 258-9999", "", "test_4@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "แต่งและฝังพลอยประดับ", callback);
		dataStoreService.insertSmith("SM10005","ไพลิน มณีจันทร์","(02) 258-2222", "", "test_5@smith.co.th", "532", "สุขุมวิท 101","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์", callback);
		dataStoreService.insertSmith("SM10006","โรงหล่อ พรหมรังสี","(02) 454-4964", "(081) 843-3076", "test_6@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ",10110 , "หล่อขึ้นรูป", callback);
		dataStoreService.insertSmith("SM10007","มุข พินิจ","(02) 258-4444", "", "test_7@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "แต่งและฝังพลอยประดับ", callback);
		dataStoreService.insertSmith("SM10008","นัท หมูทอง","(02) 258-5555", "", "test_8@smith.co.th", "392/21", "สุขุมวิท 20","คลองเตย", "กรุงเทพ", 10110, "ขัดและติดพลอยแมกกาไซต์", callback);
		dataStoreService.insertSmith("SM10009","โรงหล่อ ศิลป์เดชา","(02) 454-4965", "(081) 843-3077", "test_9@smith.co.th", "16/6", "","ปากเกล็ด", "กรุงเทพ",11120 , "หล่อขึ้นรูป", callback);
		
		dataStoreService.insertMaterial("MA10001","แร่เงิน 100%","แร่เงิน บริสุทธิ์ 100%", "แร่เงิน", "กรัม", null, 100000.0, 128302.0, 75000.0, callback); //required
		dataStoreService.insertMaterial("MA10002","แร่เงิน 92.5%","แร่เงิน 92.5%", "แร่เงิน", "กรัม", null, 100000.0, 278392.0, 75000.0, callback); //required
		dataStoreService.insertMaterial("MA20001","แมกกาไซต์ PP6","แมกกาไซต์  เบอร์ 6", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 25700.0, 2000.0, callback);
		dataStoreService.insertMaterial("MA20002","แมกกาไซต์ PP7","แมกกาไซต์  เบอร์ 7", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 20000.0, 1800.0, callback);
		dataStoreService.insertMaterial("MA20003","แมกกาไซต์ PP8","แมกกาไซต์  เบอร์ 8", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 25000.0, 7200.0, callback);
		dataStoreService.insertMaterial("MA20004","แมกกาไซต์ PP12","แมกกาไซต์  เบอร์ 12", "แมกกาไซต์", "เม็ด", 0.12, 1000.0, 12500.0, 1000.0, callback);
		dataStoreService.insertMaterial("MA20005","ถุงพลาสติก 6x6","ถุงพลาสติก ขนาด 6x6 ซม.", "อื่นๆ", "ถุง", 0.12, 1000.0, 50000.0, 30000.0, callback);
		dataStoreService.insertMaterial("MA20006","ถุงพลาสติก 7x7","ถุงพลาสติก ขนาด 7x7 ซม.", "อื่นๆ", "ถุง", 0.14, 1000.0, 50000.0, 30000.0, callback);
		dataStoreService.insertMaterial("MA20007","บุษราคัม","Yellow Sapphire", "พลอยประดับ", "เม็ด", 0.12, 0.0, 80.0, 0.0, callback);
		dataStoreService.insertMaterial("MA20008","โทแพซสีเหลือง","Yellow Topaz", "พลอยประดับ", "เม็ด", 0.12, 0.0, 100.0, 0.0, callback);
		dataStoreService.insertMaterial("MA20009","โอปอล","Opal", "พลอยประดับ", "เม็ด", 0.12, 0.0, 120.0, 0.0, callback);
		dataStoreService.insertMaterial("MA20010","เพทายสีเหลือง","Yellow Zircon", "พลอยประดับ", "เม็ด", 0.12, 0.0, 0.0, 0.0, callback);
		dataStoreService.insertMaterial("MA20011","อำพัน","Amber", "พลอยประดับ", "เม็ด", 0.12, 0.0, 0.0, 0.0, callback);
		dataStoreService.insertMaterial("MA20012","หยกสีเหลือง","Yellow Jade", "พลอยประดับ", "เม็ด", 0.12, 0.0, 0.0, 0.0, callback);
		
		dataStoreService.insertSuppiler("SU10001","บริษัท 99GOLDS","(02) 258-8888", "(089) 222-2244", "webmaster@woeldmark.co.th", "392/21","สุขุมวิท 20", "คลองเตย", "กทม", 10110, "(02) 345-6124", 7, "MA10001|MA10002", callback);
		dataStoreService.insertSuppiler("SU10002","ห้างทอง น่ำเชียง","(089) 477-4443", "(089) 477-4443", "tamrong@hotmail.com", "56/12-6 อาคารบิสโก้ ซอยทรัพย์", "ถนนทรัพย์", "บางรัก", "กทม", 10500, "(02) 345-6124", 7, "MA10001|MA10002", callback);
		dataStoreService.insertSuppiler("SU10003","ร้าน วีวี่เจมส์","(081) 558-2721", "(081) 558-2122", "taechiraya@gmail.com", "90/156 หมู่ 14", "บางกรวย-ไทรน้อย", "บางบัวทอง", "นนทบุรี", 11110, "(02) 345-6124", 15, "MA20001|MA20002|MA20003", callback);
		dataStoreService.insertSuppiler("SU10004","มายด์ แอนด์ ลีน่าเครื่องประดับ","(07) 473-2040", "(089) 977-7702", "mljewelry_stones@hotmail.com", "2 ซอยบรรจบ สตูลธานี", "",  "เมืองสตูล", "สตูล", 91000, "(02) 345-6124", 15, "MA20001|MA20002|MA20003", callback);
		dataStoreService.insertSuppiler("SU10005","บริษัท เครื่องประดับอิรอส จำกัด","(02) 749-5044", "(02) 749-5044", "", "67/23 หมู่ 12 ซอยอุดมสุข 23", "สุขุมวิท 103", "บางนา", "กทม", 10260, "(02) 345-6124", 3 , "MA20001|MA20002|MA20003", callback);
		
		
		dataStoreService.insertQuotation("QA10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1360.0, 200, 11000.0, new Date(), null, "สมบัติ ยอดขาย", null, "", "2_waiting_for_approved", callback);
		dataStoreService.insertQuotation("QA10002","CU10008", "ประทีปเจมส์", "เงินสด", 0, "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date(), new Date(), new Date() , 1836.0, 270, 14850.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved", callback);
		dataStoreService.insertQuotation("QA10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1896.0, 300, 16500.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved", callback);
		dataStoreService.insertQuotation("QA10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 632.0, 100, 5000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "4_canceled", callback);
		dataStoreService.insertQuotation("QA10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1360.0, 200, 11000.0, new Date(), null, "สมบัติ ยอดขาย", null, "", "2_waiting_for_approved", callback);
		dataStoreService.insertQuotation("QA10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1836.0, 270, 14850.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved", callback);
		dataStoreService.insertQuotation("QA10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1896.0, 300, 16500.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "2_waiting_for_approved", callback);
		dataStoreService.insertQuotation("QA10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 632.0, 100, 5000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved", callback);
		dataStoreService.insertQuotation("QA10009","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1360.0, 200, 11000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised", callback);
		dataStoreService.insertQuotation("QA10010","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1836.0, 270, 14850.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved", callback);
		dataStoreService.insertQuotation("QA10011","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 1896.0, 300, 16500.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "1_waiting_for_revised", callback);
		dataStoreService.insertQuotation("QA10012","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date(), new Date(), new Date() , 632.0, 100, 5000.0, new Date(), null, "สมบัติ ยอดขาย", "ภักดิ์ทูล ใจทอง", "", "3_approved", callback);
		
		dataStoreService.insertQuotationItem("QS10011","QA10001", "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true, callback);
		dataStoreService.insertQuotationItem("QS10051","QA10005", "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true, callback);
		dataStoreService.insertQuotationItem("QS10091","QA10009", "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true, callback);
		dataStoreService.insertQuotationItem("QS10022","QA10002", "PD10001", "Diamond cut silver ring", 476.0, 55.0, "ring","วง", 70, true, callback);
		dataStoreService.insertQuotationItem("QS10023","QA10002", "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true, callback);
		dataStoreService.insertQuotationItem("QS10062","QA10006", "PD10001", "Diamond cut silver ring", 476.0, 55.0, "ring","วง", 70, true, callback);
		dataStoreService.insertQuotationItem("QS10063","QA10006", "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true, callback);
		dataStoreService.insertQuotationItem("QS10102","QA10010", "PD10001", "Diamond cut silver ring", 476.0, 55.0, "ring","วง", 70, true, callback);
		dataStoreService.insertQuotationItem("QS10103","QA10010", "PD10002", "Thin plain silver ring",1360.0, 55.0, "ring","วง", 200, true, callback);
		dataStoreService.insertQuotationItem("QS10034","QA10003", "PD10004","Spiral silver earrings", 1896.0, 55.0, "earring", "คู่", 300, true, callback);
		dataStoreService.insertQuotationItem("QS10074","QA10007", "PD10004","Spiral silver earrings", 1896.0, 55.0, "earring", "คู่", 300, true, callback);
		dataStoreService.insertQuotationItem("QS10114","QA10011", "PD10004","Spiral silver earrings", 1896.0, 55.0, "earring", "คู่", 300, true, callback);
		dataStoreService.insertQuotationItem("QS10045","QA10004", "PD10007","Plain silver necklaces", 632.0, 50.0, "necklace","เส้น",100, true, callback);
		dataStoreService.insertQuotationItem("QS10085","QA10008", "PD10007","Plain silver necklaces", 632.0, 50.0, "necklace","เส้น",100, true, callback);
		dataStoreService.insertQuotationItem("QS10125","QA10012", "PD10007","Plain silver necklaces", 632.0, 50.0, "necklace","เส้น",100, true, callback);
		
		dataStoreService.insertSale("SO10001","QA10001", "IN10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_production", "PO1111", new Date(), callback);
		dataStoreService.insertSale("SO10002","QA10002", "IN10002","CU10008", "ประทีปเจมส์", "เงินสด", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_production_in_progress", "PO1112", new Date(), callback);
		dataStoreService.insertSale("SO10003","QA10003", "IN10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "6_canceled", "PO1113", new Date(), callback);
		dataStoreService.insertSale("SO10004","QA10004", "IN10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_production_in_progress", "PO1114", new Date(), callback);
		dataStoreService.insertSale("SO10005","QA10005", "IN10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "3_production_completed", "PO1177", new Date(), callback);
		dataStoreService.insertSale("SO10006","QA10006", "IN10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "4_on_delivery", "PO1179", new Date(), callback);
		dataStoreService.insertSale("SO10007","QA10007", "IN10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "4_on_delivery", "PO1126", new Date(), callback);
		dataStoreService.insertSale("SO10008","QA10008", "IN10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "5_delivery_completed", "PO1178", new Date(), callback);
		
		dataStoreService.insertInvoice("IN10001","SO10001","CU10017", "Flora Creek", "เงินสด", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1111", new Date(), null, callback);
		dataStoreService.insertInvoice("IN10002","SO10002","CU10008", "ประทีปเจมส์", "เงินสด", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1112", new Date(), null, callback);
		dataStoreService.insertInvoice("IN10003","SO10003","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "เงินสด", 20 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "4_canceled", "PO1113", new Date(), null, callback);
		dataStoreService.insertInvoice("IN10004","SO10004","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "เงินสด", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1114", new Date(), null, callback);
		dataStoreService.insertInvoice("IN10005","SO10005","CU10017", "Flora Creek", "แคชเชียร์เช็ค", 30 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย", new Date() , 1360.0, 200, 11000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1177", new Date(), null, callback);
		dataStoreService.insertInvoice("IN10006","SO10006","CU10008", "ประทีปเจมส์", "แคชเชียร์เช็ค", 0 , "ลูกค้าทั่วไป", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1836.0, 270, 14850.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_paid", "PO1179", new Date(), new Date(), callback);
		dataStoreService.insertInvoice("IN10007","SO10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", "แคชเชียร์เช็ค", 10 , "ลูกค้าประจำ", "ค้าปลีกผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 1896.0, 300, 16500.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_waiting_for_payment", "PO1126", new Date(), null, callback);
		dataStoreService.insertInvoice("IN10008","SO10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", "แคชเชียร์เช็ค", 10 ,"ลูกค้าประจำ", "ค้าส่งผ่านหน้าร้าน", "ร้านค้า", "เอเซีย" , new Date() , 632.0, 100, 5000.0, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_paid", "PO1178", new Date(), new Date(), callback);
		
    	dataStoreService.insertDelivery("DL10002","SO10007","IN10007","CU10004", "มายด์ แอนด์ ลีน่าเครื่องประดับ", new Date() , 1896.0, 300, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "1_on_delivery", "1_product_issued", new Date(), null, "", callback);
    	dataStoreService.insertDelivery("DL10003","SO10008","IN10008","CU10010", "บริษัท บิ๊กซิลเวอร์ แมนูแฟคเจอร์ริ่ง จำกัด", new Date() , 632.0, 100, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "2_delivery_completed", "1_product_issued", new Date(), new Date(), "RC102142", callback);
		dataStoreService.insertDelivery("DL10001","SO10006","IN10006","CU10008", "ประทีปเจมส์", new Date() , 1836.0, 270, new Date(), null, "ภักดิ์ทูล ใจทอง", null, "0_product_request", "0_product_request", new Date(), null, "", callback);
	}
}
