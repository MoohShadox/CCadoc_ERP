package POJO;


public class TelFax{
    private String numero;
    private boolean telfax;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isTelfax() {
        return telfax;
    }

    public void setTelfax(boolean telfax) {
        this.telfax = telfax;
    }

    @Override
    public boolean equals(Object obj) {
        TelFax t= (TelFax)obj;
        return this.numero==t.numero;
    }
}
