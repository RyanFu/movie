//�ϴ�ͼƬ

function uploadPic(){
	$.ajaxFileUpload({
			url:'/creative/uploadPic',
			secureuri:false,
			fileElementId:'creativePic',
			dataType: 'json',
			success: function (data, status){
				if (data.result == 1) {
					//alert(data.mediaUrl);
                    //�޸�Ԥ��ͼƬ��url
                    $("#previewPic1").attr("src", baseUrl + data.mediaUrl);
                    $("#previewPic2").attr("src", baseUrl + data.mediaUrl);
                    //�������ص�picurlΪ�ϴ�ͼƬ����
                    $("#picUrl").val(data.mediaUrl);
				} else {
					alert(data.error);
					$("#picUrl").val("");
				}
			},
			error: function (data, status, e){
				alert("�ϴ�ʧ�ܣ���ȷ���ļ���Сû�г�������");
				$("#picUrl").val("");
			}
		}
	)
}
