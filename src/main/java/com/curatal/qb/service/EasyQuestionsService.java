package com.curatal.qb.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curatal.qb.entity.EasyQuestions;
import com.curatal.qb.repository.EasyQuestionsRepository;
import com.curatal.qb.utils.CSVHelper;

@Service
public class EasyQuestionsService {
	
	@Autowired
	private EasyQuestionsRepository easyQuestionsRepository;
	
	//Get All Easy Questions List (All Columns)
	public List<EasyQuestions> getEasyQuestionsList() {
		return easyQuestionsRepository.findAll();
	}
	
	//Get All Easy Questions (Single Columns)
	public List<String> getAllEasyQuestions() {
		return easyQuestionsRepository.findAllQuestions();
	}

	//Get Easy Questions By Skill
	public List<EasyQuestions> getEasyQuestionsBySkill(String skill) {
		return easyQuestionsRepository.findBySkill(skill);
	}

	//Save Easy Question
	public void saveEasyQuestion(EasyQuestions easyQuestions) {
		this.easyQuestionsRepository.save(easyQuestions);
	}
	
	//Save Bulk Questions
	public void saveBulkEasyQuestions(MultipartFile file) {
	    try {
	      List<EasyQuestions> easyQuestions = CSVHelper.setEasyQuestionsByCSV(file.getInputStream());
	      easyQuestionsRepository.saveAll(easyQuestions);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	}
	
	//Multi Easy Questions Save
	public List<EasyQuestions> saveAllEasyQuestions(List<EasyQuestions> easyQuestionsList) {
		List<EasyQuestions> response = (List<EasyQuestions>) easyQuestionsRepository.saveAll(easyQuestionsList);
		return response;
	}

//	public List<EasyQuestions> getEasyQuestionsBySkills(List<String> skills){
//		return easyQuestionsRepository.findBySkills(skills);
//	}


//	@Override
//	public List<PostDTO> getPostsList(List<Long> ids, Pageable pageable) {
//		List<PostDTO> resultList = new ArrayList<>();
//		Page<Post> paginatedPosts = postRepository.findByIds(ids, pageable);
//
//		List<Post> posts = paginatedPosts.getContent();
//		posts.forEach(post -> resultList.add(convertToPostDTO(post)));
//
//		return resultList;
//	}

//	public EasyQuestions saveQuestion(EasyQuestions easyQuestions) {
//		return easyQuestionsRepository.save(easyQuestions);
//	}


}
