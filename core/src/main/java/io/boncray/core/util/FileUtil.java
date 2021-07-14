package io.boncray.core.util;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * company:Shenzhen Greatonce Co Ltd
 * author:buer
 * create date:2017/7/29
 * remark:
 */
public class FileUtil {

    public static String readFile(String fileName) {

        File file = new File(FileUtil.class.getResource(fileName).getFile());
        return readFile(file);
    }

    public static String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String readFileAsStream(String filePath) {

        BufferedReader in = new BufferedReader(new InputStreamReader(FileUtil.class.getResourceAsStream(filePath)));
        StringBuilder buffer = new StringBuilder();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public static void writeFile(String fileName, String content) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(content.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<File> findFiles(String path) {
        String dir = "/";
        String pattern = path;
        if (path.contains("/")) {
            dir = path.substring(0, path.lastIndexOf("/"));
            pattern = path.substring(dir.length() + 1);
        }
        pattern = pattern.replace("*", ".*");
        pattern = pattern.replace("?", ".?");
        List<File> files = findClassPathFiles(dir);
        List<File> result = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().matches(pattern)) {
                result.add(file);
            }
        }
        return result;
    }

    private static List<File> findClassPathFiles(String dir) {
        List<File> list = new ArrayList<>();
        try {
            if (dir == "/") {
                dir = "";
            }
            Enumeration<URL> urlEnumeration = FileUtil.class.getClassLoader().getResources(dir);
            while (urlEnumeration.hasMoreElements()) {
                File file = new File(urlEnumeration.nextElement().getFile());
                File[] files = file.listFiles();
                for (File f : files) {
                    list.add(f);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
