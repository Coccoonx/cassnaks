package co.wouri.libreexchange.core.managers;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import co.wouri.libreexchange.core.models.Profile;


public class FileManager {

    private static final String USER_FILE = "CoazeAccount.store";
    private static final File root = Environment.getExternalStorageDirectory();
    private static final File outDir = new File(root.getAbsolutePath() + File.separator + "Coaze");

    private static final FileManager fileManager = new FileManager();

    private FileManager() {
    }

    public static FileManager getInstance() {
        return fileManager;
    }

    public boolean save(Profile profile) {
        return writeData(profile);
    }

    public Profile restore() {
        return restoreData();
    }


    private boolean writeData(Profile profile) {
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        Log.d("coaze", "Before writing to outDir = (" + outDir + ")");
        try {
            File outputFile = new File(outDir, USER_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
            oos.writeObject(profile);
            oos.flush();
            oos.close();
            Log.d("coaze", "After writing to outDir = (" + outDir + ")");
            return true;
        } catch (Exception e) {
            Log.d("coaze", "Problem while writing : " + e.getMessage());
            return false;
        }

    }

    private Profile restoreData() {
        try {
            File intputFile = new File(outDir, USER_FILE);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(intputFile));
            Profile profile = (Profile) ois.readObject();
            ois.close();
            return profile;
        } catch (Exception e) {
            Log.d("coaze", "Problem reading file : " + e.getMessage());
            return null;
        }
    }


    public boolean deleteData() {
        File outputFile = new File(outDir, USER_FILE);
        if (outputFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

}
