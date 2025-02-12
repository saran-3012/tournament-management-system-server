package com.saran.tms.persistance.fs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.sound.sampled.Line;


public class FileStrategies {
	
	final public static FileStrategy overwriteStrategy = new FileStrategy() {
		
		@Override
		public Object write(Object... args) {
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			String filePath = folderPath + fileName;
			
			try (FileOutputStream fos = new FileOutputStream(filePath)){
				fos.write(((String) args[2]).getBytes());
			} catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			return null;
		}
		
		@Override
		public Object update(Object... args) {
			final int argsLen = args.length;
			
			if(argsLen < 3) return null;
			
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			final String filePath = folderPath + fileName;
			
			String fileContent = null;
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line).append('\n');
				}
				fileContent = sb.toString();
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			String newContent = (String) args[argsLen - 1];
			FileOutputStream fos = null;
			if(argsLen == 3) {
				fileContent = newContent;
				try{
					fos = new FileOutputStream(filePath, true);
				} catch(Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			else {
				for(int i=2; i<argsLen - 1; i++) {
					fileContent = fileContent.replace(((String) args[i]), newContent);
				}
				try{
					fos = new FileOutputStream(filePath);
				} catch(Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			
			try {
				fos.write(fileContent.getBytes());
				fos.close();
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			return null;
		}
		
		@Override
		public Object read(Object... args) {
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			String fileContent = null;
			final String filePath = folderPath + fileName;
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line).append('\n');
				}
				fileContent = sb.toString();
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			return fileContent;
		}
		
		@Override
		public Object delete(Object... args) {
			final int argsLen = args.length;
			
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			final String filePath = folderPath + fileName;
			
			if(argsLen < 3) {
				File file = new File(filePath);
				return file.delete();
			}
			
			String fileContent = null;
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line).append('\n');
				}
				fileContent = sb.toString();
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			for(int i=2; fileContent != null && !fileContent.isEmpty() && i<argsLen; i++) {
				fileContent = fileContent.replace((String) args[i] , "");
			}
			
			try (FileOutputStream fos = new FileOutputStream(filePath)){
				fos.write(fileContent.getBytes());
			} catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			return null;
		}
	};
	
	final public static FileStrategy appendStrategy = new FileStrategy() {
		
		@Override
		public Object write(Object... args) {
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			String filePath = folderPath + fileName;
			
			try (FileOutputStream fos = new FileOutputStream(filePath, true)){
				fos.write(((String) args[2]).getBytes());
			} catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			return null;
		}

		@Override
		public Object update(Object... args) {
			final int argsLen = args.length;
			
			if(argsLen < 3) return null;
			
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			final String filePath = folderPath + fileName;
			
			String fileContent = null;
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line).append('\n');
				}
				fileContent = sb.toString();
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			String newContent = (String) args[argsLen - 1];
			FileOutputStream fos = null;
			if(argsLen == 3) {
				fileContent = newContent;
				try{
					fos = new FileOutputStream(filePath, true);
				} catch(Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			else {
				for(int i=2; i<argsLen - 1; i++) {
					fileContent = fileContent.replace(((String) args[i]), newContent);
				}
				try{
					fos = new FileOutputStream(filePath);
				} catch(Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
			
			try {
				fos.write(fileContent.getBytes());
				fos.close();
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			return null;
		}
		
		@Override
		public Object read(Object... args) {
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			String fileContent = null;
			final String filePath = folderPath + fileName;
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line).append('\n');
				}
				fileContent = sb.toString();
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			return fileContent;
		}
		
		@Override
		public Object delete(Object... args) {
			final int argsLen = args.length;
			
			String folderPath = (String) args[0];
			String fileName = (String) args[1];
			
			final String filePath = folderPath + fileName;
			
			if(argsLen < 3) {
				File file = new File(filePath);
				return file.delete();
			}
			
			String fileContent = null;
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = br.readLine()) != null) {
					sb.append(line).append('\n');
				}
				fileContent = sb.toString();
			}
			catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			for(int i=2; fileContent != null && !fileContent.isEmpty() && i<argsLen; i++) {
				fileContent = fileContent.replace((String) args[i] , "");
			}
			
			try (FileOutputStream fos = new FileOutputStream(filePath)){
				fos.write(fileContent.getBytes());
			} catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			return null;
		}
	};
}
