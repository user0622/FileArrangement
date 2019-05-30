package UI;

import BLL.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainForm extends JFrame implements ActionListener{
	public static MainForm _instance;
	//�������
	JPanel p, SortPanel, SearchPanel, ShowPanel, FunctPanel, TreePanel;
	//����
	JTree BigTree;
	
	FilesTree filesTree;
	//�������ļ���ͼ��������������
	JScrollPane ScrollShow, TreeShow;
	//��ѡ��ť����ʾ�������͵��ļ�
	JRadioButton AllFiles,Videos,Text,Picture,Music;
	//���ڵ�
    DefaultMutableTreeNode node;
    //��ť
	JButton PreBtn, LatBtn, GoBtn;
	//
	ButtonGroup Classify;
	//�����б����
	JComboBox SortList, SortType;
	//�����ı���
	JTextField SearchText, GuideText;
	//ѡ���
	JCheckBox FileCheck, DirCheck;
	//��ǩ���
	JLabel SortTxt, SearchTxt, SearchType;
	//��������
	String Sort_Items[] = {"�ļ���С","�޸�ʱ��","����ĸ"};
	//��������
	String Sort_Type_Items[] = {"����","����"};
	public String Cur_URL = "";
	String Pre_URL = "";
	String LatURL = "";
	//�����ļ���ʽƥ��ĳ�ʼ��
	List<String> VideoType = Arrays.asList("avi","wmv","rm","rmvb","mpeg1","mpeg2","mpeg4","mp4","3gp","asf","swf","vob","dat","mov","m4v","flv","f4v","mkv","mts","ts","qsv","AVI","WMV","RM","RMVB","MPEG1","MPEG2","MPEG4","MP4","3GP","ASF","SWF","VOB","DAT","MOV","M4V","FLV","F4V","MKV","MTS","TS","QSV");  
	List<String> GraphType = Arrays.asList("bmp","gif","jpeg","jpeg2000","tiff","psd","png","swf","svg","pcx","dxf","wmf","emf","lic","eps","tga","jpg","BMP","GIF","JPEG","JPEG2000","TIFF","PSD","PNG","SWF","SVG","PCX","DXF","WMF","EMF","LIC","EPS","TGA","JPG");
	List<String> TxtType = Arrays.asList("txt","doc","docx","wps","pdf","chm","pdg","wdl","xls","xlsx","ppt","pptx","java","c","cpp","py");
	List<String> MusicType = Arrays.asList("cd","wave","wav","aiff","au","mp3","midi","wma","aac","ape","CD","WAVE","WAV","AIFF","AU","MP3","MIDI","WMA","RealAudio","VQF","OggVorbis","AAC","APE");
	public Map<String, String> Maps = new HashMap<String,String>();
	//�ļ��б����ر���
	JList<String> list;
	public DefaultListModel defaultListModel;
	public Stack<String> stack, stack_return;
	//�½��Ҽ������˵���ѡ�����������
	JPopupMenu jPopupMenu = null;
	JPopupMenu jPopupMenu2 = null;
	JPopupMenu jPopupMenu3 = null;
	//�½��Ҽ������û�ж�������
	JMenuItem[] JMIs = new JMenuItem[10];
	JMenuItem[] JMIs2 = new JMenuItem[5];
	JMenuItem delete = new JMenuItem("ɾ��");
	public Icon[] AllIcons = new Icon[999999];//�洢�����õ����ļ�ͼ��
	public int Icon_Counter = 0;
	//����GB,MB,KB,B��Ӧ���ֽ��������㻻���ļ���С����λ
	long[] Sizes = {1073741824,1048576,1024,1};
	String[] Size_Names = {"GB", "MB", "KB", "B"};
	Boolean isSearching = false;
	
	public MainForm(){//������
		this._instance = this;
		//���ô�����
		this.setTitle("�ļ�����ϵͳ");
		//���ô��ڵ�xλ�á�yλ�á�����
		this.setBounds(500, 500, 1010, 650);
		//��ʼ��������Ĭ�ϲ���
		this.getContentPane().setLayout(null);
		//���ú���������
		Init();
		//������������Ļ�м�
		this.setLocationRelativeTo(null);
		//��ʾ���JFrame
		this.setVisible(true);
		//�����ر�ʱ�Ĳ���
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//���ɵ������ڴ�С
		this.setResizable(false);
	}
	
	public void Init(){
		//����panel��ʼ��
		p = new JPanel();
		SortPanel = new JPanel();
		SearchPanel = new JPanel();
		ShowPanel = new JPanel();
		FunctPanel = new JPanel();
		TreePanel = new JPanel();
		//�����������Ϳ�ߣ�������ʽ����
		p.setBounds(5, 5, 1000, 50);
		p.setLayout(new FlowLayout(FlowLayout.LEFT,15,5));
    
		//������ѯ����ģ��
		SortPanel.setSize(200, 80);
        SortPanel.setLayout(new FlowLayout());
        
        //���JRadioButton��ʼ��
        AllFiles = new JRadioButton("�����ļ�");
        AllFiles.setSelected(true);
		Videos = new JRadioButton("��Ƶ");
		Picture  = new JRadioButton("ͼƬ");
		Music = new JRadioButton("����");
		Text = new JRadioButton("�ĵ�");
		Videos.addActionListener(this);
		Picture.addActionListener(this);
		Music.addActionListener(this);
		Text.addActionListener(this);
		AllFiles.addActionListener(this);
		
		Classify = new ButtonGroup();
		Classify.add(AllFiles);
		Classify.add(Videos);
		Classify.add(Picture);
		Classify.add(Music);
		Classify.add(Text);
		
		SortTxt = new JLabel("����");
		SortList = new JComboBox(Sort_Items);
		SortType = new JComboBox(Sort_Type_Items);
		SortPanel.add(SortTxt);
		SortPanel.add(SortList);
		SortPanel.add(SortType);
		
		SearchTxt = new JLabel("����");
		SearchTxt.setBounds(5,5,50,30);
		SearchText = new JTextField(15);
		SearchText.setBounds(50, 5, 120, 30);
		SearchText.addActionListener(this);		
		
		SearchType = new JLabel("��������");		
		FileCheck = new JCheckBox("�ļ�");
		FileCheck.setSelected(true);
		DirCheck = new JCheckBox("Ŀ¼");
		DirCheck.setSelected(true);
		FileCheck.addActionListener(this);
		DirCheck.addActionListener(this);
		
		p.add(AllFiles);
		p.add(Videos);
		p.add(Picture);
		p.add(Music);
		p.add(Text);
		p.add(SortPanel);
		p.add(SearchTxt);
		p.add(SearchText);	
		p.add(SearchType);
		p.add(FileCheck);
		p.add(DirCheck);
	   
		 //���ϵ�����35
        FunctPanel = new JPanel();
        FunctPanel.setBounds(5, 50, 990, 45);
        FunctPanel.setLayout(null);
        PreBtn = new JButton("<");
        PreBtn.setFont(new Font("Serif", Font.PLAIN, 20));
        PreBtn.setBounds(5, 5, 85, 25);
        PreBtn.addActionListener(this);
        LatBtn = new JButton(">");
        LatBtn.setFont(new Font("Serif", Font.PLAIN, 20));
        LatBtn.setBounds(85, 5, 85, 25);
        LatBtn.addActionListener(this);
        GuideText = new JTextField();
        GuideText.setBounds(180, 5, 740, 25);
        GuideText.addActionListener(this);
        GoBtn = new JButton("Go!");
        GoBtn.setFont(new Font("Serif", Font.PLAIN, 15));
        GoBtn.setBounds(925, 5, 65, 25);
        GoBtn.addActionListener(this);
        FunctPanel.add(PreBtn);
        FunctPanel.add(LatBtn);
        FunctPanel.add(GuideText);
        FunctPanel.add(GoBtn);
        this.add(FunctPanel);
        
		//�в��ļ��б�
        stack = new Stack<String>();
        stack_return = new Stack<String>();
        ShowPanel.setSize(800, 610);
        ShowPanel.setLocation(200, 90);
        ShowPanel.setLayout(null);    
        list = new JList<String>();
        jPopupMenu = new JPopupMenu();//�ļ�/�ļ��е����Բ˵�
        jPopupMenu2 = new JPopupMenu();//���̵����Բ˵�
        JMIs[0] = new JMenuItem("��");
        JMIs[1] = new JMenuItem("ɾ��");
        JMIs[2] = new JMenuItem("������");
        JMIs[3] = new JMenuItem("����");
        for(int k = 0; k < 4; ++k){//�ļ�/�ļ��е����Բ˵���ʼ��
        	JMIs[k].addActionListener(this);
        	jPopupMenu.add(JMIs[k]);            	
        }        
        JMIs2[0] = new JMenuItem("��");
        JMIs2[1] = new JMenuItem("����");
        for(int k = 0; k < 2; ++k){//���̵����Բ˵���ʼ��
        	JMIs2[k].addActionListener(this);
        	jPopupMenu2.add(JMIs2[k]);            	
        }    
        jPopupMenu3 = new JPopupMenu();
        delete.addActionListener(this);
        jPopupMenu3.add(delete);
        list.add(jPopupMenu3);
        list.add(jPopupMenu2);
        list.add(jPopupMenu);
        
        Home_List();//��ʾ���̸�Ŀ¼
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(list.getSelectedIndex() != -1){
					if(e.getClickCount() == 1){//����listʱ�������¼�
					
					}else if(e.getClickCount() == 2){//˫��listʱ�����ļ���������Ŀ¼
						System.out.println(list.getSelectedValue());
						twoClick(list.getSelectedValue());												
					}
					if(e.getButton() == 3){//�һ�listʱ���򿪲˵���
						if(Cur_URL != ""){
							if(list.getSelectedValuesList().size() == 1){
								jPopupMenu.show(list,e.getX(),e.getY()); //����һ����ǵ����ļ��к��ļ�����Ӧ��һ��������ȫ�Ĳ˵���
							}else if(list.getSelectedValuesList().size() > 1){//���ѡ�ж���ļ��к��ļ�����ֻ֧��ɾ������
								jPopupMenu3.show(list, e.getX(), e.getY());
							}
						}		                 
						else if(Cur_URL == "" && list.getSelectedValuesList().size() == 1){
							jPopupMenu2.show(list, e.getX(), e.getY()); //����һ����Ǵ��̣��˵�����ֻ���С��򿪡��͡����ԡ�����
						}						
					}
				}
			}
		});	
	        
		ScrollShow = new JScrollPane(list);
		ShowPanel.add(ScrollShow);
		ScrollShow.setSize(790, 520);
		ScrollShow.setLocation(5, 5);
		this.add(ShowPanel);
		
		//���Ŀ¼��״ͼ
        TreePanel.setSize(190,610);
        TreePanel.setLocation(5, 90);
        TreePanel.setLayout(null); 
        filesTree = new FilesTree();
        TreeShow = new JScrollPane(filesTree);
        TreeShow.setBounds(5, 5, 185, 520);
        TreePanel.add(TreeShow);
        this.add(TreePanel);                   
        this.add(p);		
	}
	
	private void oneClick(String choice){//���һ�εķ���
		
	}
	
	public void twoClick(String choice){//�������ʱ���¼�
		if(!isSearching){//�����ʱ��������״̬�����������ĵ������
			choice += "\\";		
			File file = new File(Cur_URL + choice);
			if(file.isDirectory()){
				Cur_URL += choice;	
				stack.push(Cur_URL);
				Go_There();
			}else{
				OpenIt(file);
			}
		}else{//�����������״̬���Ǿ�Ҫ��map����ȡ���ǵ�URL����Ϊ������˳�򶼴����ˣ��޷���һ��URL��Ӧ
			File file = new File(Maps.get(choice));
			OpenIt(file);
		}
	}
	
	public void Home_List(){//�ص���ʼ���̽���
		List<String> Disks = MemoryInfo.getDisk();
		defaultListModel = new DefaultListModel();
		for(int i = 0; i < Disks.size(); ++i){
			defaultListModel.addElement(Disks.get(i));
		}
		Icon[] icons = GetFileIcon.getSmallIcon("HOME");//�õ���Ŀ¼�µ�ͼ��
		list.setModel(defaultListModel);
		list.setCellRenderer(new MyCellRenderer(icons));
		GuideText.setText("");
		Cur_URL = "";
		stack.push(Cur_URL);
	}
	
	public void OpenIt(File file){//���õ����еĳ��򡰴򿪡��ļ��ķ���
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void Go_There(){//��ȥ�ģ���ȥ�ģ�������ת������
			GuideText.setText(Cur_URL);
			if(Cur_URL != ""){//Cur_URL�ǿգ�������Ŀ��Ŀ¼
				defaultListModel.clear();
				String[] getString = GetFileNames.getFileName(Cur_URL);		
				for(int i = 0; i < getString.length; ++i){
					defaultListModel.addElement(getString[i]);		
				}	
				Icon[] icons = GetFileIcon.getSmallIcon(Cur_URL);
				list.setModel(defaultListModel);
				list.setCellRenderer(new MyCellRenderer(icons));
				
			}else{//Cur_URLΪ��ʱ������ת�ظ�Ŀ¼
				Home_List();
			}
	}
	
	public void GetAllResults(String path){//�������ܺ��ĺ���
		  if(path != ""){		    	
				String[] getString = GetFileNames.getFileName(path);
				for(int i = 0; i < getString.length; ++i){
					File file = new File(path + getString[i] + "\\");						
					if(file.isDirectory()){//�������ļ�����						
						GetAllResults(path + getString[i] + "\\");
					}else{
						String prefix = getString[i].substring(getString[i].lastIndexOf('.') + 1);					
						if(VideoType.contains(prefix) && Videos.isSelected()){//�ж��Ƿ�Ϊ��Ƶ�ļ�����Ƶ��ť��ѡ�У�����������ǵ���ʾĿ¼��
							System.out.println(getString[i]);
							Maps.put(getString[i], path + getString[i]);//��Maps�洢�ļ�����·���Ķ�Ӧ��ϵ
							defaultListModel.addElement(getString[i]);
							AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
						}else if(GraphType.contains(prefix) && Picture.isSelected()){//�ж��Ƿ�ΪͼƬ�ļ���ͼƬ��ť��ѡ�У�����������ǵ���ʾĿ¼��
							Maps.put(getString[i], path + getString[i]);//��Maps�洢�ļ�����·���Ķ�Ӧ��ϵ
							defaultListModel.addElement(getString[i]);
							AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
						}else if(TxtType.contains(prefix) && Text.isSelected()){//�ж��Ƿ�Ϊ�ĵ��ļ����ĵ���ť��ѡ�У�����������ǵ���ʾĿ¼��
							Maps.put(getString[i], path + getString[i]);//��Maps�洢�ļ�����·���Ķ�Ӧ��ϵ
							defaultListModel.addElement(getString[i]);
							AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
						}else if(MusicType.contains(prefix) && Music.isSelected()){
							Maps.put(getString[i], path + getString[i]);//��Maps�洢�ļ�����·���Ķ�Ӧ��ϵ
							defaultListModel.addElement(getString[i]);
							AllIcons[Icon_Counter++] = GetFileIcon.getSingleSmallIcon(path + getString[i]);
						}
					}				
				}
		    }
	}		
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MainForm m = new MainForm();     
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == PreBtn){//������
			LatURL = Cur_URL;			
			if(!stack.isEmpty()){
				stack.pop();//ÿ�ӵ�ǰһ��Ŀ¼����֮ǰ��Ŀ¼ʱ��stack��Ҫ��ջ		
				stack_return.push(Cur_URL);//����֮ǰ��Ŀ¼���뷵��ջstack_return
				if(!stack.isEmpty()){					
					Cur_URL = stack.peek();//��ջ�еõ���һ�����ʵ�Ŀ¼��������ǰĿ¼					
				}
				else{
					Cur_URL = "";//���ջΪ�գ���˵��ǰ���Ǹ�Ŀ¼����ֱ���ÿ�
				}
				Go_There();
			}
			if(isSearching){//�����������״̬���Ǵ�ʱӦ�ý���
				isSearching = false;
				AllFiles.setSelected(true);
			}
		}
		
		else if(e.getSource() == LatBtn){//������
			if(!stack_return.isEmpty()){//�����ߣ���ӷ���ջ����URL
				Cur_URL = stack_return.peek();
				stack_return.pop();
				stack.push(Cur_URL);
				Go_There();
			}
			if(isSearching){//�����������״̬���Ǵ�ʱӦ�ý���
				isSearching = false;
				AllFiles.setSelected(true);
			}
		}
		
		else if(e.getSource() == JMIs[0] || e.getSource() == JMIs2[0]){	//���ļ�/�ļ���/����
			if(!isSearching){
				String url = Cur_URL + list.getSelectedValue();
			if(Cur_URL != ""){
				url += "\\";
			}
				File file = new File(url);
			if(file.isDirectory()){
				twoClick(url);
			}else{
				OpenIt(file);				
			}
			}else{
				File file = new File(Maps.get(list.getSelectedValue()));
				OpenIt(file);
			}			
		}
		
		else if(e.getSource() == JMIs[1]){//ɾ��
			File file = new File(Cur_URL + "/" + list.getSelectedValue());
			int n;
			if(file.isFile()){
				n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ���ļ� " + file.getName() + " ô?", "�ļ�ɾ��",JOptionPane.YES_NO_OPTION);
			}else{
				n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�� " + file.getName() + " ����Ŀ¼�µ��ļ�ô?", "�ļ���ɾ��",JOptionPane.YES_NO_OPTION);
			}
			if(n == 0){
				FileDelete.delete(Cur_URL + list.getSelectedValue() +  "\\");
				Go_There();
			}			
		}
		
		else if(e.getSource() == delete){//��ѡ�µ�ɾ��
			List<String> selected_str = list.getSelectedValuesList();
			File file;
			int num = selected_str.size();
			int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ�� " + selected_str.get(0) + " ��" + num + "��ô?", "�ļ�ɾ��",JOptionPane.YES_NO_OPTION);
			if(n == 0){
				if(isSearching){//�����������������MapsȡURL
					for(int i = 0; i < selected_str.size(); ++i){
						file = new File(Maps.get(selected_str.get(i)));
						FileDelete.delete(file.getAbsolutePath());
					}				
				}else{//�������Cur_URLƴ�ӻ��
					for(int i = 0; i < selected_str.size(); ++i){
						FileDelete.delete(Cur_URL + selected_str.get(i) +  "\\");
					}		
					Go_There();
				}
			}						
		}
		
		else if(e.getSource() == JMIs[2]){//������
			String before = list.getSelectedValue();
			File file = new File(Cur_URL + before + "\\");
			String after = "";
			if(file.isDirectory()){
				after = (String) JOptionPane.showInputDialog(null, "���������ļ�����:\n", "������", JOptionPane.PLAIN_MESSAGE, null, null,
		                list.getSelectedValue());
			}else{
				after = (String) JOptionPane.showInputDialog(null, "���������ļ���:\n", "������", JOptionPane.PLAIN_MESSAGE, null, null,
		                list.getSelectedValue());
			}			
			if(before != after && after != null){
				new File(Cur_URL + before + "\\").renameTo(new File(Cur_URL + after + "\\"));
				Go_There();
			}else{
				Go_There();
			}
		}
		
		else if(e.getSource() == JMIs[3]){//���ļ�/�ļ������Դ���
			String temp_url = Cur_URL + list.getSelectedValue() + "\\";
			File file = new File(temp_url);
			Icon icon = GetFileIcon.getSingleSmallIcon(temp_url);			
			String name = list.getSelectedValue();
			long size;
			double final_size;
		    long File_Num = 0, Directory_Num = 0;
		    int flag = 0;
			String file_size = "";
			
			String Create_Time = FileTime.getCreateTime(temp_url);
			String Modify_Time = FileTime.getModifiedTime(temp_url);
			String Last_Access = FileTime.getLatestAccessTime(temp_url);
			
			if(file.isDirectory()){//Ŀ¼���Գ�ʼ���������
				DirectoryInfo DInfo = new DirectoryInfo();
				size = DInfo._instance.getDirSize(file);
				File_Num = DInfo.File_Num;
				Directory_Num = DInfo.Directory_Num;
				flag = 1;
			}else{//�ļ����Գ�ʼ���������
				size = file.length();				
			}			 
			final_size = 0;				
			for(int i = 0; i < Sizes.length; ++i){
				if(size / Sizes[i] > 0){
					final_size = size * 1.0 / Sizes[i];
					DecimalFormat fnum = new DecimalFormat("##0.00");  
					file_size = fnum.format(final_size) + Size_Names[i];
					break;
				}
			}
			if(flag == 1){
				FileProperties properties = new FileProperties(icon, name, file_size, File_Num, Directory_Num-1, temp_url, Create_Time);
			}else{
				FileProperties properties = new FileProperties(icon, name, file_size, temp_url, Create_Time, Modify_Time, Last_Access);
			}		
		}
		
		else if(e.getSource() == JMIs2[1]){//�������Բ鿴
			String temp_url = list.getSelectedValue() + "\\";
			Icon icon = GetFileIcon.getSingleSmallIcon(temp_url);	
			File file = new File(temp_url);			
			FileSystemView fileSys=FileSystemView.getFileSystemView();
			String name = fileSys.getSystemDisplayName(file);
			double Available = file.getFreeSpace() * 1.0 / Sizes[0];		
			double Used = file.getTotalSpace()* 1.0 / Sizes[0] - Available;
			FileProperties properties = new FileProperties(icon, name, Used, Available);
		}
		
		else if(e.getSource() == GoBtn || e.getSource() == GuideText){//ͨ����ַ�������ļ���ַ��ת
			String url = GuideText.getText();
			if(url.length() > 0){
			File file = new File(url);
			if(file.exists()){
				stack.push(Cur_URL);
				Cur_URL = url;
				Go_There();
			}else{
				JOptionPane.showConfirmDialog(null, "û���ҵ���Ŀ¼", "ȷ�϶Ի���", JOptionPane.YES_OPTION);
			}
			}else{
				Home_List();
			}
		}
		
		else if(e.getSource() == AllFiles){//������������л�����ʾ�����ļ�����ص���Ŀ¼
			isSearching = false;		
			Home_List();
		}
		
		else if(e.getSource() == Videos || e.getSource() == Picture || e.getSource() == Text || e.getSource() == Music){//���ѡ������������
			 isSearching = true;
			 Maps.clear();
			 isSearching = true;
			 defaultListModel.clear();
			 Icon_Counter = 0;
			 AllIcons = new Icon[999999];
			 GetAllResults(Cur_URL);
			 list.setModel(defaultListModel);
			 list.setCellRenderer(new MyCellRenderer(AllIcons));
		}	
		
		else if(e.getSource() == SearchText){//����������󰴻س����������¼�
			 boolean flag_Dir = false, flag_File = false;
			 if(FileCheck.isSelected()){//�����ļ���
				 flag_File = true;
			 }
			 if(DirCheck.isSelected()){//�����ļ�����
				 flag_Dir = true;
			 }
			 if(!(flag_File || flag_Dir)){//��������ѡ�ǲ��е�
				 JOptionPane.showMessageDialog(null, "������ѡ��һ���������", "ȷ�϶Ի���", JOptionPane.YES_OPTION);
			 }else{//��ʼ����
				 isSearching = true;
				 Maps.clear();
				 isSearching = true;
				 defaultListModel.clear();
				 Icon_Counter = 0;
				 AllIcons = new Icon[999999];
				 FileSearch.bfsSearchFile(Cur_URL, SearchText.getText(), flag_Dir, flag_File);
				 list.setModel(defaultListModel);
				 list.setCellRenderer(new MyCellRenderer(AllIcons));
			 }
		}
	}
}
