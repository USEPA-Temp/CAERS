The swagger file available: 
https://ofmext.epa.gov/facilityiptqueryprd/swagger-v1.json

Help
https://swagger.io/docs/specification/2-0/describing-responses/

Modifications:
basePath: added http://localhost so swagger plug-in would stop complaining
/QueryXXX: modified all of services to return a collection (array)
/QueryXXX: modified all of services to produce application/json
/QueryReleasePoint: modified to return an array of ReleasePoint (not Unit)
/QueryAssociation: modified to return an array of Association (not Unit)
