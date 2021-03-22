package manageuser.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao {

	/**
	 * Phương thức kết nối tới CSDL
	 * 
	 * @return trả về kết nối
	 * @throws SQLException
	 *             ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException
	 *             ném exception xảy ra khi không tìm thấy file Class
	 */
	public void connectDB() throws ClassNotFoundException, SQLException;

	/**
	 * Đóng kết nối CSDL
	 * 
	 * @throws SQLException
	 *             ném exception xảy ra khi thao tác với DB
	 */
	public void closeConnection() throws SQLException;

	/**
	 * Phương thức lấy ra danh sách cột trong DB
	 * 
	 * @return danh sách cột trong DB
	 * @throws SQLException
	 *             ném exception xảy ra khi thao tác với DB
	 * @throws ClassNotFoundException
	 *             ném exception xảy ra khi không tìm thấy file Class
	 */
	public List<String> getListColumnNames(String databaseName, String tableName, String columnName)
			throws IOException, SQLException;

	/**
	 * Phương thức lấy về 1 connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection();

	/**
	 * Phương thức gán connection cho đối tượng Dao
	 * 
	 * @param connection
	 *            Connection muốn gán
	 */
	public void setConnection(Connection connection);

	/**
	 * Thiết lập cho connection không tự động commit
	 * 
	 * @throws SQLException
	 *             ném exception xảy ra khi thao tác với DB
	 */
	public void disableAutoCommit() throws SQLException;

	/**
	 * Cho phép connection commit các thao tác với CSDL
	 * 
	 * @throws SQLException
	 *             ném exception xảy ra khi thao tác với DB
	 */
	public void commit() throws SQLException;

	/**
	 * Thực hiện rollback
	 * 
	 * @throws SQLException
	 *             ném exception xảy ra khi thao tác với DB
	 */
	public void rollback() throws SQLException;
}
