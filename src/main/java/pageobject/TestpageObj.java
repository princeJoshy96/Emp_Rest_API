package pageobject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class TestpageObj {
    private static Properties properties;

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
    }

    public static void logResponse(Response response) {
        response.then().log().all();
    }

    public static void assertEmployeeDetails(Properties properties, String employeeName, int employeeSalary,
            int employeeAge) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(employeeName, properties.getProperty("employeeName"));
        softAssert.assertEquals(employeeSalary, Integer.parseInt(properties.getProperty("employeeSalary")));
        softAssert.assertEquals(employeeAge, Integer.parseInt(properties.getProperty("employeeAge")));
        softAssert.assertAll();
    }

    public static int getStatusOk() {
        return Integer.parseInt(properties.getProperty("Status_Ok"));
    }

    public static int getStatusBadRequest() {
        return Integer.parseInt(properties.getProperty("Status_Bad_Request"));
    }

    public static int getStatusTooManyRequests() {
        return Integer.parseInt(properties.getProperty("Status_Too_Many_Requests"));
    }

    public static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream employeeFile = new FileInputStream("C:\\Users\\Joshua.Onyena\\Downloads\\EclipeWorkspace2\\TRAP\\src\\test\\resources\\Employee.properties");
            FileInputStream statusCodeFile = new FileInputStream("C:\\Users\\Joshua.Onyena\\Downloads\\EclipeWorkspace2\\TRAP\\src\\test\\resources\\Statuscode.properties");

            properties.load(employeeFile);
            properties.load(statusCodeFile);

            employeeFile.close();
            statusCodeFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Properties getProperties() {
        return properties;
    }
}
