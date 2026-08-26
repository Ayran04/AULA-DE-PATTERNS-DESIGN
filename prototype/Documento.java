public class Documento implements DocumentoPrototype {

    private String titulo;
    private String autor;
    private String conteudo;
    private String tipo;

    public Documento(String titulo, String autor, String conteudo, String tipo) {
        this.titulo = titulo;
        this.autor = autor;
        this.conteudo = conteudo;
        this.tipo = tipo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public Documento clone() {
        return new Documento(titulo, autor, conteudo, tipo);
    }

    @Override
    public String toString() {
        return """
                Documento
                --------------------------
                Titulo: %s
                Autor: %s
                Tipo: %s
                Conteudo: %s
                """.formatted(
                titulo,
                autor,
                tipo,
                conteudo
        );
    }
}
