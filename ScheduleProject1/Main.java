import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdvisingSystem system = new AdvisingSystem();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        system.loadStudentsFromCSV("students_100.csv");
        system.loadEventsFromCSV("events_40.csv");

        System.out.println("Welcome to the Student Advising System!");

        while (choice != 9) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1.    Add a student");
            System.out.println("2.    Search for a student");
            System.out.println("3.    Print students");
            System.out.println("4.    Delete a student");
            System.out.println("5.    Schedule a meeting/workshop");
            System.out.println("6.    Print meeting/workshop details");
            System.out.println("7.    Print workshop participants");
            System.out.println("8.    Print all meetings/workshops alphabetically");
            System.out.println("9.    Exit");
            System.out.print("\nEnter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter the student's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the student's ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the student's email address: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter the student's major: ");
                    String major = scanner.nextLine();
                    System.out.print("Enter the student's year level: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter any notes for the student: ");
                    String notes = scanner.nextLine();
                    if (system.addStudent(new Student(id, name, email, major, year, notes))) {
                        System.out.println("✅ Student added successfully!");
                    } else {
                        System.out.println("❌ Failed to add student (duplicate ID?)");
                    }
                    break;

                case 2:
                    System.out.println("\nSearch by:");
                    System.out.println("1.    Student ID");
                    System.out.println("2.    Email");
                    System.out.println("3.    Full name");
                    System.out.println("4.    Partial name (contains)");
                    System.out.print("\nEnter your choice: ");
                    int sChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (sChoice == 1) {
                        System.out.print("Enter the student's ID: ");
                        int sId = scanner.nextInt();
                        scanner.nextLine();
                        IStudent s = system.searchStudentById(sId);
                        if (s != null) {
                            System.out.println("✅ Student found!");
                            System.out.println(s.toString());
                        } else {
                            System.out.println("❌ Student not found!");
                        }
                    } else if (sChoice == 2) {
                        System.out.print("Enter the student's email: ");
                        String sEmail = scanner.nextLine();
                        IStudent s = system.searchStudentByEmail(sEmail);
                        if (s != null) {
                            System.out.println("✅ Student found!");
                            System.out.println(s.toString());
                        } else {
                            System.out.println("❌ Student not found!");
                        }
                    } else if (sChoice == 3) {
                        System.out.print("Enter the student's full name: ");
                        String sName = scanner.nextLine();
                        system.printStudentsByName(sName);
                    } else if (sChoice == 4) {
                        System.out.print("Enter a part of the student's name: ");
                        String partial = scanner.nextLine();
                        system.printStudentsByPartialName(partial);
                    } else {
                        System.out.println("❌ Invalid choice!");
                    }
                    break;

                case 3:
                    System.out.println("\nPrint students by:");
                    System.out.println("1.    All students");
                    System.out.println("2.    Major");
                    System.out.println("3.    Year level");
                    System.out.print("\nEnter your choice: ");
                    int pChoice = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (pChoice == 1) {
                        System.out.println("\n--- All Students ---");
                        system.printAllStudents();
                    } else if (pChoice == 2) {
                        System.out.print("Enter the major: ");
                        String pMajor = scanner.nextLine();
                        System.out.println("\n--- Students in " + pMajor + " ---");
                        system.printStudentsByMajor(pMajor);
                    } else if (pChoice == 3) {
                        System.out.print("Enter the year level: ");
                        int pYear = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("\n--- Students in Year " + pYear + " ---");
                        system.printStudentsByYearLevel(pYear);
                    } else {
                        System.out.println("❌ Invalid choice!");
                    }
                    break;

                case 4:
                    System.out.print("Enter the student's ID to delete: ");
                    int delId = scanner.nextInt();
                    scanner.nextLine();
                    if (system.deleteStudent(delId)) {
                        System.out.println("✅ Student deleted successfully!");
                    } else {
                        System.out.println("❌ Student not found!");
                    }
                    break;

                case 5:
                    System.out.println("Enter type:");
                    System.out.println("1.    Workshop");
                    System.out.println("2.    Meeting");
                    System.out.print("\nEnter your choice: ");
                    int type = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (type == 1) {
                        System.out.print("Enter workshop title: ");
                        String t = scanner.nextLine();
                        System.out.print("Enter student names separated by a comma: ");
                        String ns = scanner.nextLine();
                        
                        System.out.print("Enter workshop START date and time (MM/DD/YYYY HH:MM): ");
                        String startStr = scanner.nextLine();
                        DateTime workshopStart = parseDateTime(startStr);
                        if (workshopStart == null) {
                            System.out.println("❌ Invalid START time format!");
                            break;
                        }
                        
                        System.out.print("Enter workshop END date and time (MM/DD/YYYY HH:MM): ");
                        String endStr = scanner.nextLine();
                        DateTime workshopEnd = parseDateTime(endStr);
                        if (workshopEnd == null) {
                            System.out.println("❌ Invalid END time format!");
                            break;
                        }
                        
                        System.out.print("Enter workshop location: ");
                        String loc = scanner.nextLine();
                        
                        int[] ids = system.getStudentIdsByNames(ns);
                        if (ids != null && system.scheduleWorkshop(t, workshopStart, workshopEnd, loc, ids)) {
                            System.out.println("✅ Workshop scheduled successfully!");
                        } else {
                            System.out.println("❌ Failed to schedule workshop (check for conflicts or invalid students)");
                        }
                        
                    } else if (type == 2) {
                        System.out.print("Enter meeting title: ");
                        String mt = scanner.nextLine();
                        System.out.print("Enter student ID: ");
                        int sId = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Enter meeting START date and time (MM/DD/YYYY HH:MM): ");
                        String startStr = scanner.nextLine();
                        DateTime meetingStart = parseDateTime(startStr);
                        if (meetingStart == null) {
                            System.out.println("❌ Invalid START time format!");
                            break;
                        }
                        
                        System.out.print("Enter meeting END date and time (MM/DD/YYYY HH:MM): ");
                        String endStr = scanner.nextLine();
                        DateTime meetingEnd = parseDateTime(endStr);
                        if (meetingEnd == null) {
                            System.out.println("❌ Invalid END time format!");
                            break;
                        }
                        
                        System.out.print("Enter meeting location: ");
                        String loc = scanner.nextLine();
                        
                        if (system.scheduleMeeting(mt, meetingStart, meetingEnd, loc, sId)) {
                            System.out.println("✅ Meeting scheduled successfully!");
                        } else {
                            System.out.println("❌ Failed to schedule meeting (check for conflicts or invalid student ID)");
                        }
                    }
                    break;

                case 6:
                    System.out.println("Enter search criteria:");
                    System.out.println("1.    Student name");
                    System.out.println("2.    Workshop/Meeting title");
                    System.out.print("\nEnter your choice: ");
                    int c = scanner.nextInt();
                    scanner.nextLine();
                    if (c == 1) {
                        System.out.print("Enter the student name: ");
                        String sn = scanner.nextLine();
                        system.printEventDetailsByStudentName(sn);
                    } else if (c == 2) {
                        System.out.print("Enter the workshop/meeting title: ");
                        String t = scanner.nextLine();
                        system.printEventDetailsByTitle(t);
                    }
                    break;

                case 7:
                    System.out.print("Enter the workshop title: ");
                    String wTitle = scanner.nextLine();
                    System.out.println("\n--- Participants in '" + wTitle + "' ---");
                    system.printWorkshopStudents(wTitle);
                    break;

                case 8:
                    System.out.println("\n--- All Events (Alphabetically) ---");
                    system.printAllEventsAlphabetically();
                    break;

                case 9:
                    System.out.println("Goodbye!");
                    break;
                    
                default:
                    System.out.println("❌ Invalid choice! Please enter 1-9.");
            }
        }
        scanner.close();
    }
    
    private static DateTime parseDateTime(String input) {
        try {
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                return null;
            }
            
            String[] dateParts = parts[0].split("/");
            String[] timeParts = parts[1].split(":");
            
            if (dateParts.length != 3 || timeParts.length != 2) {
                return null;
            }
            
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            
            return new DateTime(year, month, day, hour, minute);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid date/time format! Use MM/DD/YYYY HH:MM");
            return null;
        } catch (Exception e) {
            System.out.println("❌ Error parsing date/time: " + e.getMessage());
            return null;
        }
    }
}