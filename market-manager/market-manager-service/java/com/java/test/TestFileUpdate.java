package com.java.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.java.utils.FastDFSClient;

public class TestFileUpdate {

	@Test
	public void testFastDFS() throws Exception {
		// 加载对应jar包
		// 加载配置文件
		ClientGlobal.init("D:\\Perdio6\\Java\\market-manager-web\\src\\main\\resources\\resource\\fileconfig.conf");
		// 3、创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		// 4、创建一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5、声明一个StorageServer对象，null。
		StorageServer storageServer = null;
		// 6、获得StorageClient对象。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 7、直接调用StorageClient对象方法上传文件即可。
		String[] strings = storageClient.upload_file("D:\\qwer.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}

	@Test
	public void TestFastDFSUtils() {
		try {
			//加载配置文件
			FastDFSClient fastDFSClient = new FastDFSClient(
					"D:\\Perdio6\\Java\\market-manager-web\\src\\main\\resources\\resource\\fileconfig.conf");
			//文件路径
			String uploadFile = fastDFSClient.uploadFile("D:\\20140714_164100_IMG_0011.JPG", "jpg");
			System.out.println(uploadFile);
		} catch (Exception e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		}
	}
}
