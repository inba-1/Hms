package com.hospitalmanagement;

import java.util.*;
import java.sql.*;

public class Doctor {
    private Connection con;

    public Doctor(Connection con) {
        this.con = con;
    }

    public void viewDoctor() {
        String query = "Select * from doctor";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet r = ps.executeQuery();
            /*
             * This object stores the table-like result returned by the SELECT query.
             * You use it to read each row of the result.
             * ResultSet in JDBC represents the result of a SQL SELECT query —
             * it stores the rows and columns of data just like a table.
             */
            System.out.println("Doctors");
             System.out.println("____________________________________________");
            System.out.printf("| %-10s | %-10s | %-15s |\n", "Doctor ID", "Name","Dept" );
            System.out.println("|-------------------------------------------|");
            while (r.next()) {
                // r.next()
                // is used to move the cursor to the next row in the ResultSet,
                // returns a boolean value.
                int id = r.getInt("id");
                String name = r.getString("name");
                String dept = r.getString("dept");

                System.out.printf("| %-10d | %-10s | %-15s |\n", id, name, dept);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctor(int id) {
        String query = "select * from doctor where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet r = ps.executeQuery();
            if (r.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
