package com.ffait.reid;

public class IDCard {
	String name;
	String sex;
	String nation;
	String birthday;
	String address;
	String idnum;
	String org;
	String validateTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getValidateTime() {
		return validateTime;
	}

	public void setValidateTime(String validateTime) {
		this.validateTime = validateTime;
	}

	@Override
	public String toString() {
		return "IDCard [name=" + name + ", sex=" + sex + ", nation=" + nation + ", birthday=" + birthday + ", address="
				+ address + ", idnum=" + idnum + ", org=" + org + ", validateTime=" + validateTime + "]";
	}

}
