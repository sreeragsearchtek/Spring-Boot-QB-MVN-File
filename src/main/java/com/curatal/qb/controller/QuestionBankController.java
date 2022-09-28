package com.curatal.qb.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.curatal.qb.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.curatal.qb.service.EasyQuestionsService;
import com.curatal.qb.service.ExpertQuestionsService;
import com.curatal.qb.service.IntermediateQuestionsService;
import com.curatal.qb.service.SkillService;
import com.curatal.qb.utils.CSVHelper;
import com.curatal.qb.utils.ResponseMessage;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class QuestionBankController {
	
	@Autowired
	private EasyQuestionsService easyQuestionsService;
	
	@Autowired
	private IntermediateQuestionsService intermediateQuestionsService;
	
	@Autowired
	private ExpertQuestionsService expertQuestionsService;
	
	@Autowired
	private SkillService skillService;

	//View Admin Dashboard
	@GetMapping("/viewAdminDashboard")
	public String viewAdminHomePage() {
		return "admin_dashboard";
	}

	//View Interviewer Questions Page
	@GetMapping("/")
	public String viewInterviewerQuestionsPage(Model model) {
		model.addAttribute("skillsList", skillService.getSkillsList());
		return "interviewer_questions_list";
	}

	//View Admin Questions Page
	@GetMapping("/viewAdminQuestionsPage")
	public String viewAdminQuestionsPage(Model model) {
		model.addAttribute("skillsList", skillService.getSkillsList());
		return "admin_questions_list";
	}
	
	//View Add Question Page
	@GetMapping("/addQuestionPage")
	public String questionFormSubmit(Model model) {
		model.addAttribute("skillsList", skillService.getSkillsList());
//		EasyQuestions easyQuestions = new EasyQuestions();
//		IntermediateQuestions intermediateQuestions = new IntermediateQuestions();
//		ExpertQuestions expertQuestions = new ExpertQuestions();
//		model.addAttribute("easyQuestions", easyQuestions);
//		model.addAttribute("intermediateQuestions", intermediateQuestions);
//		model.addAttribute("expertQuestions", expertQuestions);
		return "add_question";
	}

	//View Bulk Upload Question Page
	@GetMapping("/bulkUploadQuestionsPage")
	public String bulkUploadQuestionForm(Model model) {
		return "bulk_upload_questions";
	}

	//View Add Skill Page
	@GetMapping("/addSkillsPage")
	public String skillsFormSubmit(Model model) {
//		Skills skills = new Skills();
//		model.addAttribute("skills", skills);
		return "add_skill";
	}

	//Add Single Skill
	@PostMapping("/addSkill")
	public String saveSkill(@ModelAttribute("skills") Skills skills) {
		skillService.saveSkill(skills);
		return "redirect:/addSkillsPage";
	}
	
	//Add Single Easy Question
	@PostMapping("/addEasyQuestion")
	public String saveEasyQuestion(@ModelAttribute("easyQuestions") EasyQuestions easyQuestions) {
		easyQuestionsService.saveEasyQuestion(easyQuestions);
		return "redirect:/addQuestionPage";
	}

	//Add Single Intermediate Question
	@PostMapping("/addIntermediateQuestion")
	public String saveIntermediateQuestion(@ModelAttribute("intermediateQuestions") IntermediateQuestions intermediateQuestions) {
		intermediateQuestionsService.saveIntermediateQuestion(intermediateQuestions);
		return "redirect:/addQuestionPage";
	}

	//Add Single Expert Question
	@PostMapping("/addExpertQuestion")
	public String saveExpertQuestion(@ModelAttribute("expertQuestions") ExpertQuestions expertQuestions) {
		expertQuestionsService.saveExpertQuestion(expertQuestions);
		return "redirect:/addQuestionPage";
	}
	
	//Add Multiple Skills
	@RequestMapping(value = "/saveAllSkills/{skills}", method = RequestMethod.POST, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Skills>> saveAllSkill(@PathVariable String skills) {
		try {
			
			List<Skills> skillsList = new ArrayList<>();

			String skillsArrayList[] = skillService.getAllSkills().toArray(new String[0]);
			
			if(skills.contains(",")) {
				
				List<String> skillsListDTO = new ArrayList<>();
				
				String skillStrArray[] = skills.split(",");
				
				for(int i=0;i<skillStrArray.length;i++) {
					
					int duplicateSkillStatus = 0;
	        		
	        		for (int j=0;j<skillsArrayList.length;j++) {
	    				if(skillsArrayList[j].equals(skillStrArray[i])) {
	    					duplicateSkillStatus = 1;
	    				}else {
	    					
	    				}
	    			}
					
					if(duplicateSkillStatus == 1) {
						
					}else {

						skillsListDTO.add(skillStrArray[i]);
						
					}
					
				}
				
				Set<String> skillListSet = new HashSet<>(skillsListDTO);
				skillsListDTO.clear();
				skillsListDTO.addAll(skillListSet);
				
				String skillsArrayListDTO[] = skillsListDTO.toArray(new String[0]);
				
				for (int k=0;k<skillsArrayListDTO.length;k++) {
					
					Skills skill = new Skills();
					skill.setSkill(skillsArrayListDTO[k]);
					skillsList.add(skill);	
						
				}
				
			}else {
				
				int duplicateSkillStatus = 0;
        		
        		for (int j=0;j<skillsArrayList.length;j++) {
    				if(skillsArrayList[j].equals(skills)) {
    					duplicateSkillStatus = 1;
    				}else {
    					
    				}
    			}
				
				if(duplicateSkillStatus == 1) {
					
				}else {
					Skills skill = new Skills();
					skill.setSkill(skills);
					skillsList.add(skill);
				}
				
			}

			ResponseEntity<List<Skills>> responseEntity = new ResponseEntity<List<Skills>>(skillService.saveAllSkills(skillsList), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<Skills>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	//Add Multiple Easy Questions
	@RequestMapping(value = "/saveAllEasyQuestions", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<List<EasyQuestions>> saveAllEasyQuestions(@RequestBody HashMap<String, String> jsonValues, HttpSession session) {
	    try {
	    	
	    	List<EasyQuestions> easyQuestionsList = new ArrayList<>();

			String easyQuestionsArrayList[] = easyQuestionsService.getAllEasyQuestions().toArray(new String[0]);
	    	
//	    	System.out.println("operation: "+jsonValues); 
	        String skillStr = String.valueOf(jsonValues.get("skill_name"));  
	        String easyQuestionsStr = String.valueOf(jsonValues.get("easy_questions"));
	        
	        if(easyQuestionsStr.contains("#$#")) {
	        	
	        	List<String> questionsListDTO = new ArrayList<>();
	        	
	        	String easyQuestionsStrArray[] = easyQuestionsStr.split("\\#\\$\\#");
	        	
	        	for(int i=0;i<easyQuestionsStrArray.length;i++) {
	        		
	        		int duplicateQuestionStatus = 0;
	        		
	        		for (int j=0;j<easyQuestionsArrayList.length;j++) {
	    				if(easyQuestionsArrayList[j].equals(easyQuestionsStrArray[i])) {
	    					duplicateQuestionStatus = 1;
	    				}else {
	    					
	    				}
	    			}
					
	        		
					if(duplicateQuestionStatus == 1) {
						
					}else {
						
						questionsListDTO.add(easyQuestionsStrArray[i]);
						
					}
					
				}
	        	
	        	
	        	
	        	Set<String> questionsListSet = new HashSet<>(questionsListDTO);
	        	questionsListDTO.clear();
	        	questionsListDTO.addAll(questionsListSet);
				
				String questionsArrayListDTO[] = questionsListDTO.toArray(new String[0]);
				
				for (int k=0;k<questionsArrayListDTO.length;k++) {
					
					EasyQuestions easyQuestions = new EasyQuestions();
					easyQuestions.setSkill(skillStr);
					easyQuestions.setQuestion(questionsArrayListDTO[k]);
					easyQuestionsList.add(easyQuestions);	
						
				}
	        	
	        }
	        else {
	        	
	        	int duplicateQuestionStatus = 0;
        		
        		for (int j=0;j<easyQuestionsArrayList.length;j++) {
    				if(easyQuestionsArrayList[j].equals(easyQuestionsStr)) {
    					duplicateQuestionStatus = 1;
    				}else {
    					
    				}
    			}
        		
        		EasyQuestions easyQuestions = new EasyQuestions();
        		
        		if(duplicateQuestionStatus == 1) {
					
				}else {
					easyQuestions.setSkill(skillStr);
					easyQuestions.setQuestion(easyQuestionsStr);
					easyQuestionsList.add(easyQuestions);
				}
					
	        }

	        ResponseEntity<List<EasyQuestions>> responseEntity = new ResponseEntity<List<EasyQuestions>>(easyQuestionsService.saveAllEasyQuestions(easyQuestionsList), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<EasyQuestions>> (HttpStatus.BAD_REQUEST);
		}    
	    
	}
	
	//Add Multiple Intermediate Questions
	@RequestMapping(value = "/saveAllIntermediateQuestions", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<List<IntermediateQuestions>> saveAllIntermediateQuestions(@RequestBody HashMap<String, String> jsonValues, HttpSession session) {
	    try {
	    	
	    	List<IntermediateQuestions> intermediateQuestionsList = new ArrayList<>();

			String intermediateQuestionsArrayList[] = intermediateQuestionsService.getAllIntermediateQuestions().toArray(new String[0]);
	    	
//		    System.out.println("operation: "+jsonValues); 
	        String skillStr = String.valueOf(jsonValues.get("skill_name"));  
	        String intermediateQuestionsStr = String.valueOf(jsonValues.get("intermediate_questions"));
	        
	        if(intermediateQuestionsStr.contains("#$#")) {
	        	
	        	List<String> questionsListDTO = new ArrayList<>();
	        	
	        	String intermediateQuestionsStrArray[] = intermediateQuestionsStr.split("\\#\\$\\#");
	        	
	        	for(int i=0;i<intermediateQuestionsStrArray.length;i++) {
	        		
	        		int duplicateQuestionStatus = 0;
	        		
	        		for (int j=0;j<intermediateQuestionsArrayList.length;j++) {
	    				if(intermediateQuestionsArrayList[j].equals(intermediateQuestionsStrArray[i])) {
	    					duplicateQuestionStatus = 1;
	    				}else {
	    					
	    				}
	    			}

					if(duplicateQuestionStatus == 1) {
						
					}else {
						
						questionsListDTO.add(intermediateQuestionsStrArray[i]);
						
					}
					
				}
	        	
	        	Set<String> questionsListSet = new HashSet<>(questionsListDTO);
	        	questionsListDTO.clear();
	        	questionsListDTO.addAll(questionsListSet);
				
				String questionsArrayListDTO[] = questionsListDTO.toArray(new String[0]);
				
				for (int k=0;k<questionsArrayListDTO.length;k++) {
					
					IntermediateQuestions intermediateQuestions = new IntermediateQuestions();
					intermediateQuestions.setSkill(skillStr);
					intermediateQuestions.setQuestion(questionsArrayListDTO[k]);
					intermediateQuestionsList.add(intermediateQuestions);	
						
				}
	        	
	        }
	        else {
	        	
	        	int duplicateQuestionStatus = 0;
        		
        		for (int j=0;j<intermediateQuestionsArrayList.length;j++) {
    				if(intermediateQuestionsArrayList[j].equals(intermediateQuestionsStr)) {
    					duplicateQuestionStatus = 1;
    				}else {
    					
    				}
    			}
        		
        		IntermediateQuestions intermediateQuestions = new IntermediateQuestions();
        		
        		if(duplicateQuestionStatus == 1) {
					
				}else {
					intermediateQuestions.setSkill(skillStr);
					intermediateQuestions.setQuestion(intermediateQuestionsStr);
					intermediateQuestionsList.add(intermediateQuestions);
				}
					
	        }

	        ResponseEntity<List<IntermediateQuestions>> responseEntity = new ResponseEntity<List<IntermediateQuestions>>(intermediateQuestionsService.saveAllIntermediateQuestions(intermediateQuestionsList), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<IntermediateQuestions>> (HttpStatus.BAD_REQUEST);
		}    
	    
	}
	
	//Add Multiple Intermediate Questions
	@RequestMapping(value = "/saveAllExpertQuestions", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<List<ExpertQuestions>> saveAllExpertQuestions(@RequestBody HashMap<String, String> jsonValues, HttpSession session) {
	    try {
	    	
	    	List<ExpertQuestions> expertQuestionsList = new ArrayList<>();
	    	
	    	String expertQuestionsArrayList[] = expertQuestionsService.getAllExpertQuestions().toArray(new String[0]);
	    	
//			System.out.println("operation: "+jsonValues); 
	        String skillStr = String.valueOf(jsonValues.get("skill_name"));  
	        String expertQuestionsStr = String.valueOf(jsonValues.get("expert_questions"));
	        
	        if(expertQuestionsStr.contains("#$#")) {
	        	
	        	List<String> questionsListDTO = new ArrayList<>();

	        	String expertQuestionsStrArray[] = expertQuestionsStr.split("\\#\\$\\#");
	        	
	        	for(int i=0;i<expertQuestionsStrArray.length;i++) {
	        		
	        		int duplicateQuestionStatus = 0;
	        		
	        		for (int j=0;j<expertQuestionsArrayList.length;j++) {
	    				if(expertQuestionsArrayList[j].equals(expertQuestionsStrArray[i])) {
	    					duplicateQuestionStatus = 1;
	    				}else {
	    					
	    				}
	    			}

					if(duplicateQuestionStatus == 1) {
						
					}else {
						
						questionsListDTO.add(expertQuestionsStrArray[i]);
						
					}
					
				}
	        	
	        	Set<String> questionsListSet = new HashSet<>(questionsListDTO);
	        	questionsListDTO.clear();
	        	questionsListDTO.addAll(questionsListSet);
				
				String questionsArrayListDTO[] = questionsListDTO.toArray(new String[0]);
				
				for (int k=0;k<questionsArrayListDTO.length;k++) {
					
					ExpertQuestions expertQuestions = new ExpertQuestions();
					expertQuestions.setSkill(skillStr);
					expertQuestions.setQuestion(questionsArrayListDTO[k]);
					expertQuestionsList.add(expertQuestions);	
						
				}
	        	
	        }
	        else {
	        	
	        	int duplicateQuestionStatus = 0;
        		
        		for (int j=0;j<expertQuestionsArrayList.length;j++) {
    				if(expertQuestionsArrayList[j].equals(expertQuestionsStr)) {
    					duplicateQuestionStatus = 1;
    				}else {
    					
    				}
    			}
        		
        		ExpertQuestions expertQuestions = new ExpertQuestions();
        		
        		if(duplicateQuestionStatus == 1) {
					
				}else {
					expertQuestions.setSkill(skillStr);
					expertQuestions.setQuestion(expertQuestionsStr);
					expertQuestionsList.add(expertQuestions);
				}
					
	        }

	        ResponseEntity<List<ExpertQuestions>> responseEntity = new ResponseEntity<List<ExpertQuestions>>(expertQuestionsService.saveAllExpertQuestions(expertQuestionsList), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<ExpertQuestions>> (HttpStatus.BAD_REQUEST);
		}    
	    
	}
	
	//Get Easy Questions By Skill Name
	@RequestMapping(value = "/getEasyQuestionsBySkill/{skill}", method = RequestMethod.GET, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<EasyQuestions>> getAllEasyQuestions(@PathVariable String skill) {
		try {

			ResponseEntity<List<EasyQuestions>> responseEntity = new ResponseEntity<List<EasyQuestions>>(easyQuestionsService.getEasyQuestionsBySkill(skill), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<EasyQuestions>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	//Get Intermediate Questions By Skill Name
	@RequestMapping(value = "/getIntermediateQuestionsBySkill/{skill}", method = RequestMethod.GET, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<IntermediateQuestions>> getAllIntermediateQuestions(@PathVariable String skill) {
		try {

			ResponseEntity<List<IntermediateQuestions>> responseEntity = new ResponseEntity<List<IntermediateQuestions>>(intermediateQuestionsService.getIntermediateQuestionsBySkill(skill), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<IntermediateQuestions>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	//Get Intermediate Questions By Skill Name
	@RequestMapping(value = "/getExpertQuestionsBySkill/{skill}", method = RequestMethod.GET, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ExpertQuestions>> getAllExpertQuestions(@PathVariable String skill) {
		try {

			ResponseEntity<List<ExpertQuestions>> responseEntity = new ResponseEntity<List<ExpertQuestions>>(expertQuestionsService.getExpertQuestionsBySkill(skill), HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			return new ResponseEntity<List<ExpertQuestions>> (HttpStatus.BAD_REQUEST);
		}
	}

//	@RequestMapping(value = "/getEasyQuestionsBySkills/{skills}", method = RequestMethod.GET, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
//	public ResponseEntity<List<EasyQuestions>> getAllEasyQuestions(@RequestParam List<String> skills) {
//		try {
//			ResponseEntity<List<EasyQuestions>> responseEntity = new ResponseEntity<List<EasyQuestions>>(easyQuestionsService.getEasyQuestionsBySkills(skills), HttpStatus.OK);
//			return responseEntity;
//
//		} catch (Exception e) {
//			return new ResponseEntity<List<EasyQuestions>> (HttpStatus.BAD_REQUEST);
//		}
//	}

	//Download Questions Bulk Upload Sample Template
	@GetMapping("/questionsBulkUploadSampleCSVFile")
	public void downloadQuestionsSampleTemplateFile(HttpServletResponse response) throws IOException {
		File file = new File("src/main/resources/static/styles/assets/documents/questionsBulkUploadTemplateFile.csv");

		//Force Browser to download the file Rather than opening it
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + file.getName();

		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		byte[] buffer = new byte[8129]; //8KB Buffer
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outputStream.close();

	}
	
	//Download Skills Bulk Upload Sample Template
	@GetMapping("/skillsBulkUploadSampleCSVFile")
	public void downloadSkillsSampleTemplateFile(HttpServletResponse response) throws IOException {
		File file = new File("src/main/resources/static/styles/assets/documents/skillsBulkUploadTemplateFile.csv");

		//Force Browser to download the file Rather than opening it
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + file.getName();

		response.setHeader(headerKey, headerValue);

		ServletOutputStream outputStream = response.getOutputStream();

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		byte[] buffer = new byte[8129]; //8KB Buffer
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outputStream.close();

	}
	
	//Bulk Upload Easy Questions
	@PostMapping("/bulkUploadEasyQuestions")
	  public ResponseEntity<ResponseMessage> uploadEasyQuestionsFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (CSVHelper.hasCSVFormat(file)) {
	      try {
	    	 
	    	easyQuestionsService.saveBulkEasyQuestions(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        
	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/api/csv/download/")
	                .path(file.getOriginalFilename())
	                .toUriString();

	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
	}
	
	//Bulk Upload Intermediate Questions
	@PostMapping("/bulkUploadIntermediateQuestions")
	  public ResponseEntity<ResponseMessage> uploadIntermediateQuestionsFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (CSVHelper.hasCSVFormat(file)) {
	      try {
	    	 
	    	intermediateQuestionsService.saveBulkIntermediateQuestions(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        
	        String fileDownloadUri = "Successfully Uploaded";

	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
	 }
	
	//Bulk Upload Expert Questions
	@PostMapping("/bulkUploadExpertQuestions")
	  public ResponseEntity<ResponseMessage> uploadExpertQuestionsFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (CSVHelper.hasCSVFormat(file)) {
	      try {
	    	 
	    	expertQuestionsService.saveBulkExpertQuestions(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        
	        String fileDownloadUri = "Successfully Uploaded";

	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
	      }
	    }

	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
	 }
	
	//Bulk Upload Skills
	@PostMapping("/bulkUploadSkills")
	public ResponseEntity<ResponseMessage> uploadSkillsFile(@RequestParam("file") MultipartFile file) {
	    String message = "";

	    if (CSVHelper.hasCSVFormat(file)) {
		     try {
			    	 
		   	skillService.saveBulkSkills(file);

		      message = "Uploaded the file successfully: " + file.getOriginalFilename();
		        
//		      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//			              .path("/api/csv/download/")
//			              .path(file.getOriginalFilename())
//			              .toUriString();
		      
		      String fileDownloadUri = "Successfully Uploaded";

			  return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
		    } catch (Exception e) {
		    	e.printStackTrace();
		      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
		      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
		    }
		  }

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
	}
	
	
}
