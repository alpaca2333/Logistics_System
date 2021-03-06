package utils;

import data.po.DataPO;
import data.service.DataService;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;

/**
 *
 * Created by mist on 2015/11/25 0025.
 */
public class FileIOHelper {

    /**
     * 将对象保存在filepath指定的文件中
     *
     * @param object 希望保存的对象
     * @param filePath 希望保存的文件位置
     */
    public static void saveToFile(Object object, String filePath) {
        if (object == null) return;
        FileOutputStream fout = null;
        ObjectOutputStream oout = null;
        try {
            File file = new File(filePath);
            String dirPath = null;
            for (int i = filePath.length() - 1; i >= 0; --i) {
                if (filePath.charAt(i) == '/') {
                    dirPath = filePath.substring(0, i + 1);
                    break;
                }
            }
            File dir = new File(dirPath);
            dir.mkdir();
            fout = new FileOutputStream(file);
            oout = new ObjectOutputStream(fout);
            oout.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oout != null) {
                    oout.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件中读取对象
     *
     * @param filePath 希望读取的文件位置
     * @return 从文件中读取的对象
     */
    public static Object getFromFile(String filePath) {
        FileInputStream fin = null;
        ObjectInputStream oin = null;
        ArrayList<DataPO> result  = null;
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                String dirPath = null;
                for (int i = filePath.length() - 1; i >= 0; --i) {
                    if (filePath.charAt(i) == '/') {
                        dirPath = filePath.substring(0, i + 1);
                        break;
                    }
                }
                File dir = new File(dirPath);
                dir.mkdir();
                file.createNewFile();
            }
            fin = new FileInputStream(file);
            oin = new ObjectInputStream(fin);
            result = (ArrayList<DataPO>) oin.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("数据文件不存在(" + filePath + ") - " + Calendar.getInstance().getTime());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oin != null) {
                    oin.close();
                }
                if (fin != null) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void startFileAutoSaving() {

    }
}


