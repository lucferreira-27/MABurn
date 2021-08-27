package com.lucas.ferreira.maburn.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.Test;

import com.lucas.ferreira.maburn.util.FileArchive;

public class FileArchiveTest {

	@Test
	public void testIsArchiveShouldBeTrue() throws IOException {

		Path path = Files.createTempFile("ARCHIVE", "_TEST");
		File file = path.toFile();
		path = compress(file.getAbsolutePath());
		file = path.toFile();
		file.deleteOnExit();
		FileArchive fileArchive = new FileArchive();
		boolean result = fileArchive.isArchive(file);
		assertTrue(result);
	}

	@Test
	public void testIsArchiveShouldBeFalse() throws IOException {
		Path path = Files.createTempFile("ARCHIVE", "_TEST");
		File file = path.toFile();
		file.deleteOnExit();
		FileArchive fileArchive = new FileArchive();
		boolean result = fileArchive.isArchive(file);
		assertFalse(result);

	}

	public static Path compress(String dirPath) {
		final Path sourceDir = Paths.get(dirPath);
		String zipFileName = dirPath.concat(".zip");
		try {
			final ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
			Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
					try {
						Path targetFile = sourceDir.relativize(file);
						outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
						byte[] bytes = Files.readAllBytes(file);
						outputStream.write(bytes, 0, bytes.length);
						outputStream.closeEntry();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}
			});
			outputStream.close();
			return Paths.get(zipFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
