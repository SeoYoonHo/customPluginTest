package org.example;

import lombok.extern.slf4j.Slf4j;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@Slf4j
public class ReflectTask extends DefaultTask {
    @TaskAction
    public void reflect() {
        log.info("hhhhherererer");
        System.out.println("hhh????");
        try {
            // build/classes/java/main 디렉토리를 클래스패스에 추가
            File classesDir = new File(getProject().getBuildDir(), "classes/java/main");
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classesDir.toURI().toURL(),
                    new URL("file:/Users/seoyoonho/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-api/2.0.7/41eb7184ea9d556f23e18b5cb99cad1f8581fc00/slf4j-api-2.0.7.jar")});

            // 커스텀 클래스 로더를 사용하여 클래스 로드
            Class<?> clazz = Class.forName("com.study.boardExample.controller.AuthController", true, classLoader);
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
