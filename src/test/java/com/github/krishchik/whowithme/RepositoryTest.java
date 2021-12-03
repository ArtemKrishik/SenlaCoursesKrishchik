package com.github.krishchik.whowithme;

import com.github.krishchik.whowithme.util.DatabaseConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfiguration.class)
public class RepositoryTest extends DatabaseHelper {
}
