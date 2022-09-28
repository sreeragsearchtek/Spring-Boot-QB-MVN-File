package com.curatal.qb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curatal.qb.entity.IntermediateQuestions;
import com.curatal.qb.repository.IntermediateQuestionsRepository;
import com.curatal.qb.utils.CSVHelper;

@Service
public class IntermediateQuestionsService {
	
	@Autowired
	private IntermediateQuestionsRepository intermediateQuestionsRepository;
	
	//Get All Intermediate Questions List (All Columns)
	public List<IntermediateQuestions> getIntermediateQuestionsList() {
		return intermediateQuestionsRepository.findAll();
	}

	//Get All Intermediate Questions (Single Columns)
	public List<String> getAllIntermediateQuestions() {
		return intermediateQuestionsRepository.findAllQuestions();
	}
		
	//Get Intermediate Questions List By Skill
	public List<IntermediateQuestions> getIntermediateQuestionsBySkill(String skill) {
		return intermediateQuestionsRepository.findBySkill(skill);
	}

	//Save Intermediate Question
	public void saveIntermediateQuestion(IntermediateQuestions intermediateQuestions) {
		this.intermediateQuestionsRepository.save(intermediateQuestions);
	}
	
	//Save Bulk Upload Intermediate Questions
	public void saveBulkIntermediateQuestions(MultipartFile file) {
	    try {
	      List<IntermediateQuestions> intermediateQuestions = CSVHelper.setIntermediateQuestionsByCSV(file.getInputStream());
	      intermediateQuestionsRepository.saveAll(intermediateQuestions);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	}
	
	//Multi Intermediate Questions Save
	public List<IntermediateQuestions> saveAllIntermediateQuestions(List<IntermediateQuestions> intermediateQuestionsList) {
		List<IntermediateQuestions> response = (List<IntermediateQuestions>) intermediateQuestionsRepository.saveAll(intermediateQuestionsList);
		return response;
	}
	
}
