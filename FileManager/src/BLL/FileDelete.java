package BLL;

import java.io.File;

import javax.swing.JOptionPane;

/**
 * ɾ���ļ���Ŀ¼
 *
 */
public class FileDelete {

    /**
     * ɾ���ļ����������ļ����ļ���
     *
     * @param fileName
     *            Ҫɾ�����ļ���
     * @return ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
        	JOptionPane.showMessageDialog(null, "ɾ���ļ�ʧ��:" + fileName + "�����ڣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);  
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * ɾ�������ļ�
     *
     * @param fileName
     *            Ҫɾ�����ļ����ļ���
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
            	JOptionPane.showMessageDialog(null, "ɾ�������ļ�" + fileName + "�ɹ���", "��ʾ",JOptionPane.WARNING_MESSAGE);  
                return true;
            } else {
            	JOptionPane.showMessageDialog(null, "ɾ�������ļ�" + fileName + "ʧ�ܣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);  
                return false;
            }
        	} else {
        	JOptionPane.showMessageDialog(null, "ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        	}
    }

    /**
     * ɾ��Ŀ¼��Ŀ¼�µ��ļ�
     *
     * @param dir
     *            Ҫɾ����Ŀ¼���ļ�·��
     * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteDirectory(String dir) {
        // ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
        	JOptionPane.showMessageDialog(null, "ɾ��Ŀ¼ʧ�ܣ�" + dir + "�����ڣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        boolean flag = true;
        // ɾ���ļ����е������ļ�������Ŀ¼
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // ɾ�����ļ�
            if (files[i].isFile()) {
                flag = FileDelete.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // ɾ����Ŀ¼
            else if (files[i].isDirectory()) {
                flag = FileDelete.deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
        	JOptionPane.showMessageDialog(null, "ɾ��Ŀ¼ʧ�ܣ�", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        // ɾ����ǰĿ¼
        if (dirFile.delete()) {
        	JOptionPane.showMessageDialog(null, "ɾ��Ŀ¼" + dir + "�ɹ���", "��ʾ",JOptionPane.WARNING_MESSAGE);
            return true;
        } else {
            return false;
        }
    }    
}