/* Ẩn hiện trình độ tiếng Nhật */

function displayJapaneseLevel() {
	var x = document.getElementById("level");
	// xét trạng thái
	if (x.style.display === "none") {
		x.style.display = "block";
	} else {
		x.style.display = "none";
	}
	return false;
}

/* Hiển thị pop up khi click delete user trên màn hình ADM005 */

function confirmDelete(mess, path, value) {
	if (confirm(mess)) {
		// Tạo form với phương thức post gửi đến đường dẫn được truyền vào
		var form = document.createElement('form');
		form.method = 'post';
		form.action = path;

		// Tạo 1 input có type là hidden để lưu id người dùng và thêm vào form
		var hiddenField = document.createElement('input');
		hiddenField.type = 'hidden';
		hiddenField.name = 'userId';
		hiddenField.value = value;
		form.appendChild(hiddenField);

		form.submit();
	}
}