package com.curatal.qb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "skills")
public class Skills {
	
	@Id
	@GeneratedValue
	@Column(name = "skill_id")
	private int skillId;
	
	@Column(name = "skill", unique = true)
	private String skill;

    public Skills() {

	}

	public Skills(String skill) {
		this.skill = skill;
	}

	/* Getter & Setter */

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "Skills [skill=" + skill + "]";
	}
	
}
