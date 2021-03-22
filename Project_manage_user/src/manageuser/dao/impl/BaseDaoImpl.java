/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * BaseDaoImpl.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.BaseDao;
import manageuser.utils.DatabaseProperties;

/**
 * Lớp định nghĩa các phương thức chung để làm việc với DB
 * 
 * @author PhamMinhThao
 *
 */
public class BaseDaoImpl implements BaseDao {

	/*
	 * Khai báo Connection, PreparedStatement, ResultSet để thực hiện thao
	 * tác với CSDL
	 */
	protected Connection connection;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#connectDB()
	 *
	 */
	@Override
	public void connectDB() throws ClassNotFoundException, SQLException {
		try {
			// Lấy về giá trị user, pass, url, className theo key
			String user = DatabaseProperties.getProperty("user");
			String pass = DatabaseProperties.getProperty("pass");
			String url = DatabaseProperties.getProperty("url");
			String className = DatabaseProperties.getProperty("className");
			// Truyền vào tên lớp interface Driver của JDBC Driver đó để có thể
			// sử
			// dụng JDBC Driver
			Class.forName(className);
			// Tạo kết nối tới DB
			connection = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#closeConnection()
	 *
	 */
	@Override
	public void closeConnection() throws SQLException {
		// Nếu connection khác null và chưa được đóng
		if (connection != null && !connection.isClosed()) {
			try {// Đóng connection
				connection.close();
			} catch (SQLException e) {
				System.out.println("Errors: " + this.getClass().getName() + "."
						+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
				throw e;
			} finally {
				connection = null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageruser.dao.BaseDao#getListColumnNames(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<String> getListColumnNames(String databaseName, String tableName, String columnName)
			throws SQLException, IOException {
		// Khởi tạo danh sách cột
		List<String> listColumn = null;
		try {
			// Khởi tạo đối tượng chứa dữ liệu cấu trúc database
			DatabaseMetaData data = connection.getMetaData();
			// Thực hiện lấy dữ liệu ra ResultSet
			ResultSet rs = data.getColumns(null, databaseName, tableName, columnName);
			listColumn = new ArrayList<>();
			while (rs.next()) {
				// trong khi kq.next = true thi van thuc hien vong lap
				// them từng cột vào danh sách tên cột
				listColumn.add(rs.getString("column_name"));
			}
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
		return listColumn;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#getConnection()
	 *
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#setConnection(java.sql.Connection)
	 *
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#disableAutoCommit()
	 */
	@Override
	public void disableAutoCommit() throws SQLException {
		// Nếu đang mở connection, gán thuộc tính autocommit của connection
		// thành false
		try {
			if (connection != null) {
				connection.setAutoCommit(false);
			}
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#commit()
	 */
	@Override
	public void commit() throws SQLException {
		// Nếu đang mở connection, cho phép commit
		try {
			if (connection != null) {
				connection.commit();
			}
			// In và ném ra ngoại lệ
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manageuser.dao.BaseDao#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		// Nếu đang mở connection, cho phép rollback data
		try {
			if (connection != null) {
				connection.rollback();
			}
			// In và ném ra ngoại lệ
		} catch (SQLException e) {
			System.out.println("Errors: " + this.getClass().getName() + "."
					+ Thread.currentThread().getStackTrace()[1].getMethodName() + "\n" + e.getMessage());
			throw e;
		}
	}

}
