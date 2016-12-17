#!/usr/bin/python

import time
import random

# generate some invoice data
# each CSV should contain one invoice
# filenames do not matter
# structure of the files must be as follows:

#  rental_date string,
#  dropoff_date string,
#  renterid string,
#  rental_agreement string,
#  rental_invoice string,
#  vehicle_tag string,
#  vehicle_state string,
#  vehicle_class string,
#  distance_driven int,
#  pickup_location string,
#  rental_duration int,
#  charges float

# example:
# 03-10-2017,01-12-2017,001,334899532,30329177623,BPGT63,FL,SRAR,375,JAX,3,167.43


def main():
    print "GenerateData v1.0"

    # write 1,000 individual records
    for i in range(0, 1000):
        # form the random data
        var_rental_date = str(random.randint(01, 12)) + "-" + str(random.randint(01, 30)) + "-" + str(
            random.randint(2010, 2016))
        var_dropoff_date = "03-10-2017"
        var_renterid = str(random.randint(10000, 99999))
        var_rental_agreement = str(random.randint(10000, 99999))
        var_rental_invoice = str(random.randint(10000, 99999))

        # generate a random vehicle tag
        tag=random.sample(["1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E","F","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"], 6)

        var_vehicle_tag = str(tag[0]) + str(tag[1]) + str(tag[2]) + str(tag[3]) + str(tag[4]) + str(tag[5])
        var_vehicle_state = "FL"
        var_vehicle_class = "SRAR"
        var_distance_driven = str(random.randint(0, 900))

        # choose from a list of random airport codes
        airport_code=(["ATL", "LAX", "JAX", "RDW", "RTP", "DTW", "MDW", "GRR", "ORD", "DFW", "DEN", "JFK", "SFO", "CLT", "LAS", "PHX", "IAH", "IAD", "DCA", "EWR", "MCO", "SEA", "MSP", "PHL", "BOS", "LGA", "BWI", "SAN", "TPA", "CLE", "PDX"])
        var_pickup_location = (random.sample(airport_code, 1))[0]
        var_rental_duration = str(random.randint(1, 7))
        var_charges = str(round((random.uniform(50.00, 799.99)),2))

        # form the record from our var_* variables above
        var_record = str(
            var_rental_date + "," + var_dropoff_date + "," + var_renterid + "," + var_rental_agreement + "," + var_rental_invoice + "," + var_vehicle_tag + "," + var_vehicle_state + "," + var_vehicle_class + "," + str(
                var_distance_driven) + "," + var_pickup_location + "," + str(var_rental_duration) + "," + str(
                var_charges))

        var_filename="data/csv/invoice" + str(i) + ".csv"
        print("Writing " + var_filename)
        f=open(var_filename,"w")
        f.write(var_record)
        f.close()
    # for i

main()