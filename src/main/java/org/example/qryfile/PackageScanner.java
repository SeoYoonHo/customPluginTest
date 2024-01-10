package org.example.qryfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageScanner {
    public static List<Class<?>> findClasses(String packageName) throws  ClassNotFoundException {
        String classpath = System.getProperty("java.class.path");
        String path = classpath + File.separator + packageName.replace(".", File.separator);
        List<Class<?>> classes = new ArrayList<>();

        System.out.println(classpath);
        System.out.println(path);

        File packageDir = new File(path);
        if (packageDir.exists() && packageDir.isDirectory()) {
            File[] files = packageDir.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        }

        return classes;
    }

    public static List<Class<?>> findClassesInPackage(String packagePath) {
        List<Class<?>> classes = new ArrayList<>();
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);

        System.out.println(classpath);

        for (String classpathEntry : classpathEntries) {
            File baseDir = new File(classpathEntry, packagePath);
            if (baseDir.exists() && baseDir.isDirectory()) {
                File[] files = baseDir.listFiles();
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".class")) {
                        String className = packagePath + "." + file.getName().substring(0, file.getName().length() - 6);
                        try {
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return classes;
    }
}
