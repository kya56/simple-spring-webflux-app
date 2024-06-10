package com.example.simplespringwebfluxapp.inventory.testutils;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class RepositoryTestExtension implements BeforeTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        var applicationContext = SpringExtension.getApplicationContext(context);
        var databaseClient = applicationContext.getBean(DatabaseClient.class);
        var databaseHelper = new DatabaseHelper(databaseClient);

        if (getAnnotatedCleanDatabase(context)) {
            databaseHelper.cleanBefore();
        }

        var scripts = getAnnotatedScripts(context);
        if (scripts != null) {
            for (String script : scripts) {
                databaseHelper.migrate(script);
            }
        }

    }

    private boolean getAnnotatedCleanDatabase(ExtensionContext context) {
        return context.getTestClass()
            .map(cla -> cla.getAnnotation(DBMigrate.class))
            .map(DBMigrate::cleanBefore)
            .orElse(false) ||
            context.getElement()
                .flatMap(el -> findAnnotation(el, DBMigrate.class))
                .map(DBMigrate::cleanBefore)
                .orElse(false);
    }

    private String[] getAnnotatedScripts(ExtensionContext context) {
        var scriptsOnClass = context.getTestClass()
            .map(cla -> cla.getAnnotation(DBMigrate.class))
            .map(DBMigrate::scripts)
            .orElse(null);

        var scriptsOnMethod = context.getElement()
            .flatMap(el -> findAnnotation(el, DBMigrate.class))
            .map(DBMigrate::scripts)
            .filter(scripts -> scripts.length > 0)
            .orElse(null);

        if (scriptsOnClass == null) {
            return scriptsOnMethod;
        } else {
            return scriptsOnMethod == null ? scriptsOnClass :
                Stream.of(scriptsOnClass, scriptsOnMethod)
                    .flatMap(Stream::of)
                    .toArray(String[]::new);
        }
    }

}
