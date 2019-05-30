package BLL;

import java.io.File;

public class DirectoryInfo {
	 public static DirectoryInfo _instance = null;
	 public long File_Num, Directory_Num;
	 public DirectoryInfo(){
		 this._instance = this;
		 File_Num = 0;
		 Directory_Num = 0;
	 }
	
	 public long getDirSize(File file) {     
	        //�ж��ļ��Ƿ����     
	        if (file.exists()) {     
	            //�����Ŀ¼��ݹ���������ݵ��ܴ�С    
	            if (file.isDirectory()){     	            	
	            	this.Directory_Num++;
	                File[] children = file.listFiles();     
	                long size = 0;     
	                for (File f : children) 
	                    size += getDirSize(f);     
	                return size;     
	            } else {
	            	this.File_Num++;
	                long size = file.length();        
	                return size;     
	            }     
	        } else {     
	            System.out.println("�ļ������ļ��в����ڣ�����·���Ƿ���ȷ��");     
	            return 0;     
	        }     
	    }     
}
