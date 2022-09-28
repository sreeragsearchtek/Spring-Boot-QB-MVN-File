package com.curatal.qb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.curatal.qb.entity.EasyQuestions;

public interface EasyQuestionsRepository extends JpaRepository<EasyQuestions, Integer>{

	List<EasyQuestions> findBySkill(String skill);
	
	@Query("SELECT question FROM EasyQuestions easyQuestions")
	List<String> findAllQuestions();

//	@Query("select eq from EasyQuestions eq where eq.skill in :skills" )
//	List<EasyQuestions> findBySkills(@Param("skills") List<String> easyQuestionsSkillsList);
	
//	@Query("SELECT e from EasyQuestions e where e.secondarySkill=:secondarySkill")
//	List<EasyQuestions> findQuestionBySkill(@Param("secondarySkill") String secondarySkill);
//	
}
