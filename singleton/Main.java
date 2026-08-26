public class Main {

    public static void main(String[] args) {

        ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstancia();
        configuracao.setIdioma("pt-BR");
        configuracao.setTema("escuro");

        ConfiguracaoSistema outraReferencia = ConfiguracaoSistema.getInstancia();

        System.out.println("Idioma: " + outraReferencia.getIdioma());
        System.out.println("Tema: " + outraReferencia.getTema());
        System.out.println("Mesma instancia: " + (configuracao == outraReferencia));
    }
}
