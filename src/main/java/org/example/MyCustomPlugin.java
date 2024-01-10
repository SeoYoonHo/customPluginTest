package org.example;

import org.apache.tools.ant.taskdefs.Java;
import org.example.qryfile.QryFileAnnotationProcessor;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;

public class MyCustomPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        // ReflectTask 태스크 추가
        project.getTasks().create("customTask", ReflectTask.class);

        // ReflectTask가 compileJava 이후에 실행되도록 설정
        project.getTasks().named("customTask").configure(task -> {
            System.out.println("hiehfioewhro;iquewr");
//            Class<?> clazz = null;
//            try {
//                clazz = Class.forName("com.study.boardExample.dto.MemberDTO");
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(clazz);
            task.dependsOn("compileJava");
        });

//        Task customTask = project.task("customTask").doLast(task -> {
//            System.out.println("custom plugin start!!!");
//            try {
//                Class<?> clazz = Class.forName("com.study.boardExample.dto.MemberDTO");
//                System.out.println(clazz);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//
////            SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
////            SourceSet main = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
////            FileCollection runtimeClasspath = main.getRuntimeClasspath();
////
////            runtimeClasspath.forEach(file -> {
////                // 여기에서 파일 시스템을 탐색하여 클래스 파일 찾기
////                if (file.isDirectory()) {
////                    findClassesInDirectory(file, file);
////                }
////            });
////
////            QryFileAnnotationProcessor qryFileAnnotationProcessor = new QryFileAnnotationProcessor();
////            try {
////                qryFileAnnotationProcessor.processAnnotations();
////            } catch (ClassNotFoundException e) {
////                throw new RuntimeException(e);
////            }
//        });
//
//        customTask.dependsOn("compileJava");
////        project.getTasks().named("compileJava").configure(task -> task.finalizedBy(customTask));
    }

    private void findClassesInDirectory(File root, File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    findClassesInDirectory(root, file);
                } else if (file.getName().endsWith(".class")) {
                    // 클래스 파일 처리
                    String className = getClassName(root, file);
                    try {
                        System.out.println(className);
                        Class<?> findClass = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }

    private String getClassName(File root, File file) {
        String relative = root.toURI().relativize(file.toURI()).getPath();
        return relative.replace('/', '.').replace('\\', '.').replace(".class", "");
    }
}
