package com.github.krishchik.whowithme;

import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DatabaseHelper {
}
