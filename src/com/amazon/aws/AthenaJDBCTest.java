package com.amazon.aws;

import java.sql.*;
import java.util.Properties;
import com.amazonaws.athena.jdbc.AthenaDriver;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;


public class AthenaJDBCTest {

    static final String athenaUrl = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443";

    public static void main(String[] args) {

        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
            Properties info = new Properties();
            info.put("s3_staging_dir", "s3://dixonaws-athena-result-bucket/test/");
            //info.put("log_path", "/Users/myUser/.athena/athenajdbc.log");
            info.put("aws_credentials_provider_class","com.amazonaws.auth.PropertiesFileCredentialsProvider");
            info.put("aws_credentials_provider_arguments","/Users/dixonaws/.aws/athenaCredentials");
            String databaseName = "mydatabase";

            System.out.println("Connecting to Athena...");
            conn = DriverManager.getConnection(athenaUrl, info);

            System.out.println("Listing tables...");
            String sql = "show tables in "+ databaseName;
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                //Retrieve table column.
                String name = rs.getString("tab_name");

                //Display values.
                System.out.println("Name: " + name);
            }
            rs.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception ex) {

            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        System.out.printf("Finished connectivity test.");
    }
}