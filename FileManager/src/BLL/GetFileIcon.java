package BLL;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

import java.util.*;
 
public class GetFileIcon {
/**
 * ��ȡСͼ��
 * @param f
 * @return
 */
	public static Icon getSingleSmallIcon(String path){//��ȡ����Сͼ��
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File file = new File(path);
		Icon icon = fsv.getSystemIcon(file);
		return icon;
	}
	
	
    public static Icon[] getSmallIcon(String path)
    {//��ȡĿ¼�µ�����Сͼ��
    	Icon[] icons = new Icon[9999999];
    	int counter = 0;
    	if(path == "HOME"){
    		List<String> Disks = MemoryInfo.getDisk();    	
    		for(int i = 0; i < Disks.size(); ++i){
    			FileSystemView fsv = FileSystemView.getFileSystemView();
    			File file = new File(Disks.get(i) + "\\");
    			icons[counter++] = fsv.getSystemIcon(file);
    		}	
    	}else{
    	File file = new File(path);
    	File[] files = file.listFiles();
    	for(File a : files){
    		if(a != null && a.exists()){
    			 FileSystemView fsv = FileSystemView.getFileSystemView();    	
    			 icons[counter++] = fsv.getSystemIcon(a);
    		}
    	}
    	}
        return icons;
    }
 
}