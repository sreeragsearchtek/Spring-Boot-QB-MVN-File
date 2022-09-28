package com.curatal.qb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curatal.qb.entity.ExpertQuestions;
import com.curatal.qb.repository.ExpertQuestionsRepository;
import com.curatal.qb.utils.CSVHelper;

@Service
public class ExpertQuestionsService {
	
	@Autowired
	private ExpertQuestionsRepository expertQuestionsRepository;

	//Get All Expert Questions List (All Columns)
	public List<ExpertQuestions> getExpertQuestionsList() {
		return expertQuestionsRepository.findAll();
	}
	
	//Get All Expert Questions (Single Columns)
	public List<String> getAllExpertQuestions() {
		return expertQuestionsRepository.findAllQuestions();
	}
	
	//Get Expert Questions List By Skill
	public List<ExpertQuestions> getExpertQuestionsBySkill(String skill) {
		return expertQuestionsRepository.findBySkill(skill);
	}

	//Save Expert Question
	public void saveExpertQuestion(ExpertQuestions expertQuestions) {
		this.expertQuestionsRepository.save(expertQuestions);
	}
	
	//Save Bulk Expert Questions
	public void saveBulkExpertQuestions(MultipartFile file) {
	    try {
	      List<ExpertQuestions> expertQuestions = CSVHelper.setExpertQuestionsByCSV(file.getInputStream());
	      expertQuestionsRepository.saveAll(expertQuestions);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	}
	
	//Multi Expert Questions Save
	public List<ExpertQuestions> saveAllExpertQuestions(List<ExpertQuestions> expertQuestionsList) {
		List<ExpertQuestions> response = (List<ExpertQuestions>) expertQuestionsRepository.saveAll(expertQuestionsList);
		return response;
	}
}
