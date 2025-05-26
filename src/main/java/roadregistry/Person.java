package roadregistry;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Person {

	private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthday;
    private int demeritPoints;
    private boolean isSuspended;
    private List<String[]> demeritHistory = new ArrayList<>();
    
    public Person(String personID, String firstName, String lastName, String address, String birthday) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
        this.demeritPoints = 0;
        this.isSuspended = false;
    }

    public boolean addPerson() {
        if (!validatePersonID(this.personID) || !validateAddress(this.address) || !validateBirthday(this.birthday)) {
            return false;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("persons.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean updatePersonalDetails(String newID, String newFirstName, String newLastName, String newAddress, String newBirthday) {
        // Implementation placeholder – we’ll add logic based on update rules
        return false;
    }

    public String addDemeritPoints(String offenseDate, int points) {
        if (!validateDate(offenseDate) || points < 1 || points > 6)
            return "Failed";

        // Add to history
        demeritHistory.add(new String[]{offenseDate, String.valueOf(points)});

        // Recalculate total points in last 2 years
        int recentPoints = 0;
        LocalDate offense = LocalDate.parse(offenseDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        for (String[] record : demeritHistory) {
            LocalDate recordDate = LocalDate.parse(record[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if (ChronoUnit.YEARS.between(recordDate, offense) <= 2) {
                recentPoints += Integer.parseInt(record[1]);
            }
        }

        int age = calculateAge(this.birthday);
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

    private boolean validatePersonID(String id) {
        return id.matches("^[2-9]{2}.{6}[A-Z]{2}$") &&
               id.substring(2, 8).replaceAll("[^!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]", "").length() >= 2;
    }

    private boolean validateAddress(String address) {
        return address.matches("^\\d+\\|[^|]+\\|[^|]+\\|Victoria\\|[^|]+$");
    }

    private boolean validateBirthday(String dob) {
        return dob.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }

    private boolean validateDate(String date) {
        return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
    }

    private int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public boolean isSuspended() {
        return this.isSuspended;
    }
    
    @Override
    public String toString() {
        return personID + "|" + firstName + "|" + lastName + "|" + address + "|" + birthday;
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
}
