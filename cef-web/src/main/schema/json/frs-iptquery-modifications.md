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
/QueryXXX: modified pageSize and offset, set type = integer
/QueryReleasePoint: modified to return an array of ReleasePoint (not Unit)
/QueryAssociation: modified to return an array of Association (not Unit)
ProgramFacility: removed latitude, longitude, accuracyValue, 
collectionMethodCode, scale, heightDatumCode, refPointCode
ProgramFacility: fix capitalization on hucXXX, facilitySourceSystemProgramCode
Model XXXYear: change all fields carrying year to integer
Model controlOperatingMonthsNumber: change to integer
Model Dates: change all dates to maxlength 30 for consistency
Model Dates: added format: "date" or "date-time" as appropriate (guess)
Model flag/indicator: changed type to boolean
Model lastReportedDate: reverted to string, it is neither date nor date-time, format unknown
Model Dates: had to revert dates to string, data format coming from FRS is not in an ISO format
Model boolean: had to revert to string, why can't we use standard JSON datatypes?
