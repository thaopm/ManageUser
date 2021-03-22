/**
 * Copyright(C)Jul 17, 2019 Luvina Software Company
 * MessageProperties.java, Jul 17, 2019, Phạm Minh Thảo
 */
package manageuser.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * Lớp đọc dữ liệu từ file message_ja.properties
 * 
 * @author PhamMinhThao
 *
 */
public class MessageProperties {
	// Khai báo map để lưu key và nội dung thông báo theo từng key
	private static Map<String, String> data = new HashMap<>();

	/**
	 * Khối lệnh đọc dữ liệu trong file properties, khối lệnh này sẽ chỉ được
	 * gọi 1 lần duy nhất khi lớp được load vào bộ nhớ
	 */
	static {
		try {
			// Tạo đối tượng Properties để chứa cặp key và value
			Properties props = new Properties();
			/**
			 * InputStream là một class trừu tượng vì vậy không thể khởi tạo đối tượng
			 * InputStream thông qua chính class InputStream
			 */
			// getResourceAsStream: Trả về 1 InputStream lấy dữ liệu từ file
			// message_ja.properties
			// .class: Tạo đối tượng class để gọi getResourceAsStream
			// .load: Tải dữ liệu từ đối tượng InputStream
			props.load(MessageProperties.class.getResourceAsStream(("/message_ja.properties")));

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
			System.out.println("MessageProperties: " + e.getMessage());
		}
	}

	/**
	 * Phương thức lấy thông báo trong file properties thông qua key truyền vào
	 * 
	 * @param key
	 *            key đánh dấu nội dung cần lấy
	 * @return String Nội dung cần lấy
	 */
	public static String getMessage(String key) {
		String string = "";
		if (data.containsKey(key)) {
			string = data.get(key);
		}
		return string;
	}
}
