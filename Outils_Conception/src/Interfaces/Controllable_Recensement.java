package Interfaces;


public interface Controllable_Recensement<T extends Visualisable> extends Controllable<T> {
    Recensement<T>  getRecensement();
}
