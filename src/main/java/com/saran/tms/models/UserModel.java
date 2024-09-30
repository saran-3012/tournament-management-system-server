package com.saran.tms.models;

public class UserModel implements Model {

	private Long userId;
	private String userName;
	private Long dateOfBirth;
	private String phoneNumber;
	private String email;
	private String password;
	private Short gender;
	private String bloodGroup;
	private String userAddress;
	private Short role;
	private Long organizationId;
	private Long userCreatedAt;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getGender() {
		return gender;
	}
	public void setGender(Short gender) {
		this.gender = gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public Short getRole() {
		return role;
	}
	public void setRole(Short role) {
		this.role = role;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getUserCreatedAt() {
		return userCreatedAt;
	}
	public void setUserCreatedAt(Long userCreatedAt) {
		this.userCreatedAt = userCreatedAt;
	}
	
	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", userName=" + userName + ", dateOfBirth=" + dateOfBirth
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", password=" + password + ", gender=" + gender
				+ ", bloodGroup=" + bloodGroup + ", userAddress=" + userAddress + ", role=" + role + ", organizationId="
				+ organizationId + ", userCreatedAt=" + userCreatedAt + "]";
	}
	
}
