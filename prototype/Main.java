public class Main {

    public static void main(String[] args) {

        Documento modeloContrato = new Documento(
                "Contrato Padrao",
                "Departamento Juridico",
                "Clausulas gerais do contrato.",
                "Contrato"
        );

        Documento contratoCliente = modeloContrato.clone();
        contratoCliente.setTitulo("Contrato Cliente A");
        contratoCliente.setAutor("Ana Silva");
        contratoCliente.setConteudo("Clausulas especificas para o Cliente A.");

        System.out.println(modeloContrato);
        System.out.println(contratoCliente);
    }
}
