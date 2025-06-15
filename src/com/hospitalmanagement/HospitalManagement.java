package com.hospitalmanagement;

import java.util.Scanner;
import java.sql.*;

public class HospitalManagement {
    private static final String url = "jdbc:mysql://localhost:3306/hms";
    private static final String username = "root";
    private static final String password = "inba@2005";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner scn = new Scanner(System.in);
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected DB");
            Patient patient = new Patient(conn, scn);
            Doctor doctor = new Doctor(conn);
            while (true) {
                System.out.println("Welcom to ABC Hospital Management");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patient");
                System.out.println("3.view Doctor");
                System.out.println("4.book Appointment");
                System.out.println("5.exit");
                System.out.println("Enter your Choice");
                int Choice = scn.nextInt();
                switch (Choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatient();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, conn, scn);
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                        break;

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection conn, Scanner scn) {
        System.out.println("Enter the patient id:");
        int patientId = scn.nextInt();
        System.out.println("Enter the Doctor id:");
        int doctorId = scn.nextInt();
        System.out.println("Enter the appointment Date(YYYY-MM-DD)");
        String appointmentDate = scn.next();
        if (patient.getPatientId(patientId) && doctor.getDoctor(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, conn)) {
                String appointmentQuery = "insert into appointment(patients_id,doctor_id,appointment_date)values(?,?,?)";
                try {
                    PreparedStatement p = conn.prepareStatement(appointmentQuery);
                    p.setInt(1, patientId);
                    p.setInt(2, doctorId);
                    p.setString(3, appointmentDate);
                    int rowsAffected = p.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection conn) {
        String query = "select count(*) from appointment where doctor_id = ? and appointment_date = ?";
        try {
            PreparedStatement p = conn.prepareStatement(query);
            p.setInt(1, doctorId);
            p.setString(2, appointmentDate);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                int count = r.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
