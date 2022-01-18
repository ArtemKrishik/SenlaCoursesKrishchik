package com.github.krishchik.whowithme;

import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DatabaseHelper {
}
