package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件测试
 * @author pc-zw
 *
 */
public class FileTest {

	
	public static void main(String[] args) {
	
		String path = "G:\\mail\\file";
		String attachName = "zhangwu";
		String fileType = "tar";
		File targeFolder = new File(path);
		List<String> attchPathes = new ArrayList<String>();
		if (targeFolder.exists()) {
			String regEx = "^"+attachName+"\\s*\\([0-9]*\\)."+fileType+"$";   
	        Pattern p = Pattern.compile(regEx); 	        
			File[] listFile= targeFolder.listFiles();
			if (listFile.length>0) {
				for (File targetFile : listFile) {
					String filName = targetFile.getName();
					if (filName.equals(attachName+"."+fileType)) {
						if (!attchPathes.contains(path+File.separatorChar+filName)) {
							attchPathes.add(path+File.separatorChar+filName);
						}
					}else{
						Matcher matcher=p.matcher(filName);
						if (matcher.matches()) {
							if (!attchPathes.contains(path+File.separatorChar+filName)) {
								attchPathes.add(path+File.separatorChar+filName);
							}
						}					
					}
				}				
			}else{
				System.out.println("查询目录下没有文件!");
			}
		}else{
			System.out.println("查询的目录不存在!");
		}
		
		if (attchPathes!=null && attchPathes.size()>0) {
			for (String string : attchPathes) {
				System.out.println("查到的路径是:"+string);
			}
		}
	}
}
