/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * DatabaseProperties.java, Jul 17, 2019, PhaÌ£m Minh ThaÌ‰o
 */
package manageuser.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Lớp đọc file db.properties
 * 
 * @author PhamMinhThao
 *
 */
public class DatabaseProperties {
	// Khai báo map để lưu key và nội dung thông báo theo từng key
	private static Map<String, String> data = new HashMap<String, String>();

	/**
	 * Khối lệnh đọc dữ liệu trong file properties, khối lệnh này sẽ chỉ được
	 * gọi 1 lần duy nhất khi lớp được load vào bộ nhớ
	 */
	static {
		try {
			Properties props = new Properties();
			/**
			 * InputStream là một class trừu tượng vì vậy không thể khởi tạo đối tượng
			 * InputStream thông qua chính class InputStream
			 */
			// getResourceAsStream: Trả về 1 InputStream lấy dữ liệu từ file
			// db.properties
			// .class: Tạo đối tượng class để gọi getResourceAsStream
			// .load: Tải dữ liệu từ đối tượng InputStream
			props.load(DatabaseProperties.class.getResourceAsStream(("/db.properties")));
			// Liệt kê từng key trong danh sách properties

			// Lấy về 1 set chứa danh sách các key và nội dung tương ứng của key trong file
			// properties
			Set<String> set = props.stringPropertyNames();
			// Duyệt set để lưu từng key và nội dung tương ứng của key vào Map
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				data.put(key, props.getProperty(key));
			}
		} catch (IOException e) {
			System.out.println("DatabaseProperties: " + e.getMessage());
		}
	}

	/**
	 * Phương thức lấy thông báo trong file properties thông qua key truyền vào
	 * 
	 * @param key
	 *            key đánh dấu nội dung cần lấy
	 * @return String Nội dung cần lấy
	 */
	public static String getProperty(String key) {
		String string = "";
		if (data.containsKey(key)) {
			string = data.get(key);
		}
		return string;
	}

}
