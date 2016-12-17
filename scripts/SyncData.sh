#!/bin/bash

aws s3 sync ../fleetbriefing-data/data s3://fleetbriefing-data/data/ --acl "public-read" --delete
