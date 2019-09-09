The swagger file available: 
https://ofmext.epa.gov/facilityiptquery/swagger-v1.json

Modifications:
basePath: added http://localhost so swagger plug-in would stop complaining
/QueryXXX: modified all of services to return a collection (array)
/QueryReleasePoint: modified to return an array of ReleasePoint (not Unit)
/QueryAssocation: modified to return an array of Association (not Unit)
