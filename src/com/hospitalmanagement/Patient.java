package com.hospitalmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.xdevapi.PreparableStatement;

public class Patient {
    private Connection conn;// Connection is an interface in jdbc it used set connection btw db and prgm
    private Scanner scn;

    public Patient(Connection conn, Scanner scn) {
        this.conn = conn;
        this.scn = scn;
    }

    public void addPatient() {
        System.out.println("Enter the patient details:");
        System.out.println("Enter the Name");
        String name = scn.next();
        System.out.println("enter the age");
        int age = scn.nextInt();
        System.out.println("Enter Patient gender");
        String gender = scn.next();
        try {
            String query = "INSERT INTO Patients(name,age,gender) VALUES(?,?,?)";

            // PreparedStatement is an
            // interface from java.sql.*stmts, package used for executing parameterized SQL
            // queries.

            PreparedStatement ps = conn.prepareStatement(query);
            // prepareStatement(query) is a method of Connection
            // that takes a SQL query as a string and prepares it for execution.

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();
            /*
             * It returns an int value — the number of rows affected by the query
             * is used in Java JDBC to execute SQL statements that modify the database, such
             * as:
             * INSERT UPDATE DELETE
             * DDL statements like CREATE, ALTER, or DROP
             */
            if (affectedRows > 0) {
                System.out.println("Patient added");
            } else {
                System.out.println("Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void viewPatient() {
        String query = "SELECT * from Patients";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet r = ps.executeQuery();
            /*
             * This object stores the table-like result returned by the SELECT query.
             * You use it to read each row of the result.
             * ResultSet in JDBC represents the result of a SQL SELECT query —
             * it stores the rows and columns of data just like a table.
             */
            System.out.println("_____________________________________");
            System.out.printf("| %-10s | %-5s | %-3s | %-6s |\n", "Patient ID", "Name", "Age", "Gender");
            System.out.println("|------------|-------|-----|--------|");

            while (r.next()) {
                // r.next()
                // is used to move the cursor to the next row in the ResultSet,
                // returns a boolean value.
                int id = r.getInt("id");
                String name = r.getString("name");
                int age = r.getInt("age");
                String gender = r.getString("gender");
                System.out.printf("| %-10d | %-5s | %-3d | %-6s |\n", id, name, age, gender);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientId(int id) {
        String q = "SELECT * FROM Patients where id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setInt(1, id);
            ResultSet r = ps.executeQuery();
            if (r.next())
                return true;
            else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

}
