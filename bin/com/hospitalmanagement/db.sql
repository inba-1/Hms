--table for Patients
create table  Patients (id int auto_increment primary key, 
name varchar(255)NOT NULL,
age INT NOT NULL,
gender varchar(10)NOt null);
--table for Doctor
Table for Doctor
create table Doctor(id int auto_increment primary key,n
ame varchar(255)not null,
dept varchar(255)not null);
--table for Appointment
create table Appointment(
id int auto_increment Primary key,
patients_id int not null,
doctor_id int not null,
appointment_date date not null,
foreign key(patients_id) references Patients(id),
foreign key(doctor_id) references Doctor(id));