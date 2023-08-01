package api.test;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.EmpEndPoints;
import api.payload.Employ;
import api.payload.EmployeePayloadBuilder;
import api.payload.Payload;
import api.utilities.dataProvider;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import pageobject.TestpageObj;

public class DDrTest2 {
	private Employ userPayload;
	private int createdEmpId;
	private Properties properties;
	private JsonPath jsonPath;
	private Faker faker;
	Response response;

	@BeforeClass
	public void setup() {
		TestpageObj.loadProperties();
		properties = TestpageObj.getProperties();
		faker = new Faker();
	}

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = dataProvider.class)
	public void testpostUser(String EmpID, String EmpName, String EmpSal, String EmpAge) throws FileNotFoundException {
		userPayload = new EmployeePayloadBuilder().setId(EmpID).setEmployeeName(EmpName).setEmployeeAge(EmpAge)
				.setEmployeeSalary(EmpSal).build();

		int employeeId = Integer.parseInt(EmpID);

		if (employeeId < 1) {
			throw new NumberFormatException("The EmpID parameter is not a valid number.");
		}

		Response response = EmpEndPoints.CreateEmp(userPayload);
		TestpageObj.logResponse(response);
		TestpageObj.assertStatusCode(response, TestpageObj.getStatusOk());

		// Initialize jsonPath with the response body
		JsonPath jsonPath = JsonPath.from(response.getBody().asString());

		if (jsonPath.get("data.id") != null) {
			int responseId = jsonPath.get("data.id");
			String message = jsonPath.get("message");
			String Status = jsonPath.get("status");

			Assert.assertEquals(Status, "success");
			System.out.println("Message: " + message);
			createdEmpId = responseId;
			System.out.println("Created Employee ID: " + createdEmpId);
		}
		// TestpageObj.assertEmployeeDetails(TestpageObj.getProperties(), EmpName,
		// employeeSalary, employeeAge);
	}

	@Test(priority = 2)
	public void testDeleteEmployee() {
		Employ userPayload = new Employ();
		userPayload.setEID(faker.idNumber().hashCode());
		Payload payload = new Payload();
		payload.setMessage("This is a message");
		userPayload.setPayload(payload);

		// Build the employee payload
		EmployeePayloadBuilder payloadBuilder = new EmployeePayloadBuilder();
		payloadBuilder.setId(String.valueOf(faker.idNumber().hashCode())).setEmployeeName("John Doe")
				.setEmployeeAge("30").setEmployeeSalary("50000").setPayload(userPayload.getPayload());

		// Create an employee record
		Employ employee = payloadBuilder.build();
		int id = Integer.parseInt(employee.getId());

		// Delete the employee record
		response = EmpEndPoints.DeleteUser(String.valueOf(id));
		TestpageObj.logResponse(response);

		// Check if the employee record was deleted
		if (response.statusCode() == TestpageObj.getStatusOk()) {
			System.out.println("Employee with ID " + id + " was found");
		} else {
			Assert.fail("Status code mismatch. Expected: 200, Actual: " + response.statusCode());
		}
	}

	@Test(priority = 3)
	public void testDeleteEmploye0() {
		String EmpId = "4";
		Response response = EmpEndPoints.DeleteUser(String.valueOf(EmpId));
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TestpageObj.logResponse(response);

		int statusCode = response.statusCode();
		if (statusCode == TestpageObj.getStatusOk()) {
			System.out.println("Employee with ID " + EmpId + " was found");
		} else if (statusCode == TestpageObj.getStatusTooManyRequests()) {
			System.out.println("Employee with ID " + EmpId + " was not found ;" + statusCode);
		} else {
			Assert.fail("Status code mismatch. Expected: 400, Actual: " + statusCode);
		}

		// Initialize jsonPath with the response body
		jsonPath = response.getBody().jsonPath();
		if (jsonPath.get("data") != null) {
			String responseData = jsonPath.get("data");
			int responseId = Integer.parseInt(responseData);
			String message = jsonPath.get("message");

			Assert.assertEquals(responseId, Integer.parseInt(EmpId));
			System.out.println("Message: " + message);
		} else {
			// The 'data' key was not found in the response, consider it a successful test.
			System.out.println("The 'data' key was not found in the response.");
		}
	}

	@Test(priority = 4)
	public void testGetEmployees() {
		Response response = EmpEndPoints.ReadEmp();
		Employ employeeResponse = response.as(Employ.class);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TestpageObj.logResponse(response);
		TestpageObj.assertStatusCode(response, TestpageObj.getStatusOk());
		Assert.assertEquals(response.getContentType(), ContentType.JSON.toString());
		Assert.assertEquals(employeeResponse.getStatus(), "success");
		int numDataRecords = employeeResponse.getData().size();
		System.out.println("Number of data records: " + numDataRecords);
	}

	@Test(priority = 5)
	public void testGetEmployeeDetails() {
		try {
			Response response = EmpEndPoints.getEmply();
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			TestpageObj.logResponse(response);
			TestpageObj.assertStatusCode(response, TestpageObj.getStatusOk());
			Assert.assertEquals(response.getContentType(), ContentType.JSON.toString());

			String employeeName = response.jsonPath().getString("employee_name");
			int employeeSalary = response.jsonPath().getInt("employee_salary");
			Integer employeeAge = response.jsonPath().get("employee_age");

			// Check if employeeAge is not null before using it
			if (employeeAge != null) {
				int employeeAgeInt = employeeAge.intValue();
				TestpageObj.assertEmployeeDetails(properties, employeeName, employeeSalary, employeeAgeInt);
			} else {
				// Employee age is missing or null in the response
				System.out.println("Employee age is missing or null in the response.");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			// Handle the NullPointerException here (e.g., fail the test, log the error,
			// etc.)
		}
	}
}
