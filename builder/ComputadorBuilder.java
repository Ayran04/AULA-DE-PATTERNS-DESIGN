public class ComputadorBuilder {

    private String processador;
    private int memoriaRam;
    private int armazenamento;
    private String placaVideo;
    private String sistemaOperacional;
    private boolean monitor;
    private boolean teclado;
    private boolean mouse;

    public ComputadorBuilder processador(String processador) {
        this.processador = processador;
        return this;
    }

    public ComputadorBuilder memoriaRam(int memoriaRam) {
        this.memoriaRam = memoriaRam;
        return this;
    }

    public ComputadorBuilder armazenamento(int armazenamento) {
        this.armazenamento = armazenamento;
        return this;
    }

    public ComputadorBuilder placaVideo(String placaVideo) {
        this.placaVideo = placaVideo;
        return this;
    }

    public ComputadorBuilder sistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
        return this;
    }

    public ComputadorBuilder monitor(boolean monitor) {
        this.monitor = monitor;
        return this;
    }

    public ComputadorBuilder teclado(boolean teclado) {
        this.teclado = teclado;
        return this;
    }

    public ComputadorBuilder mouse(boolean mouse) {
        this.mouse = mouse;
        return this;
    }

    public Computador build() {

        Computador computador = new Computador();

        computador.setProcessador(processador);
        computador.setMemoriaRam(memoriaRam);
        computador.setArmazenamento(armazenamento);
        computador.setPlacaVideo(placaVideo);
        computador.setSistemaOperacional(sistemaOperacional);
        computador.setMonitor(monitor);
        computador.setTeclado(teclado);
        computador.setMouse(mouse);

        return computador;
    }
}