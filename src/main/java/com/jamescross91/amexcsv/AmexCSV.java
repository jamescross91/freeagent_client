package com.jamescross91.amexcsv;

import com.karlnosworthy.freeagent.model.FreeAgentExpense;
import com.karlnosworthy.freeagent.model.FreeAgentExpenseAttachment;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AmexCSV {
    private final String csvPath;
    private final String pdfPath;

    public AmexCSV(String csvPath, String pdfPath) {
        this.csvPath = csvPath;
        this.pdfPath = pdfPath;
    }

    public List<FreeAgentExpense> parse() throws IOException {
        Reader in = new FileReader(csvPath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);

        List<FreeAgentExpense> expenses = new ArrayList<>();
        for (CSVRecord record : records) {
            if(record.get("Colibri").equals("") || record.get("Date").equals("")) {
                continue;
            }

            expenses.add(buildExpense(record));
        }

        return expenses;
    }

    public FreeAgentExpense buildExpense(CSVRecord record) throws IOException {
        FreeAgentExpense expense = new FreeAgentExpense();
        expense.setUser("https://api.freeagent.com/v2/users/380994");
        String grossValue = record.get("Colibri").replace("£", "").replaceAll("[^\\x00-\\x7F]","");
        expense.setGrossValue("-" + grossValue);
        expense.setDatedOn(getNiceDate(record.get("Date")));
        expense.setDescription(record.get("Description"));
        expense.setCategory(FreeAgentCategory.categoryForDescription(record.get("Description")));
        expense.setAttachment(getAttachment());

        return expense;
    }

    //Return something in the format 2011-08-24
    private String getNiceDate(String csvDate) {
        String inputPattern = "dd MMM yyyy";
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPattern);

        String outputPattern = "yyyy-MM-dd";
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPattern);

        LocalDate date = LocalDate.parse(csvDate, inputFormatter);
        String formattedDate = date.format(outputFormatter);

        return formattedDate;
    }

    private FreeAgentExpenseAttachment getAttachment() throws IOException {
        String data = AttachmentHash.encodeFileToBase64Binary(pdfPath);
        FreeAgentExpenseAttachment attachment = new FreeAgentExpenseAttachment();
        attachment.setData(data);
        attachment.setDescription("Amex bill pdf");
        attachment.setFileName("amex_bill.pdf");
        attachment.setContentType("application/x-pdf");

        return attachment;
    }
}
