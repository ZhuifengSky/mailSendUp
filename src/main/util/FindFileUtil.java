package main.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.bean.FileFindResultBean;

public class FindFileUtil {
	
	/**
	 * 查询母目标文件
	 * @param path
	 * @param attachName
	 * @param fileType
	 * @return
	 */
	public static FileFindResultBean findTargetFilePath(String attachPath,String attachName,String fileType){
		FileFindResultBean resultBean = new FileFindResultBean();
		File targeFolder = new File(attachPath);
		List<String> attchPathes = new ArrayList<String>();
		if (targeFolder.exists()) {
			String regEx = "^"+attachName+"\\s*\\([0-9]*\\)."+fileType+"$";   
	        Pattern p = Pattern.compile(regEx); 	        
			File[] listFile= targeFolder.listFiles();
			if (listFile.length>0) {
				for (File targetFile : listFile) {
					String filName = targetFile.getName();
					if (filName.equals(attachName+"."+fileType)) {
						if (!attchPathes.contains(attachPath+File.separatorChar+filName)) {
							attchPathes.add(attachPath+File.separatorChar+filName);
						}
					}else{
						Matcher matcher=p.matcher(filName);
						if (matcher.matches()) {
							if (!attchPathes.contains(attachPath+File.separatorChar+filName)) {
								attchPathes.add(attachPath+File.separatorChar+filName);
							}
						}					
					}
				}				
			}else{
				resultBean.setCode("401");
				resultBean.setInfo("查询目录下没有文件!");
			}
		}else{
			resultBean.setCode("404");
			resultBean.setInfo("查询的目录不存在!");
		}
		if (attchPathes!=null && attchPathes.size()>0) {
			resultBean.setCode("200");
			resultBean.setAttchPathes(attchPathes);
		}else{
			resultBean.setCode("205");
			resultBean.setInfo("未找到能匹配文件!");
		}
		return resultBean;		
	}
}
