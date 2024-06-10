package com.example.simplespringwebfluxapp.inventory.testutils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
public class DatabaseHelper {
    private DatabaseClient databaseClient;

    private static final String CLEANUP_SQL_PATH = "src/test/resources/db/cleanup";

    private Mono<String> getSql(String pathString) throws URISyntaxException {
        var path = Paths.get(pathString);
        return Flux
            .using(() -> Files.lines(path),
                Flux::fromStream,
                Stream::close)
            .reduce((line1, line2) -> line1 + "\n" + line2);
    }

    public void migrate(String path) {
        try {
            getSql(path)
                .flatMap(this::executeSql)
                .subscribe(count -> {
                    log.info("data migration %s is completed. %s".formatted(path, count));
                });
        } catch (URISyntaxException ex) {
            log.error(ex.getMessage());
        }
    }

    public void cleanBefore() {
        Stream.of(new File(CLEANUP_SQL_PATH).listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getName)
            .peek(file -> log.info("[cleanBefore] migrating script " + file))
            .forEach(s -> migrate("%s/%s".formatted(CLEANUP_SQL_PATH, s)));
    }

    private Mono<Long> executeSql(String sql) {
        return databaseClient.sql(sql).fetch().rowsUpdated();
    }

}

