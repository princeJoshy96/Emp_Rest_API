package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import api.payload.Employ;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pageobject.TestpageObj;

public class EmpEndPoints {

	static ResourceBundle getURL() {
		ResourceBundle Employee = ResourceBundle.getBundle("Employee");

		return Employee;
	}

	public static Response CreateEmp(Employ payload) {

		String create_url = getURL().getString("create_Emp_uri");

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload).when()
				.post(create_url);
		return res;
	}

	public static Response GetEmp(String EmpName) {
		String get_url = getURL().getString("get_Emp_uri");
		Response res = given().pathParam("Name", EmpName).when().get(get_url);
		String responseBody = res.getBody().asString();

		Gson gson = new GsonBuilder().create();
		Response response = gson.fromJson(responseBody, Response.class);

		return response;

	}

	public static Response ReadEmp() {
		String read_url = getURL().getString("read_Emp_uri");
		Response res = given().when().get(read_url);
		return res;
	}

	public static Response DeleteUser(String empId) {
		String deleteUrl = getURL().getString("delete_Emp_id");
		String urlWithId = deleteUrl.replace("{id}", empId);
		Response res = given().params("id", empId). when().delete(urlWithId);
		return res;
	}

	public static Response getEmply() {
	    String getemp_url = getURL().getString("get_Emp_uri");
	    Response res = given().accept(ContentType.JSON).when().get(getemp_url);
	    res.then().statusCode(TestpageObj.getStatusOk()).contentType(ContentType.JSON);
	    return res;
	}
}
