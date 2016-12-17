CREATE EXTERNAL TABLE IF NOT EXISTS fleetbriefing.invoices (
  rental_date string,
  dropoff_date string,
  renterid string,
  rental_agreement string,
  rental_invoice string,
  vehicle_tag string,
  vehicle_state string,
  vehicle_class string,
  distance_driven int,
  pickup_location string,
  rental_duration int,
  charges float
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe'
WITH SERDEPROPERTIES (
  'serialization.format' = ',',
  'field.delim' = ','
) LOCATION 's3://fleetbriefing-data/data/';