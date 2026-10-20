package com.scc.dao;

import com.scc.model.ScenarioEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    // Add new appointment
    public boolean addAppointment(String patientName, String doctor,
                                  double consultFee, String status) {
        String sql = "INSERT INTO appointments (patient_name, doctor, consult_fee, status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patientName);
            pstmt.setString(2, doctor);
            pstmt.setDouble(3, consultFee);
            pstmt.setString(4, status);

            int rows = pstmt.executeUpdate();
            System.out.println("✅ Saved successfully!");
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("❌ Insert error: " + e.getMessage());
            return false;
        }
    }

    // === THIS IS WHAT SHOWS IN CHOICE 2 ===
    public List<ScenarioEntity> getAllAppointments() {
        List<ScenarioEntity> list = new ArrayList<>();
        String sql = "SELECT id, patient_name, doctor, consult_fee, status " +
                "FROM appointments ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor"),
                        rs.getDouble("consult_fee"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Fetch error: " + e.getMessage());
        }
        return list;
    }
}