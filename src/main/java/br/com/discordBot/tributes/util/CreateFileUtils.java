package br.com.discordBot.tributes.util;

import br.com.discordBot.tributes.entity.Donations;

import java.io.*;

public class CreateFileUtils {

    public String openingHtmlFile() {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Selenium Test </title></head>");
        html.append("<body>");
        html.append("<table border=\"1\" bordercolor=\"#000000\">");
        return html.toString();
    }

    public String createHtmlBody(Donations donations) {
        StringBuilder htmlBody = new StringBuilder();
        htmlBody.append("<tr><td><b>TestId</b></td><td><b>TestName</b></td><td><b>TestResult</b></td></tr>");
        htmlBody.append("<tr><td>001</td><td>Login</td><td>Passed</td></tr>");
        htmlBody.append("<tr><td>002</td><td>Logout</td><td>Passed</td></tr>");
        return htmlBody.toString();
    }

    public String closingHtmlFile() {
        StringBuilder html = new StringBuilder();
        html.append("</table></body></html>");
        return html.toString();
    }

    public File writeToFile(String htmlHeader, String htmlBody, String htmFooter, String fileName) throws IOException {

        File myObj = new File("D:\\desenvolvimento\\tributes\\donationsForm\\src\\" + fileName);
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            System.out.println("File already exists.");
        }

        FileWriter myWriter = new FileWriter(fileName);
        myWriter.write(htmlHeader + htmlBody + htmFooter);
        myWriter.close();

        return myObj;
    }
}
