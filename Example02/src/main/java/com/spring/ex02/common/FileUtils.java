package com.spring.ex02.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.spring.ex02.vo.FileVO;

@Component("fileUtils")
public class FileUtils {
	private Log log = LogFactory.getLog(this.getClass());

	private static final String uploadPath = "E:\\workspace-sts\\.metadata\\.plugins\\"
											+ "org.eclipse.wst.server.core\\tmp0\\"
											+ "wtpwebapps\\Example02\\resources\\images";	//저장위치

	public List<FileVO> parseFileInfo(int reg_id, MultipartFile[] file) throws Exception {
		
		List<FileVO> fileList = new ArrayList<FileVO>();  //파일 리스트를 만듦


		File target = new File(uploadPath); //파일 경로
		if (!target.exists()) //만약 해당 경로의 폴더가 없으면 생성
			target.mkdirs();
		
		for (int i = 0; i < file.length; i++) { //첨부파일 개수만큼 반복
			//	file = request.getFile(mf); //파일을 가져와 넣음
			if (!file[i].isEmpty()) {	//첨부파일이 있을 때(기존에 있던 파일은 비어있는 걸로 취급함)
				String orgFileName = file[i].getOriginalFilename();
				String orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf(".")); //파일 확장자
				String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + orgFileExtension;
				int saveFileSize = (int) file[i].getSize();
				
				String extension = orgFileExtension.toLowerCase();
				System.out.println(extension);
				if((extension.equals(".png")||extension.equals(".jpg")||extension.equals(".gif"))){ //확장자 검사
				
					target = new File(uploadPath, saveFileName); //uploadPath(경로)의 폴더에 saveFileName파일의  객체 생성
					file[i].transferTo(target);					 //
					
					FileVO fileInfo = new FileVO(); //파일 정보 리스트에 추가
					fileInfo.setOrg_name(orgFileName);
					fileInfo.setSave_name(saveFileName);
					fileInfo.setF_size(saveFileSize);
					fileInfo.setReg_id(reg_id);
					fileList.add(fileInfo);
					System.out.println("FileUtils reg_id : "+ reg_id);
				}
				
			}
		}

		return fileList;
	}
}
