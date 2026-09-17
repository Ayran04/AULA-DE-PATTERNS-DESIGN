public class Main {

    public static void main(String[] args) {

        DocumentFile standaloneFile = new DocumentFile("resume.pdf", 300);
        System.out.println("Standalone file: " + standaloneFile.getName() + " - " + standaloneFile.getSize() + " KB");

        Folder emptyFolder = new Folder("empty-folder");
        System.out.println("Empty folder size: " + emptyFolder.getSize() + " KB");

        DocumentFile report = new DocumentFile("report.pdf", 500);
        DocumentFile photo = new DocumentFile("photo.png", 1_500);
        DocumentFile invoice = new DocumentFile("invoice.pdf", 200);
        DocumentFile backup = new DocumentFile("backup.zip", 4_000);

        Folder invoices = new Folder("invoices");
        invoices.add(invoice);

        Folder documents = new Folder("documents");
        documents.add(report);
        documents.add(invoices);

        Folder root = new Folder("root");
        root.add(photo);
        root.add(documents);
        root.add(backup);

        System.out.println();
        System.out.println("Root total size: " + root.getSize() + " KB");
        System.out.println();

        root.display("");
    }
}
