package com.curatal.qb.repository;

import java.util.List;

import com.curatal.qb.entity.EasyQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.curatal.qb.entity.ExpertQuestions;

public interface ExpertQuestionsRepository extends JpaRepository<ExpertQuestions, Integer>{

	List<ExpertQuestions> findBySkill(String skill);
	
	@Query("SELECT question FROM ExpertQuestions easyQuestions")
	List<String> findAllQuestions();
	
}
