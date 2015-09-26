package com.ravindra.allmethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class FindAllMethodsMain {

	public static String[] projectDirectoties() throws FileNotFoundException, IOException {
		Properties prop = PropertiesLoader.loadProperties();
		String projectFolders = prop.getProperty("MethodPojectDirectories");
		String[] directories = projectFolders.split("&&");
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
//					System.out.println(absolutePath);
//					String basePath = fil.getAbsolutePath().substring(0,fil.getAbsolutePath().lastIndexOf("com"));

					String classNames = fil.getAbsolutePath().substring(fil.getAbsolutePath().indexOf("com"), fil.getAbsolutePath().lastIndexOf(".java")).replaceAll("\\\\", ".");
					String javaFileNames = absolutePath.substring(absolutePath.lastIndexOf("\\")+1, absolutePath.lastIndexOf(".java"));
					//					if(absolutePath.contains("\\test\\")) {

//					System.out.println(classNames);
						//					ApachePOIForMethods.writeJavaFileIntoExcel(javaFileNames, pathName);

						//get all setter and getter methods using reflection API
						
						String[] strArrMethods = getAllMethods(absolutePath);
						for(int l=1;l<strArrMethods.length;l++) {
							String sArrMethod = strArrMethods[l].toString().replaceAll(classNames+".","").replace("{", "");
							//							if(sArrMethod.contains("set")) {
							//								
							//							}
							if(!sArrMethod.contains(javaFileNames)) {
								System.out.println(sArrMethod);
								ApachePOIForMethods.writeMethodNamesToExistingWoBook(sArrMethod, javaFileNames, pathName);
							} else {

							}

						}
						//						}
				}

			}
	}


	public static String[] getAllMethods(String javaFileAsLine) throws IOException {
		FileReader fr = new FileReader(new File(javaFileAsLine));
		BufferedReader br = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();
		String readLineText = br.readLine();

		while(readLineText!=null) {
			sb.append(readLineText);
			sb.append("\n");
			readLineText = br.readLine();
		}
		br.close();
		String stringLine = sb.toString();

		String[] methArr = null;
		String meth = "";
		if(stringLine.contains("public interface ") || stringLine.contains("public abstract ") || stringLine.contains(" implements Serializable")) {

		} else {
			Pattern pattern = Pattern.compile("(?s)public(.*?)[)]");
			Matcher matcher = pattern.matcher(stringLine);

			while(matcher.find()) {
				String matcherData = matcher.group();
				String result = matcherData.trim().replaceAll("\n", "");
				meth = meth+"&&"+result.replaceAll("\\s+", " ").replace("( ", "(").trim();
				
			}

		}
		methArr =meth.split("&&");
		return methArr;
	}

	public static void main(String[] args) throws Exception {
		ApachePOIForMethods.createExcelSheet();
		ApachePOIForMethods.createWorkBookSheetTitle();
		String[] directories = projectDirectoties();
//		System.out.println(directories.length);
		for(int j=0;j<directories.length;j++) {
			File file = new File(directories[j].replaceAll("^ | $|\\n ", ""));
			File[] fileArry = file.listFiles();
			for(int m=0;m<fileArry.length;m++) {
				String pathName = fileArry[m].toString();
				//				ApachePOIForMethods.writeDirectoryIntoExcel(pathName);
				File fl = new File(pathName);
				FindAllMethodsMain.findFilesInDirectory(fl, pathName);
			}
		}

	}
}
