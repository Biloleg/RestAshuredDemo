package logToAllure;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestNGListener implements ITestListener {
    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();

    private PrintStream requestPrintStream = new PrintStream(request, true);
    private PrintStream responsePrintStream = new PrintStream(response, true);


    public void onStart(ITestContext iTestContext) {
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responsePrintStream),
                new RequestLoggingFilter(LogDetail.ALL, requestPrintStream));
    }

    public void onTestFailure(ITestResult iTestResult) {
        logRequest(request);
        logResponse(response);
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return toByteArr(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return toByteArr(stream);
    }

    private byte[] toByteArr(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }
}
