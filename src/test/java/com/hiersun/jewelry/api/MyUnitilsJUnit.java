package com.hiersun.jewelry.api;

import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

@SpringApplicationContext( { 
	"test_db.xml"
})
public class MyUnitilsJUnit extends UnitilsJUnit4 {

	@SpringApplicationContext
	protected static ApplicationContext applicationContext;  
	  


}
