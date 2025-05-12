package model.Tools;

import model.enums.general.Direction;

public interface Tool {
    void work(Direction direction);
    String getName();
}
