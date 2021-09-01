package com.lucas.ferreira.maburn.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.lucas.ferreira.maburn.model.browser.FileExtractValues;
import com.lucas.ferreira.maburn.util.MathUtil;
import com.lucas.ferreira.maburn.util.datas.BytesUtil;

public class ZipModel {

	private FileExtractValues fileExtractValues;

	public ZipModel(FileExtractValues fileExtractValues) {
		this.fileExtractValues = fileExtractValues;
		extractProgress(fileExtractValues);

	}

	private void extractProgress(FileExtractValues fileExtractValues) {
		fileExtractValues.getCurrentFile().addListener((obs, oldvalue, newvalue) ->{
			double progress = (double) (newvalue.doubleValue() / fileExtractValues.getTotalFiles().get());
			fileExtractValues.getExtractingProgress().set(progress);
		});
	}

	public void unzipFile() throws IOException {
		String path = fileExtractValues.getPath();

		List<ZipEntry> entries = listAllFilesInZip(path);
		fileExtractValues.getTotalFiles().set(entries.size());
		String fileZip = path;
		File destDir = new File(path.replace(".zip", ""));
		byte[] buffer = new byte[1024];
		
		ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
		
		try {
			for (int i = 0; i < entries.size(); i++) {
				fileExtractValues.getCurrentFile().set(i + 1);
				ZipEntry zipEntry = entries.get(i);
				zis.getNextEntry();
				File newFile = newFile(destDir, zipEntry);
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile);
					}
				} else {
					File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent);
					}

					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					
					}
					fos.close();
				}
			}
		} catch (IOException e) {
			fileExtractValues.getFailed().set(true);
			throw e;
		} finally {
			zis.closeEntry();
			zis.close();
		}
		
		new File(fileZip).delete();
		fileExtractValues.getFinish().set(true);
		
	}

	private List<ZipEntry> listAllFilesInZip(String path) throws IOException {
		List<ZipEntry> entries = new ArrayList<>();

		String fileZip = path;

		ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
		ZipEntry zipEntry = zis.getNextEntry();

		while(zipEntry != null) {
			entries.add(zipEntry);
			
			FileModel fileModel = new FileModel();
			fileModel.getName().set(zipEntry.getName());
			long size = zipEntry.getSize();
			double mbSize = BytesUtil.convertBytesToMegasBytes(size);
			fileModel.getSize().set(mbSize);
			fileExtractValues.getFileModels().add(fileModel);
			zipEntry = zis.getNextEntry();

		}

		zis.close();
		return entries;

	}

	public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}


		return destFile;
	}

}
