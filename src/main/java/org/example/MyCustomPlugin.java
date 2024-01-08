package org.example;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyCustomPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.task("customTask").doLast(task -> {
            System.out.println("custom plugin start!!!");
        });
    }
}
