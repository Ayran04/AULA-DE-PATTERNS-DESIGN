public class Computador {

    private String processador;
    private int memoriaRam;
    private int armazenamento;
    private String placaVideo;
    private String sistemaOperacional;
    private boolean monitor;
    private boolean teclado;
    private boolean mouse;

    public Computador() {
    }

    public void setProcessador(String processador) {
        this.processador = processador;
    }

    public void setMemoriaRam(int memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public void setArmazenamento(int armazenamento) {
        this.armazenamento = armazenamento;
    }

    public void setPlacaVideo(String placaVideo) {
        this.placaVideo = placaVideo;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    public void setTeclado(boolean teclado) {
        this.teclado = teclado;
    }

    public void setMouse(boolean mouse) {
        this.mouse = mouse;
    }

    @Override
    public String toString() {
        return """
                Computador
                --------------------------
                Processador: %s
                Memória: %d GB
                SSD: %d GB
                Placa de vídeo: %s
                Sistema: %s
                Monitor: %s
                Teclado: %s
                Mouse: %s
                """.formatted(
                processador,
                memoriaRam,
                armazenamento,
                placaVideo,
                sistemaOperacional,
                monitor,
                teclado,
                mouse
        );
    }
}