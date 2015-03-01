package com.done.storage;

import java.io.File;
import java.io.IOException;

public class FileHandler {
	
	public static File openFile(String fileName) {
        File file = new File(fileName);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.exit(0);
            }
        }
        return file;
    }

}
