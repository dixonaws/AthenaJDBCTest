<h1>AthenaTest</h1>
Query rental data in S3 via SQL. Provide your accessKey and secretKey as arguments when running AthenaTest, as follows:
<code>java -classpath ... AthenaTest (accessKey) (secretKey)</code>
<p>
The scripts folder includes a Python program to generate sample rental data (GenerateData.py), and a program to upload the sample data to an S3 bucket (SyncData.sh).
<p>
The sql folder includes SQL ddl to create tables in Athena so that CSV or GZIP files can be queried directly.