package api.payload;

import java.util.List;

public class Employ {
	private String status;
	private List<Employ> data;

	private String id;
	private String employee_name;
	private int employee_salary;
	private int employee_age;
	private String profile_image;
	private int EID;

	private String message;
	private Payload payload;

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public int getEID() {
		return EID;
	}

	public void setEID(int eID) {
		EID = eID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public int getEmployee_salary() {
		return employee_salary;
	}

	public void setEmployee_salary(int employee_salary) {
		this.employee_salary = employee_salary;
	}

	public int getEmployee_age() {
		return employee_age;
	}

	public void setEmployee_age(int employee_age) {
		this.employee_age = employee_age;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Employ> getData() {
		return data;
	}

	public void setData(List<Employ> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
