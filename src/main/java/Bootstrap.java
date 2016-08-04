import com.jamescross91.amexcsv.AmexCSV;
import com.karlnosworthy.freeagent.FreeAgentClient;
import com.karlnosworthy.freeagent.model.FreeAgentExpense;

import java.io.IOException;
import java.util.List;

public class Bootstrap {
    public static void main(String args[]) throws IOException {
        FreeAgentClient client = FreeAgentClient.authorise("", "v-");

        AmexCSV amexCSV = new AmexCSV("/Users/James/Downloads/amex-ingrid-june16.csv", "/Users/James/Downloads/Statement_Jun 2016.pdf");
        List<FreeAgentExpense> expenses = amexCSV.parse();

        double tot = expenses.stream().mapToDouble(expense -> Double.parseDouble(expense.getGrossValue())).sum();
        System.out.println("Total number of expenses found is " + expenses.size());
        System.out.println("Total value of expenses found is " + tot);

        for(FreeAgentExpense expense : expenses) {
            client.createExpense(expense);
            System.out.println("Submitted expense " + expense.getDescription());
        }
    }

    private static FreeAgentExpense getDummyExpense() {
        FreeAgentExpense freeAgentExpense = new FreeAgentExpense();
        freeAgentExpense.setUser("https://api.freeagent.com/v2/users/380994");
        freeAgentExpense.setGrossValue("-100");
        freeAgentExpense.setDescription("Test expense");
        freeAgentExpense.setDatedOn("2016-08-04");
        freeAgentExpense.setCategory("https://api.freeagent.com/v2/categories/285");
        return freeAgentExpense;
    }
}
