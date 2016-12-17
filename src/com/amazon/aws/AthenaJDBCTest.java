package com.amazon.aws;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import com.amazonaws.athena.jdbc.AthenaDriver;
import com.amazonaws.athena.jdbc.shaded.com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;


public class AthenaJDBCTest {

    static final String athenaUrl = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443";

    public static void main(String[] args) {
        String accessKey=args[0];
        String secretKey=args[1];

        // write the credentials file
        String credentialsFilename="/tmp/athenaCredentials";
        configureCreds(accessKey, secretKey, credentialsFilename);

        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
            Properties info = new Properties();
            info.put("s3_staging_dir", "s3://dixonaws-athena-result-bucket/test/");
            //info.put("log_path", "/Users/myUser/.athena/athenajdbc.log");
            info.put("aws_credentials_provider_class", "com.amazonaws.auth.PropertiesFileCredentialsProvider");
            info.put("aws_credentials_provider_arguments", credentialsFilename);

            String databaseName = "fleetbriefing";

            System.out.println("Connecting to Athena...");
            conn = DriverManager.getConnection(athenaUrl, info);

            long startTime=System.currentTimeMillis();
            System.out.print("Getting top three rental locations...");
            String sql = "SELECT pickup_location, count(rental_date) AS Rentals, sum(charges) AS Revenue FROM fleetbriefing.invoices_gzip GROUP BY  pickup_location ORDER BY Rentals DESC LIMIT 3";
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            long endTime=System.currentTimeMillis();
            long duration=endTime-startTime;
            System.out.println("done (" + duration + "ms)");

            while (rs.next()) {
                //Retrieve rental info
                int pickup_location = 1;
                int rentals = 2;
                int revenue = 3;

                String str_pickup_location = rs.getString(pickup_location);

                //Display values.
                System.out.println("Pickup location: " + str_pickup_location + ", with " + rs.getString(rentals) + " rentals and " + rs.getString(revenue) + " in revenue");
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

        } //try/catch/finally


    } // main()

    private static void configureCreds(String accessKey, String secretKey, String credentialsFilename) {
        File fout = new File(credentialsFilename);
        FileOutputStream fos = null;

        try {
            fout.getParentFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos = new FileOutputStream(fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        OutputStreamWriter osw = new OutputStreamWriter(fos);

        try {
            osw.write("region=us-east-1\n" +
                    "accessKey=" + accessKey + "\n" +
                    "secretKey=" + secretKey + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // configureCreds()


}
