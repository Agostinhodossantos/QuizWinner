package winner.quiz.com.model;

public class CreditoModel {
    private String recarga , rede , uid;

    public CreditoModel() {
    }

    public CreditoModel(String recarga, String rede, String uid) {
        this.recarga = recarga;
        this.rede = rede;
        this.uid = uid;
    }

    public String getRecarga() {
        return recarga;
    }

    public void setRecarga(String recarga) {
        this.recarga = recarga;
    }

    public String getRede() {
        return rede;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
