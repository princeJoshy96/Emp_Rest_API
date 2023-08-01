package api.payload;

public class EmployeePayloadBuilder {
	private Employ employeePayload;

	public EmployeePayloadBuilder() {
		employeePayload = new Employ();
	}

	private String id;
	private String employee_name;
	private String employee_salary;
	private String employee_age;
	private Payload payload;

	public EmployeePayloadBuilder setId(String empID) {
		this.id = empID;
		return this;
	}

	public EmployeePayloadBuilder setEmployeeName(String employeeName) {
		this.employee_name = employeeName;
		return this;
	}

	public EmployeePayloadBuilder setEmployeeSalary(String employeeSalary) {
		this.employee_salary = employeeSalary;
		return this;
	}

	public EmployeePayloadBuilder setEmployeeAge(String employeeAge) {
		this.employee_age = employeeAge;
		return this;
	}

	public EmployeePayloadBuilder setPayload(Payload payload) {
		this.payload = payload;
		return this;
	}

	public Employ build() {
		employeePayload.setId(id);
		employeePayload.setEmployee_name(employee_name);
		employeePayload.setEmployee_salary(Integer.parseInt(employee_salary));
		employeePayload.setEmployee_age(Integer.parseInt(employee_age));
		employeePayload.setPayload(payload);

		return employeePayload;
	}
}
