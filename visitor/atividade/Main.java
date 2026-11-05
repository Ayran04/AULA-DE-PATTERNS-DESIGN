import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Product> products = Arrays.asList(
                new Book("Design Patterns", 10000),
                new Electronics("Headphones", 20000));

        System.out.println("=== Report ===");
        visitAll(products, new ProductReportVisitor());

        System.out.println();
        System.out.println("=== Promotional prices ===");
        visitAll(products, new PromotionalPriceVisitor());

        System.out.println();
        System.out.println("=== Report again (original prices preserved) ===");
        visitAll(products, new ProductReportVisitor());

        System.out.println();
        System.out.println("=== Empty catalog ===");
        List<Product> empty = Collections.emptyList();
        visitAll(empty, new ProductReportVisitor());
        visitAll(empty, new PromotionalPriceVisitor());

        System.out.println();
        System.out.println("=== Book with fractional discount ===");
        List<Product> cheapBook = Collections.singletonList(new Book("Pocket Guide", 999));
        visitAll(cheapBook, new ProductReportVisitor());
        visitAll(cheapBook, new PromotionalPriceVisitor());

        System.out.println();
        System.out.println("=== Total price (additional challenge) ===");
        TotalPriceVisitor total = new TotalPriceVisitor();
        visitAll(products, total);
        System.out.println("Catalog total cents: " + total.getTotalInCents());

        TotalPriceVisitor emptyTotal = new TotalPriceVisitor();
        visitAll(empty, emptyTotal);
        System.out.println("Empty catalog total cents: " + emptyTotal.getTotalInCents());
    }

    private static void visitAll(List<Product> products, ProductVisitor visitor) {
        for (Product product : products) {
            product.accept(visitor);
        }
    }
}
