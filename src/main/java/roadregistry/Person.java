package roadregistry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private String personID, firstName, lastName, address, birthday;
    private boolean isSuspended;
    private List<String[]> demeritHistory = new ArrayList<>();

    public Person(String personID, String firstName, String lastName, String address, String birthday) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
    }

    public boolean addPerson() {
        return true; // Simplified for testing
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    private boolean validateDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birth = LocalDate.parse(dob, formatter);
        return (int) ChronoUnit.YEARS.between(birth, LocalDate.now());
    }

    public String addDemeritPoints(String offenseDate, int points) {
        if (!validateDate(offenseDate) || points < 1 || points > 6) return "Failed";

        demeritHistory.add(new String[]{offenseDate, String.valueOf(points)});
        int age = calculateAge(this.birthday);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate offense = LocalDate.parse(offenseDate, formatter);

        int recentPoints = 0;
        for (String[] record : demeritHistory) {
            LocalDate recordDate = LocalDate.parse(record[0], formatter);
            if (ChronoUnit.YEARS.between(recordDate, offense) <= 2) {
                recentPoints += Integer.parseInt(record[1]);
            }
        }

        if ((age < 21 && recentPoints > 6) || (age >= 21 && recentPoints > 12)) {
            this.isSuspended = true;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("demerit_points.txt", true))) {
            writer.write(this.personID + "|" + offenseDate + "|" + points);
            writer.newLine();
            return "Success";
        } catch (IOException e) {
            return "Failed";
        }
    }
}

    /*
   // Getters and setters as needed
    public static void main(String[] args) {
        System.out.println("----- RoadRegistry Demo -----");

        // Create a valid person Now we will give invalid password were person wont be added it gets failed 
        Person person = new Person(
            "2!@_&%aAZ", 
            "AA", 
            "BBB", 
            "32|Highland Street|Melbourne|Victoria|Australia", 
            "10-10-1995"
        );

        // Attempt to add the person
        boolean isAdded = person.addPerson();
        System.out.println("Add Person: " + (isAdded ? "Success" : "Failed"));

        // Add demerit points
        String demeritResult = person.addDemeritPoints("15-05-2024", 5);
        System.out.println("Add Demerit Points: " + demeritResult);

        // Confirm suspension status
        System.out.println("Suspension Status: " + (person.isSuspended() ? "Suspended" : "Not Suspended"));

        System.out.println("Check 'persons.txt' and 'demerit_points.txt' for output.");
    }
	*/

