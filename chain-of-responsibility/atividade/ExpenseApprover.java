import java.util.Locale;

public abstract class ExpenseApprover {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private ExpenseApprover next;

    public void setNext(ExpenseApprover next) {
        this.next = next;
    }

    public void approve(ExpenseRequest request) {
        if (request.getAmountInCents() <= getLimitInCents()) {
            System.out.println(getName() + " approved: " + describe(request));
        } else if (next != null) {
            next.approve(request);
        } else {
            System.out.println("No approver available: " + describe(request));
        }
    }

    protected abstract int getLimitInCents();

    protected abstract String getName();

    private static String describe(ExpenseRequest request) {
        int cents = request.getAmountInCents();
        return request.getDescription()
                + String.format(PT_BR, " (R$ %,d,%02d)", cents / 100, cents % 100);
    }
}
