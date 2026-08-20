public class Main {

    public static void main(String[] args) {

        Computador gamer = new ComputadorBuilder()
                .processador("Intel i9")
                .memoriaRam(32)
                .armazenamento(2000)
                .placaVideo("RTX 5090")
                .sistemaOperacional("Windows 11")
                .monitor(true)
                .teclado(true)
                .mouse(true)
                .build();

        System.out.println(gamer);
    }

}