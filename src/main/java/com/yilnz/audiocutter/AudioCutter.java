package com.yilnz.audiocutter;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class AudioCutter {

	public static void main(String[] args) {
		//copyAudio("/Users/zyl/Downloads/Output/temp/总句数 (1)(1)_1.wav", "/Users/zyl/Downloads/Output/out/1.wav", 1);
		//cutAudio("/Users/zyl/Downloads/Output/temp/", "/Users/zyl/Downloads/Output/out/", 1);
		if(args.length != 3){
			System.out.println("用法 java -jar *.jar C:/input/ D:/output 3");
		}else {
			cutAudio(args[0], args[1], Float.parseFloat(args[2]));
		}
	}

	public static void cutAudio(String fileDir, String destDir, float cutSecond){
		destDir = destDir.replaceAll("/$", "");
		final File[] files = new File(fileDir).listFiles();
		for (int i = 0; i < files.length; i++) {
			copyAudio(files[i].getPath(), destDir + "/" + files[i].getName(), cutSecond);
		}
	}

	public static void copyAudio(String sourceFileName, String destinationFileName, float cutSecond) {
		System.out.println("处理 "+ sourceFileName);
		AudioInputStream inputStream = null;
		AudioInputStream shortenedStream = null;
		try {
			File file = new File(sourceFileName);
			AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
			AudioFormat format = fileFormat.getFormat();
			inputStream = AudioSystem.getAudioInputStream(file);
			final float frameRate = format.getFrameRate();
			int bytesPerSecond = format.getFrameSize() * (int) frameRate;
			float startSecond = 0;
			if(cutSecond < 0){
				startSecond = -cutSecond;
				cutSecond = 0;
			}
			inputStream.skip((int)startSecond * bytesPerSecond);
			//long framesOfAudioToCopy = secondsToCopy * (int) format.getFrameRate();
			shortenedStream = new AudioInputStream(inputStream, format, (int)(fileFormat.getFrameLength() - cutSecond * frameRate));
			File destinationFile = new File(destinationFileName);
			AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (inputStream != null) try {
				inputStream.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			if (shortenedStream != null) try {
				shortenedStream.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
