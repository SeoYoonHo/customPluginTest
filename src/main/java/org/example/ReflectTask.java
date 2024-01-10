package org.example;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.lang.reflect.Method;

public class ReflectTask extends DefaultTask {
    @TaskAction
    public void reflect() {
        System.out.println("hhh????");
        try {
            // build/classes/java/main 디렉토리를 클래스패스에 추가
            File classesDir = new File(getProject().getBuildDir(), "classes/java/main");
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { classesDir.toURI().toURL() });

            // 커스텀 클래스 로더를 사용하여 클래스 로드
            Class<?> clazz = Class.forName("com.study.boardExample.dto.MemberDTO", true, classLoader);
            System.out.println(clazz);

            // 리플렉션을 사용하여 메소드 호출 등을 수행
            for (Method method : clazz.getDeclaredMethods()) {
                System.out.println("Method: " + method.getName());
            }

            // 클래스 로더 닫기
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Reflection failed", e);
        }
    }
}
