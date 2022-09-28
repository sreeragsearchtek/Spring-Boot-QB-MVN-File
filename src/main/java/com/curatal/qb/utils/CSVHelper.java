package com.curatal.qb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curatal.qb.entity.EasyQuestions;
import com.curatal.qb.entity.ExpertQuestions;
import com.curatal.qb.entity.IntermediateQuestions;
import com.curatal.qb.entity.Skills;
import com.curatal.qb.service.SkillService;

@Service
public class CSVHelper {
	
	@Autowired
	private SkillService skillService;

	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "Id", "Title", "Description", "Published" };

	  public static boolean hasCSVFormat(MultipartFile file) {
	    if (TYPE.equals(file.getContentType())
	    		|| file.getContentType().equals("application/vnd.ms-excel")) {
	      return true;
	    }

	    return false;
	  }
	  
	  //Bulk Upload Easy Questions By CSV
	  public static List<EasyQuestions> setEasyQuestionsByCSV(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<EasyQuestions> easyQuestionsList = new ArrayList<>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

		      for (CSVRecord csvRecord : csvRecords) {
		    	  EasyQuestions easyQuestions = new EasyQuestions(
		              csvRecord.get("Skill"),
		              csvRecord.get("Question")
		           );

		    	  easyQuestionsList.add(easyQuestions);
		      }

		      return easyQuestionsList;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
	  }
	  
	  //Bulk Upload Intermediate Questions By CSV
	  public static List<IntermediateQuestions> setIntermediateQuestionsByCSV(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<IntermediateQuestions> intermediateQuestionsList = new ArrayList<>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

		      for (CSVRecord csvRecord : csvRecords) {
		    	  IntermediateQuestions intermediateQuestions = new IntermediateQuestions(
		              csvRecord.get("Skill"),
		              csvRecord.get("Question")
		            );

		    	  intermediateQuestionsList.add(intermediateQuestions);
		      }

		      return intermediateQuestionsList;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
	  }
	  
	  //Bulk Upload Expert Questions By CSV
	  public static List<ExpertQuestions> setExpertQuestionsByCSV(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<ExpertQuestions> expertQuestionsList = new ArrayList<>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

		      for (CSVRecord csvRecord : csvRecords) {
		    	  ExpertQuestions expertQuestions = new ExpertQuestions(
		              csvRecord.get("Skill"),
		              csvRecord.get("Question")
		            );

		    	  expertQuestionsList.add(expertQuestions);
		      }

		      return expertQuestionsList;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
	  }
	  
	  //Bulk Upload Skills By CSV
	  public List<Skills> setSkillsByCSV(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<Skills> skillsList = new ArrayList<>();
		      
		      List<String> skillsListDTO = new ArrayList<>();
		      
		      String skillsArrayList[] = skillService.getAllSkills().toArray(new String[0]);

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

		      for (CSVRecord csvRecord : csvRecords) {

		    	  int duplicate_status = 0;
		    	  
		    	  for(int i=0;i<skillsArrayList.length;i++) {
		    		  if(skillsArrayList[i].equals(csvRecord.get("Skill"))) {
		    			  duplicate_status = 1;
		    		  }
		    		  else {
		    			  
		    		  }
		    	  }
		    	  
		    	  if(duplicate_status == 1) {
		    		  
		    	  }else {
		    		  skillsListDTO.add(csvRecord.get("Skill"));
		    	  }
		      }
		      
		      Set<String> set = new HashSet<>(skillsListDTO);
		      skillsListDTO.clear();
		      skillsListDTO.addAll(set);
		    
		      
		      for(int j=0;j<skillsListDTO.size();j++) {
		    	  Skills skills = new Skills();
		    	  skills.setSkill(skillsListDTO.get(j));
		    	  skillsList.add(skills);
		      } 

		      return skillsList;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
	  }
	  
}
