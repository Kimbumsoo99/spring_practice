package com.ssafy.member.model.dto;

public class MemberDto {
	String id;
	String name;
	String pass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public MemberDto(String id, String name, String pass) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", name=" + name + ", pass=" + pass + "]";
	}

	public MemberDto() {
		// TODO Auto-generated constructor stub
	}
}
