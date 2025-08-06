package io.github.some_example_name.model.materials.Tools;


import io.github.some_example_name.model.Result;
import io.github.some_example_name.model.enums.general.Direction;

public interface Tool {
    Result work(Direction direction);
    String getName();
}
