package org.data2semantics.yasgui.analysis.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import org.data2semantics.yasgui.analysis.Query;
import org.data2semantics.yasgui.analysis.QueryCollection;

import au.com.bytecode.opencsv.CSVReader;

public class EndpointTypes extends AnalysisHelper {
	
	private static String MONDECA_CSV = "input/mondeca_availability.csv";
	private static int[] CSV_KEYS_AVAILABILITY = new int[]{1,2,3};
	private static int CSV_KEY_DATASET = 0;
	ArrayList<String> mondecaEndpoints = new ArrayList<String>();
	public EndpointTypes(QueryCollection queryCollection) {
		super(queryCollection);
	}

	public void calc() throws IOException, ParseException {
		getMondecaEndpoints();
		for (Query query: queryCollection.getQueries()) {
			
		}
		
	}
	
	
	public void getMondecaEndpoints() throws IOException, ParseException {
		BufferedReader in = new BufferedReader(new FileReader(MONDECA_CSV));
		
        CSVReader reader = new CSVReader(in,';');
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
        	boolean available = true;
        	NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            
        	for (int rowKey: CSV_KEYS_AVAILABILITY) {
        		Number number = format.parse(nextLine[rowKey]);
        		double d = number.doubleValue();
        		if (d == 0) {
        			available = false;
        		}
        		
        	}
        	if (available) {
        		mondecaEndpoints.add(nextLine[CSV_KEY_DATASET]);
    		}
        }
        in.close();
        reader.close();
	}
	
	public static void main(String[] args) {
	}
}
