$(document).ready(function () {
	$("#uploadBtn").click(function () {
		$('input[name="file"]').each(function (index, value) {
			var file = value.files[0];
			if (file) {
				var formData = new FormData();
				formData.append('file', file);
				$.ajax({
					url: 'jersey/upload/jpg',
					type: 'post',
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function (data, textstatus, jqxhr) {
						var message = jqxhr.responseText;
						$("#messages").append("<li>" + message + "</li>");
					},
					error: function (jqxhr, textstatus, errorthrown) {
						$("#messages").append("<li style='color: red;'>" + textstatus + "</li>");
					}
				});
			}
		});
	});
});