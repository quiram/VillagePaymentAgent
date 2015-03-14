CREATE TABLE KJ905.HT_DEVICE (
imei BIGINT not null,
product_number varchar(45) not null,
model_number varchar(45) not null,
allocation varchar(4) default null,
ht_mobile_number varchar(25) default null,
allocation_date date default null,
primary key (imei)
);

CREATE TABLE KJ905.HT_FOM (
idFom int not null generated always as identity (start with 1, increment by 1),
first_name varchar(45) not null,
last_name varchar(45) not null,
email varchar(45) not null,
password varchar(45) not null,
primary key (idFom)
);

CREATE TABLE KJ905.HT_IMAGE (
idImage int not null generated always as identity (start with 1, increment by 1),
"DATE" date not null,
photo blob not null,
verification_status varchar(45) default null,
verification_date date default null,
verification_comment varchar(100) default null,
employee_type varchar(25) not null,
primary key (idImage)
);

CREATE TABLE KJ905.HT_COUNTRY (
idCountry int not null generated always as identity (start with 1, increment by 1),
title varchar(45) not null,
description varchar(45) not null,
primary key (idCountry)
);

CREATE TABLE KJ905.HT_REGION (
idRegion int not null generated always as identity (start with 1, increment by 1),
title varchar(45) not null,
description varchar(45) not null,
ht_country_idCountry int not null,
primary key (idRegion),
constraint fk_ht_country_idCountry foreign key (ht_country_idCountry) references ht_country (idCountry)
);

CREATE TABLE KJ905.HT_DISTRICT (
idDistrict int not null generated always as identity (start with 1, increment by 1),
title varchar(45) not null,
description varchar(45) not null,
ht_region_idRegion int not null,
primary key (idDistrict),
constraint fk_ht_region_idRegion foreign key (ht_region_idRegion) references ht_region (idRegion)
);

CREATE TABLE KJ905.HT_VERIFIER (
idVerifier int not null generated always as identity (start with 1, increment by 1),
first_name varchar(45) default null,
middle_name varchar(45) default null,
last_name varchar(45) default null,
gender varchar(1) default null,
dob date default null,
email varchar(45) not null,
telephone_number varchar(25) default null,
password varchar(45) not null,
education_type varchar(45) default null,
education_level varchar(45) default null,
status varchar(45) not null,
status_date date not null,
start_date date default null,
ht_vacancy_idVacancy int default null,
verification_status varchar(45) default null,
verification_date date default null,
verification_comment varchar(100) default null,
ht_device_imei BIGINT default null,
ht_image_idImage int default null,
primary key (idVerifier),
constraint fk_ht_device_imei foreign key (ht_device_imei) references ht_device (imei),
constraint fk_ht_image_idImage foreign key (ht_image_idImage) references ht_image (idImage)
);

CREATE TABLE KJ905.HT_IDENTITY_DOCUMENT (
idIdentityDocument int not null generated always as identity (start with 1, increment by 1),
"NUMBER" varchar(25) not null,
type varchar(25) not null,
issue_date date not null,
expiry_date date not null,
verification_status varchar(45) default null,
verification_date date default null,
verification_comment varchar(100) default null,
employee_type varchar(25) not null,
emp_id int not null,
primary key (idIdentityDocument),
constraint fk_emp_id_identity_document foreign key (emp_id) references ht_verifier (idVerifier)
);

CREATE TABLE KJ905.HT_INTERVIEW (
idInterview int not null generated always as identity (start with 1, increment by 1),
"DATE" date default null,
address varchar(100) default null,
status varchar(45) not null,
comment varchar(100) default null,
employee_type varchar(25) not null,
emp_id int not null,
ht_fom_idFom int not null,
primary key (idInterview),
constraint fk_emp_id_interview foreign key (emp_id) references ht_verifier (idVerifier),
constraint fk_ht_fom_idFom_interview foreign key (ht_fom_idFom) references ht_fom (idFom)
);

CREATE TABLE KJ905.HT_ADDRESS (
idAddress int not null generated always as identity (start with 1, increment by 1),
street varchar(45) default null,
village varchar(45) default null,
postcode varchar(45) not null,
town varchar(45) default null,
city varchar(45) default null,
verification_status varchar(45) default null,
verification_date date default null,
verification_comment varchar(100) default null,
employee_type varchar(25) not null,
emp_id int not null,
ht_country_idCountry int not null,
ht_region_idRegion int not null,
ht_district_idDistrict int not null,
primary key (idAddress),
constraint fk_emp_id_address foreign key (emp_id) references ht_verifier (idVerifier),
constraint fk_ht_country_idCountry_address foreign key (ht_country_idCountry) references ht_country (idCountry),
constraint fk_ht_region_idRegion_address foreign key (ht_region_idRegion) references ht_region (idRegion),
constraint fk_ht_district_idDistrict_address foreign key (ht_district_idDistrict) references ht_district (idDistrict)
);

CREATE TABLE KJ905.HT_REFERENCE (
idReference int not null generated always as identity (start with 1, increment by 1),
organisation_name varchar(45) not null,
contact_number varchar(25) not null,
address varchar(100) not null,
employee_type varchar(25) not null,
title varchar(10) default null,
full_name varchar(45) default null,
designation varchar(45) default null,
email varchar(45) default null,
verification_status varchar(45) default null,
verification_date date default null,
verification_comment varchar(100) default null,
emp_id int not null,
primary key (idReference),
constraint fk_ht_verifier_idVerifier_reference foreign key (emp_id) references ht_verifier (idVerifier)
);

CREATE TABLE KJ905.HT_BANK (
idBank int not null generated always as identity (start with 1, increment by 1),
accountNumber varchar(15) not null,
bank_name varchar(45) not null,
address varchar(100) not null,
sort_code varchar(10) default null,
iban varchar(45) default null,
contact_number varchar(25) default null,
verification_status varchar(45) default null,
verification_date date default null,
verification_comment varchar(100) default null,
employee_type varchar(25) not null,
emp_id int not null,
primary key (idBank),
constraint fk_ht_verifier_idVerifier_bank foreign key (emp_id) references ht_verifier (idVerifier)
);

CREATE TABLE KJ905.HT_STATIC_DATA (
idStaticData int not null generated always as identity (start with 1, increment by 1),
type varchar(45) not null,
value varchar(45) not null,
description varchar(45) not null,
primary key (idStaticData)
);