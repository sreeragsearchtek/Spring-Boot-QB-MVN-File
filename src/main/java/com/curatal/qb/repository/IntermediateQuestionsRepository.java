package com.curatal.qb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.curatal.qb.entity.IntermediateQuestions;

public interface IntermediateQuestionsRepository extends JpaRepository<IntermediateQuestions, Integer>{

	List<IntermediateQuestions> findBySkill(String skill);
	
	@Query("SELECT question FROM IntermediateQuestions easyQuestions")
	List<String> findAllQuestions();
	
}
