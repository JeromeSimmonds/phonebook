package com.jeromesimmonds.phonebook.core.dao;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Jerome Simmonds
 *
 */
public class DBUtils {
	
	private JdbcTemplate template;
	private static final Object LOCK = new Object();
	
	public void setDataSource(DataSource datasource) {
		template = new JdbcTemplate(datasource);
	}

	public synchronized void validateDatabase() {
		synchronized (LOCK) {	
			int count = template.queryForInt("SELECT count(*) FROM users");
			if (count > 0) return;
			LineNumberReader reader = null;
			FileReader oFR = null;
			try {
				oFR = new FileReader("src/test/db/testdata.sql");
				reader = new LineNumberReader(oFR);
				String oLine = null;
				StringBuffer oCommand = new StringBuffer();
				String oDelimiter = ";";	        
				while ((oLine = reader.readLine()) != null) {
					String oCleanLine = clean(oLine);				
					if (oCleanLine.length() < 1) {
						
					} else if (oCleanLine.startsWith("--") || oCleanLine.startsWith("#") || oCleanLine.startsWith("//")) {
	                    //logger.debug(trimmedLine);
	                } else if (oCleanLine.matches("^DELIMITER .*")) {                	
	                	oDelimiter = oCleanLine.substring(10).trim();                	
	                } else {
	                    if (oCleanLine.endsWith(oDelimiter)) {
	                    	oCommand.append(oCleanLine.substring(0, oCleanLine.lastIndexOf(oDelimiter)));
	                    	template.update(oCommand.toString());
	                    	oCommand = new StringBuffer();
	                    } else {
	                    	oCommand.append(oCleanLine).append(" ");
	                    }
	                }
				}
				System.out.println(">> Database initialized...");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) try { reader.close(); } catch (Exception e) {} 
				if (oFR != null) try { oFR.close(); } catch (Exception e) {}
			}
		}
	}

	private String clean(String line) {
		String result = line.replaceAll("-- .*", "");
		return result.trim();
	}
}