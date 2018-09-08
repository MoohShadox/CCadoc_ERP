package POJO;

public class Mail {
    private String adresseMail;
    private String typeC, nomMail;

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getTypeC() {
        return typeC;
    }

    public void setTypeC(String typeC) {
        this.typeC = typeC;
    }

    public String getNomMail() {
        return nomMail;
    }

    public void setNomMail(String nomMail) {
        this.nomMail = nomMail;
    }

    @Override
    public boolean equals(Object obj) {
        Mail m=(Mail)obj;
        return this.adresseMail.equals(m.adresseMail);
    }
}
