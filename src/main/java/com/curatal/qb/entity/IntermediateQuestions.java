package com.curatal.qb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "intermediate_questions")
public class IntermediateQuestions {
	
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
	
    public IntermediateQuestions() {

	}

	public IntermediateQuestions(String question, String skill) {
		this.question = question;
		this.skill = skill;
	}

	/* Getter & Setter */
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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
		return "IntermediateQuestions [question=" + question + ", skill=" + skill + "]";
	}
	
}
