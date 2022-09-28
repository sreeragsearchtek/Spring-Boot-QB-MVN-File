package com.curatal.qb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "easy_questions")
public class EasyQuestions {
	
	@Id
	@GeneratedValue
	@Column(name = "question_id")
	private int questionId;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "skill")
	private String skill;

	@Column(name = "selection_count",columnDefinition = "integer default 0")
	private int selectionCount;
	
	public EasyQuestions(String skill, String question) {
		this.question = question;
		this.skill = skill;
	}
	

	/* Getter & Setter */

	public EasyQuestions() {
		// TODO Auto-generated constructor stub
	}


	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public int getSelectionCount() {
		return selectionCount;
	}

	public void setSelectionCount(int selectionCount) {
		this.selectionCount = selectionCount;
	}


	@Override
	public String toString() {
		return "EasyQuestions [question=" + question + ", skill=" + skill + "]";
	}
	
	
}
