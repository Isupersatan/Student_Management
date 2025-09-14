package com.sms;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

	public void addStudent(Student s) throws SQLException {
		String sql = "INSERT INTO student (roll_no, first_name, last_name, dob, gender, email, mobile, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, s.getRollNo());
			ps.setString(2, s.getFirstName());
			ps.setString(3, s.getLastName());
			ps.setDate(4, s.getDob() == null ? null : Date.valueOf(s.getDob()));
			ps.setString(5, s.getGender());
			ps.setString(6, s.getEmail());
			ps.setString(7, s.getMobile());
			ps.setString(8, s.getAddress());
			ps.executeUpdate();

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					s.setId(rs.getInt(1));
			}
		}
	}

	public void updateStudent(Student s) throws SQLException {
		String sql = "UPDATE student SET roll_no=?, first_name=?, last_name=?, dob=?, gender=?, email=?, mobile=?, address=? WHERE id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, s.getRollNo());
			ps.setString(2, s.getFirstName());
			ps.setString(3, s.getLastName());
			ps.setDate(4, s.getDob() == null ? null : Date.valueOf(s.getDob()));
			ps.setString(5, s.getGender());
			ps.setString(6, s.getEmail());
			ps.setString(7, s.getMobile());
			ps.setString(8, s.getAddress());
			ps.setInt(9, s.getId());
			ps.executeUpdate();
		}
	}

	public void deleteStudent(int id) throws SQLException {
		String sql = "DELETE FROM student WHERE id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		}
	}

	public Student findById(int id) throws SQLException {
		String sql = "SELECT * FROM student WHERE id=?";
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next())
					return mapRow(rs);
			}
		}
		return null;
	}

	public List<Student> searchByNameOrRoll(String keyword) throws SQLException {
		String sql = "SELECT * FROM student WHERE first_name LIKE ? OR last_name LIKE ? OR roll_no LIKE ? ORDER BY created_at DESC";
		List<Student> list = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			String q = "%" + keyword + "%";
			ps.setString(1, q);
			ps.setString(2, q);
			ps.setString(3, q);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(mapRow(rs));
			}
		}
		return list;
	}

	public List<Student> findAll() throws SQLException {
		String sql = "SELECT * FROM student ORDER BY id DESC";
		List<Student> list = new ArrayList<>();
		try (Connection conn = DBUtil.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next())
				list.add(mapRow(rs));
		}
		return list;
	}

	private Student mapRow(ResultSet rs) throws SQLException {
		Student s = new Student();
		s.setId(rs.getInt("id"));
		s.setRollNo(rs.getString("roll_no"));
		s.setFirstName(rs.getString("first_name"));
		s.setLastName(rs.getString("last_name"));
		Date d = rs.getDate("dob");
		if (d != null)
			s.setDob(d.toLocalDate());
		s.setGender(rs.getString("gender"));
		s.setEmail(rs.getString("email"));
		s.setMobile(rs.getString("mobile"));
		s.setAddress(rs.getString("address"));
		return s;
	}
}
