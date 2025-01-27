package ok;

import java.util.List;

public interface Sensor {
    boolean detect(List<Item> objects);
}