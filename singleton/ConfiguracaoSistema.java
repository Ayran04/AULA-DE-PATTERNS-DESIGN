public class ConfiguracaoSistema {

    private static ConfiguracaoSistema instancia;

    private String idioma;
    private String tema;

    private ConfiguracaoSistema() {
        idioma = "pt-BR";
        tema = "claro";
    }

    public static ConfiguracaoSistema getInstancia() {

        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }

        return instancia;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
