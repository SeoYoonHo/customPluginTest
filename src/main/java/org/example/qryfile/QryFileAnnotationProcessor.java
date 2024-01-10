package org.example.qryfile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class QryFileAnnotationProcessor {

    public void processAnnotations() throws ClassNotFoundException {
//        Map<String, Object> controllerBeans = applicationContext.getBeansWithAnnotation(RestController.class);
        String packageName = "com.study.boardExample.controller";
        List<Class<?>> classes = PackageScanner.findClasses(packageName);
//        List<Class<?>> classes = PackageScanner.findClassesInPackage(packageName);

        // controller beans scan
        for (Class<?> controllerClass : classes) {
            Method[] methods = controllerClass.getMethods();

            // method scan
            for (Method method : methods) {
                QryFileMethodAnnotation qryFileMethodAnnotation = getQryFileMethodAnnotation(method);

                if (qryFileMethodAnnotation != null) {
                    String methodValue = qryFileMethodAnnotation.qryFileCustomMethod();
                    System.out.println("qryFileCustomMethod Annotation value : " + methodValue);

                    // method parameterType scan
                    Class[] paramClasses = method.getParameterTypes();
                    for (Class paramClass : paramClasses) {
                        Field[] fields = paramClass.getDeclaredFields();
                        for (Field field : fields) {
                            QryFileInputFieldAnnotation qryFileInputFieldAnnotation = getQryFileInputFieldAnnotation(field);
                            if (qryFileInputFieldAnnotation != null) {
                                String fieldValue = qryFileInputFieldAnnotation.qryFileCustomField();
                                System.out.println("qryFileCustomField Annotation value : " + fieldValue);
                            }
                        }
                    }

                    // method returnType scan
                    Class returnTypeClass = method.getReturnType();
                    Field[] fields = returnTypeClass.getDeclaredFields();
                    for (Field field : fields) {
                        QryFileOutputFieldAnnotation qryFileOutputFieldAnnotation = getQryFileOutputFieldAnnotation(field);
                        if (qryFileOutputFieldAnnotation != null) {
                            String fieldValue = qryFileOutputFieldAnnotation.qryFileCustomField();
                            System.out.println("qryFileCustomField Annotation value : " + fieldValue);
                        }
                    }
                }
            }
        }
    }


    public QryFileMethodAnnotation getQryFileMethodAnnotation(Method method) {
        Annotation[] annotations = method.getAnnotations();
        QryFileMethodAnnotation annotation = Arrays.stream(annotations)
                                                   .filter(annotation1 -> annotation1.annotationType()
                                                                                     .equals(QryFileMethodAnnotation.class))
                                                   .findFirst()
                                                   .map(annotation1 -> (QryFileMethodAnnotation) annotation1)
                                                   .orElse(null);
        return annotation;
    }

    public QryFileInputFieldAnnotation getQryFileInputFieldAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();
        QryFileInputFieldAnnotation annotation = Arrays.stream(annotations)
                                                       .filter(annotation1 -> annotation1.annotationType()
                                                                                         .equals(QryFileInputFieldAnnotation.class))
                                                       .findFirst()
                                                       .map(annotation1 -> (QryFileInputFieldAnnotation) annotation1)
                                                       .orElse(null);

        return annotation;
    }

    public QryFileOutputFieldAnnotation getQryFileOutputFieldAnnotation(Field field) {
        Annotation[] annotations = field.getAnnotations();
        QryFileOutputFieldAnnotation annotation = Arrays.stream(annotations)
                                                        .filter(annotation1 -> annotation1.annotationType()
                                                                                          .equals(QryFileOutputFieldAnnotation.class))
                                                        .findFirst()
                                                        .map(annotation1 -> (QryFileOutputFieldAnnotation) annotation1)
                                                        .orElse(null);

        return annotation;
    }
}
