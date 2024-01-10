package org.example;

import org.example.qryfile.QryFileInputFieldAnnotation;
import org.example.qryfile.QryFileMethodAnnotation;
import org.example.qryfile.QryFileOutputFieldAnnotation;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("org.example.qryfile.QryFileMethodAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class MyAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        // 초기화 코드
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element element : annotatedElements) {
                if (element instanceof ExecutableElement) {
                    ExecutableElement method = (ExecutableElement) element;
                    List<? extends VariableElement> parameters = method.getParameters();
                    for (VariableElement parameter : parameters) {
                        TypeMirror paramType = parameter.asType();
                        if (paramType instanceof DeclaredType) {
                            Element paramTypeElement = ((DeclaredType) paramType).asElement();
                            if (paramTypeElement instanceof TypeElement) {
                                List<? extends Element> paramFields = elementUtils.getAllMembers((TypeElement) paramTypeElement);
                                for (Element field : paramFields) {
                                    if (field.getKind() == ElementKind.FIELD) {
                                        // 필드를 처리합니다.
                                        // 예: 필드 이름 출력
                                        System.out.println("Field: " + field.getSimpleName());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
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