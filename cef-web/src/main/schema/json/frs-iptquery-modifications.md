The swagger file available: 
https://ofmext.epa.gov/facilityiptqueryprd/swagger-v1.json

Help
https://swagger.io/docs/specification/2-0/describing-responses/

Modifications:
basePath: added host: localhost so swagger plug-in would stop complaining
type: replaced all "Number" with "number"
/QueryXXX: modified all services to return a collection (array)
/QueryXXX: modified all services adding produce application/json to work around plain/text response
/QueryXXX: modified all services adding an operationId
/QueryReleasePoint: modified to return an array of ReleasePoint (not Unit)
/QueryAssociation: modified to return an array of Association (not Unit)
ProgramFacility: removed latitude, longitude, accuracyValue, 
collectionMethodCode, scale, heightDatumCode, refPointCode
