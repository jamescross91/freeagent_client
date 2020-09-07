import com.jamescross91.amexcsv.AmexCSV;
import com.karlnosworthy.freeagent.FreeAgentClient;
import com.karlnosworthy.freeagent.model.FreeAgentExpense;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Bootstrap {
    public static void main(String args[]) throws IOException {
        //CLEAN OAUTH CACHE!
        //Users/James/.store/oauth2/StoredCredential

        File index = new File("/Users/James/.store/oauth2");
        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(), s);
            System.out.printf("Deleting" + s);
            currentFile.delete();
        }
        index.delete();

        FreeAgentClient client = FreeAgentClient.authorise("vwIMroLQA3MAjg0wmY1b1g", "hV48m1NvH_lYZIFyInBpMg");

        client.getCompany();

        AmexCSV amexCSV = new AmexCSV("/Users/James/Downloads/activity.csv", "/Users/James/Downloads/2020-09-05.pdf");
        List<FreeAgentExpense> expenses = amexCSV.parse();

        double tot = expenses.stream().mapToDouble(expense -> Double.parseDouble(expense.getGrossValue())).sum();
        System.out.println("Total number of expenses found is " + expenses.size());
        System.out.println("Total value of expenses found is " + tot);


        for(FreeAgentExpense expense : expenses) {
            client.createExpense(expense);
            System.out.println("Submitted expense " + expense.getDescription() + " with value " + expense.getGrossValue());
        }
    }
}
