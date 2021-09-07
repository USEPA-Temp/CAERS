
CREATE TABLE slt_properties (
	name character varying(64) PRIMARY KEY,
	label character varying(64),
	description character varying(255),
	datatype character varying(20));
	
INSERT INTO slt_properties (name, label, description, datatype) values ('slt-eis-program-code','SLT EIS Program System Code','SLT Program System Code associated with EIS submissions.','string');
INSERT INTO slt_properties (name, label, description, datatype) values ('slt-eis-user','SLT EIS User ID','SLT User ID associated with EIS submissions.','string');
INSERT INTO slt_properties (name, label, description, datatype) values ('slt-email','SLT Email','Email recipient who should receive SLT email notifications.','string');

ALTER TABLE slt_properties ALTER COLUMN name SET NOT NULL;
ALTER TABLE slt_properties ALTER COLUMN label SET NOT NULL;
ALTER TABLE slt_properties ALTER COLUMN description SET NOT NULL;
ALTER TABLE slt_properties ALTER COLUMN datatype SET NOT NULL;

ALTER TABLE slt_config ADD CONSTRAINT name_fkey FOREIGN KEY (name) REFERENCES slt_properties (name);

UPDATE admin_properties SET description = 'How far off total emissions can be from calculated without triggering error.' WHERE name = 'emissions.tolerance.total.error';
UPDATE admin_properties SET description = 'Enables SCC Updates' WHERE name = 'task.scc-update.enabled.';
UPDATE admin_properties SET description = 'Enables submitting data to EIS from CAERS in the CERS V2.0 schema.' WHERE name = 'feature.cers-v2.enabled';
UPDATE admin_properties SET description = 'Enables migrating user facility associations from CDX to CAERS.' WHERE name = 'feature.cdx-association-migration.enabled';
