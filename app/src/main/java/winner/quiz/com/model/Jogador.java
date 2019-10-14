package winner.quiz.com.model;

public class Jogador {
    private String uid;
    private String credito;
    private String pontos;

    public Jogador (){

    }
    public Jogador(String uid, String credito, String pontos) {
        this.uid = uid;
        this.credito = credito;
        this.pontos = pontos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}
