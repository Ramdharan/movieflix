package com.egen.movieflix.component;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.egen.movieflix.service.DataFillingService;
/**
 * Used to read movielist json file from classpath and also insert root data
 * @author ramdharandonda
 *
 */
@Component
public class DatabaseFiller implements ApplicationListener<ContextRefreshedEvent> {
private boolean isDataInserted=false;
@Value(value = "classpath:movielist.json")
private Resource moviedata;

@Autowired
DataFillingService service;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		//Just to make sure that data is not inserted again.
		//As this is Singleton, object state does not change.
		//After inserting data for first time isDataInsterted will be set to true.
		if(!isDataInserted){
			isDataInserted=true;
			try {
File file=moviedata.getFile();
service.insertCatalogData(file);
service.insertAdminUser();
			
			} catch (IOException e) {
				e.printStackTrace();	}
			
		}
	}
	
}
