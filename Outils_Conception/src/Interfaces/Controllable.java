package Interfaces;

import java.util.Collection;

public interface Controllable<Type> {

    void setCollection(Collection <Type> T);

    void RefreshCollection(Collection <Type> T) throws IllegalAccessException;

    void setVisuel();
}
