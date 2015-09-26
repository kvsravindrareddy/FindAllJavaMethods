package com.ravindra.allmethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import com.ravindra.apachepoi.ApachePOIForMethods;
import com.ravindra.common.PropertiesLoader;

/**
 * 
 * @author KVSRR
 * NN Palli
 * Donakonda
 * Prakastam district
 * Andhrapradesh, India
 *
 */
public class FindAllMethods {

	public static String[] projectDirectoties() throws FileNotFoundException, IOException {
		Properties prop = PropertiesLoader.loadProperties();
		String projectFolders = prop.getProperty("MethodPojectDirectories");
		String[] directories = projectFolders.split("&&");
		System.out.println("Number of directories configured in properties file : "+directories.length);
		return directories;
	}

	public static void findFilesInDirectory(File file, String pathName) throws Exception
	{
		File[] list = file.listFiles();
		Class cls = null;
		if(list!=null)
			for (File fil : list)
			{

				if (fil.isDirectory())
				{
					findFilesInDirectory(fil, pathName);
				}

				if(fil.getAbsolutePath().endsWith(".java")) {
					String absolutePath = fil.getAbsolutePath();
					String basePath = fil.getAbsolutePath().substring(0,fil.getAbsolutePath().lastIndexOf("com"));

					String classNames = fil.getAbsolutePath().substring(fil.getAbsolutePath().indexOf("com"), fil.getAbsolutePath().lastIndexOf(".java")).replaceAll("\\\\", ".");
					String javaFileNames = absolutePath.substring(absolutePath.lastIndexOf("\\")+1, absolutePath.lastIndexOf(".java"));


//					ApachePOIForMethods.writeJavaFileIntoExcel(javaFileNames, pathName);
					File f = new File(basePath);
					URL url = f.toURI().toURL(); 
					URL[] urls = new URL[]{url}; 

					URLClassLoader loader = new URLClassLoader(urls);
//					cls = loader.loadClass(classNames);
					Class cls1 = Class.forName("com.ravindra.allfolders.PMDAutomation");

					//get all methods using reflection API
					String[] strArrMethods = getAllMethods(cls1);
					for(int l=1;l<strArrMethods.length;l++) {
						String sArrMethod = strArrMethods[l].toString().replaceAll(classNames+".","");
						System.out.println(sArrMethod);
						ApachePOIForMethods.writeMethodNamesToExistingWoBook(sArrMethod, javaFileNames, pathName);
					}
					
				}

			}
	}


	public static String[] getAllMethods(Class cls) {
		Method[] methodArr = cls.getDeclaredMethods();
		String meth = "";
		for(int i=0;i<methodArr.length;i++) {
			String methodName = methodArr[i].toString();
			meth = meth+"&&"+methodName;
		}
		String[] strArr =meth.split("&&");
		return strArr;
	}

	public static void main(String[] args) throws Exception {
		ApachePOIForMethods.createExcelSheet();
		ApachePOIForMethods.createWorkBookSheetTitle();
		String[] directories = projectDirectoties();
		for(int j=0;j<directories.length;j++) {
			File file = new File(directories[j].replaceAll("^ | $|\\n ", ""));
			File[] fileArry = file.listFiles();
			for(int m=0;m<fileArry.length;m++) {
				String pathName = fileArry[m].toString();
//				ApachePOIForMethods.writeDirectoryIntoExcel(pathName);
				File fl = new File(pathName);
				FindAllMethods.findFilesInDirectory(fl, pathName);
			}
		}

	}
}
