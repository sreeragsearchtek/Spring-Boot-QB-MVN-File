package com.curatal.qb.repository;

import com.curatal.qb.entity.Skills;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skills, Integer>{
	
	@Query("SELECT skill FROM Skills skills")
	List<String> findAllSkills();

}
