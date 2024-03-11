package com.task;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * here is one csv file.  you need to write a program to how many distinct "lineItem/ProductCode" *values exists and for each of these compute the cost.  lineItem/UnblendedCost indicate what is *cost. bill/BillingPeriodStartDate and bill/BillingPeriodEndDate indicate how long it was used.
 */

public class App 
{
    @SuppressWarnings("deprecation")





    
    public static void calculateCost() throws IOException{
        Map<String,Double> productCost=new HashMap<>();
        try(
            Reader reader=new FileReader(new File("data.csv")); 
            CSVParser csvParser= CSVFormat.DEFAULT.withHeader().parse(reader);
        ){
            
            for(CSVRecord record:csvParser){
                
                String productcode=record.get("lineItem/ProductCode" );
    
                Double cost=Double.parseDouble(record.get("lineItem/UnblendedCost"));
    
                LocalDateTime startDateTime=LocalDateTime.parse(record.get("bill/BillingPeriodStartDate"),DateTimeFormatter.ISO_DATE_TIME);
                
                LocalDateTime endDateTime=LocalDateTime.parse(record.get("bill/BillingPeriodEndDate"),DateTimeFormatter.ISO_DATE_TIME);
    
                Long totalDays=ChronoUnit.DAYS.between(startDateTime, endDateTime);
                
                Double total;
                if(productCost.get(productcode)!=null){
                   total=productCost.get(productcode)+(cost*totalDays);
                }else{
                    total=(cost*totalDays);
                }
                productCost.put(productcode, total);
              
            }
        }
        for(Map.Entry<String,Double> entry:productCost.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
    public static void main( String[] args ) 
    {
        try {
            calculateCost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
