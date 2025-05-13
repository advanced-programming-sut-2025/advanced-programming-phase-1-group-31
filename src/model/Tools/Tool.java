package model.Tools;

import model.Result;
import model.enums.general.Direction;

public interface Tool {
    Result work(Direction direction);
    String getName();
}
