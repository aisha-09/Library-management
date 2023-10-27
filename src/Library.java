import java.sql.SQLException;
import java.util.Arrays;
import java.sql.*;
import java.util.*;


public class Library {


    //Create Table Query
    public static final String CREATE = "DROP TABLE LIBRARY IF EXISTS; create table LIBRARY(" +
            "IssueId INTEGER AUTO_INCREMENT PRIMARY KEY , " +
            "StudentName varchar2(20)," +
            "StudentRoll NUMBER, " +
            "BookId NUMBER, " +
            "BookName varchar2(10)," +
            "BookPrice NUMBER )";

    //Display The Details (CHOICE 1)
    public static final String SELECT = "select * from LIBRARY where IssueId=?";


    // Display The Details After Insert (CHOICE 2)
    public static final String INSERT="INSERT INTO LIBRARY(StudentName, StudentRoll, BookId, BookName, BookPrice) VALUES(?, ?, ?, ?, ?)";
    public static final String INS_SELECT = "select * from LIBRARY";


    //Update UserName or Roll- other fields can be added later (CHOICE 3)
    public static final String UPDATE_STUDENT_NAME ="update LIBRARY set StudentName=? where IssueId=?";
    public static final String UPDATE_STUDENT_ROLL ="update LIBRARY set StudentRoll=? where IssueId=?";

    //Delete User (CHOICE 4)
    public static final String DELETE = "delete from LIBRARY where IssueId=?";

    //Taking input of the choice
    public static int input_choice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            try {
                System.out.println("Choose of the following options by entering the any number between 1-5");
                choice = sc.nextInt();
                sc.nextLine();
                if(choice < 1 || choice > 5){
                    System.out.println("please enter a valid integer from 1 to 5");
                }
            }
            catch (Exception e){
                System.out.println("please enter a valid integer from 1 to 5");
                sc.next();
                choice = -1;
            }
        }
        while(choice < 1 || choice  > 5);

        return choice;
    }

//Checking if roll is an integer
    public static int input_roll(){
        System.out.println("Enter the Roll Number");
        Scanner sc = new Scanner(System.in);
        int roll;
        while(true)
        {
            try{
                roll = sc.nextInt();
                break;
            }catch(Exception e){
                System.out.println("Please enter a number");
                sc.next();
            }
        };
        return roll;
    }

    //checking if book id is an integer
    public static int input_book_id(){
        System.out.println("Enter the Book Id");
        Scanner sc = new Scanner(System.in);
        int book_id;
        while(true)
        {
            try{
                book_id = sc.nextInt();
                break;
            }catch(Exception e){
                System.out.println("Please enter a number");
                sc.next();
            }
        };
        return book_id;
    }

    //checking if book price is an integer

    public static int input_price(){
        System.out.println("Enter the Book Price");
        Scanner sc = new Scanner(System.in);
        int price;
        while(true)
        {
            try{
                price = sc.nextInt();
                break;
            }catch(Exception e){
                System.out.println("Please enter a number");
                sc.next();
            }
        };
        return price;
    }


    public static void main(String[] args){
        System.out.println("Welcome to the library management system");
        System.out.println("To proceed please enter your Issue ID");
        Scanner sc = new Scanner(System.in);
        int IsId;

        System.out.println("Issue ID: ");
        IsId = sc.nextInt();
        sc.nextLine();

        System.out.println("1 => display the user details");
        System.out.println("2 => insert a new user");
        System.out.println("3 => update the values of the user");
        System.out.println("4 => delete the user");
        System.out.println("5 => exit");

        try{
            Connection conn=DriverManager.getConnection("jdbc:h2:./db","root","password");
            PreparedStatement ps = conn.prepareStatement(CREATE);
            ps.execute();
            int choice = input_choice();

            switch(choice)
            {
                case 1:
                    System.out.println("checking if record is present");
                    PreparedStatement ps1 = conn.prepareStatement(SELECT);
                    ps1.setInt(1, IsId);
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next()){
                        System.out.printf("displaying the [%S] User data", IsId);
                        while(rs1.next()){
                            System.out.println(rs1.getInt("IssueId")+" "+
                                    rs1.getInt("StudentRoll")+" "+
                                    rs1.getString("StudentName")+" "+
                                    rs1.getInt("BookId")+" "+
                                    rs1.getString("BookName")+" "+
                                    rs1.getInt("BookPrice"));
                        }
                    }
                    else {
                        System.out.println("no records are found with this Issue number");
                    }
                    break;
                case 2:
                    System.out.println("To insert a new user provide the following details");
                    System.out.println("Enter the Student Name");
                    String name = sc.nextLine();
                    int roll = input_roll();
                    int book_id = input_book_id();
                    System.out.println("Enter the Book Name");
                    String book_name = sc.nextLine();
                    int price = input_price();

                    PreparedStatement ps2 = conn.prepareStatement(INSERT);
                    ps2.setString(1, name);
                    ps2.setInt(2, roll);
                    ps2.setInt(3, book_id);
                    ps2.setString(4, book_name);
                    ps2.setInt(5, price);
                    int k = ps2.executeUpdate();
                    System.out.println(k+"rows added");

                    PreparedStatement ps2_dis = conn.prepareStatement(INS_SELECT);
                    ResultSet rs_in = ps2_dis.executeQuery();
                    System.out.println("displaying the details");
                    while(rs_in.next()){
                        System.out.println(rs_in.getInt(1)+" "+
                                rs_in.getString(2)+" "+
                                rs_in.getInt(3)+" "+
                                rs_in.getInt(4)+" "+
                                rs_in.getString(5)+" "+
                                rs_in.getInt(6));
                    }
                    break;
                case 3:
                    System.out.println("Select the parameters [\"roll\",\" name\"] you wish to update...");

                    String [] parameters = {"roll", "name"};
                    String update_choice;
                    do {
                        update_choice = sc.nextLine();
                        if(!Arrays.asList(parameters).contains(update_choice)){
                            System.out.println("Please choose a valid parameter");
                        }
                    }
                    while(!Arrays.asList(parameters).contains(update_choice.toLowerCase()));

                    switch(update_choice)
                    {
                        case "name":
                            String Name = sc.nextLine();
                            PreparedStatement pud1 = conn.prepareStatement(UPDATE_STUDENT_NAME);
                            pud1.setString(1, Name);
                            pud1.setInt(2, IsId);
                            pud1.executeUpdate();
                            break;
                        case "roll":
                            int r = input_roll();
                            PreparedStatement pud2 = conn.prepareStatement(UPDATE_STUDENT_ROLL);
                            pud2.setInt(1, r);
                            pud2.setInt(2, IsId);
                            pud2.executeUpdate();
                            break;

                    }
                    break;
                case 4:
                    System.out.printf("Delete the user with Registration number %S", IsId);
                    PreparedStatement ps4 = conn.prepareStatement(DELETE);
                    ps4.setInt(1, IsId);
                    int j = ps4.executeUpdate();
                    if(j == 1) {
                        System.out.println("User Deleted successfully");
                    }
                    break;
                case 5:
                    System.out.println("exiting....");
                    break;
            }

            sc.close();
        }
        catch(SQLException sqlException){
            System.out.println("SQLException handler"+sqlException.getMessage());
        }
    }


}