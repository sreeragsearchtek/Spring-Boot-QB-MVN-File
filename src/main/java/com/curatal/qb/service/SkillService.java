package com.curatal.qb.service;

import java.io.IOException;
import java.util.List;

import com.curatal.qb.entity.Skills;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.curatal.qb.repository.SkillRepository;
import com.curatal.qb.utils.CSVHelper;

@Service
public class SkillService {
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Lazy
	@Autowired
	private CSVHelper cSVHelper;
	
	//Get All Skills List
	public List<Skills> getSkillsList() {
		return skillRepository.findAll();
	}
	
	//Get Skills
	public List<String> getAllSkills() {
		return skillRepository.findAllSkills();
	}

	//Single Skill Save
	public void saveSkill(Skills skills) {
		this.skillRepository.save(skills);
	}
	
	//Bulk Skill Upload
	public void saveBulkSkills(MultipartFile file) {
	    try {
	      List<Skills> skills = cSVHelper.setSkillsByCSV(file.getInputStream());
	      skillRepository.saveAll(skills);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	}

	//Multi Skill Save
	public List<Skills> saveAllSkills(List<Skills> skillsList) {
		List<Skills> response = (List<Skills>) skillRepository.saveAll(skillsList);
		return response;
	}
}
