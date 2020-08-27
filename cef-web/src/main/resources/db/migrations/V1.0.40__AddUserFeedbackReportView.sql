
alter table user_feedback
	add facility_name varchar(80),
	add agency_code varchar(3),
	add year SMALLINT,
	add user_name varchar(255);
        