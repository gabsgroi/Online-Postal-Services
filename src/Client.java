import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        // usual stuff here... menu, etc...

        String address = args[0];
        String rmi_name = args[1];

        try {
            Services server = (Services) Naming.lookup("rmi://"+address+"/"+rmi_name);

            System.out.println("L'ora attuale e': ");
            System.out.println(server.getDate());

            System.out.println("Converted to up:"+server.toUP("ciao!"));

            boolean go = true;
            Scanner user_input = new Scanner(System.in);
            Scanner user_input2 = new Scanner(System.in);
            Scanner user_input3 = new Scanner(System.in);
            while (go) {
                System.out.println("--------------------------------");
                System.out.println(" 0 - Login");
                System.out.println(" 1 - Subscrive");
                System.out.println(" 2 - Quit");
                System.out.println("--------------------------------");

                int choice = user_input.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Insert UserId");
                        String userId=user_input.next();
                        System.out.println("Insert Password");
                        String password=user_input.next();
                        User tmp_user= server.searchUser(userId,password);
                        if (tmp_user == null){
                            System.out.println("UserId: "+userId+" not found");
                        }
                        else {
                            /*boolean menu=true;
                            while (menu){
                                System.out.println("--------------------------------");
                                System.out.println(" 0 - Login");
                                System.out.println(" 1 - Subscrive");
                                System.out.println(" 2 - Quit");
                                System.out.println("--------------------------------");
                                int choice2 = user_input.nextInt();
                                switch (choice2){

                                }
                            }*/
                            System.out.println("Hello "+tmp_user.toString());
                        }

                        /*System.out.print("Name:");
                        String name = user_input.next();
                        System.out.print("Age:");
                        int age = user_input.nextInt();
                        Person x = new Person(name, age);
                        server.addPerson(x);*/
                        break;
                    case 1:

                            System.out.println("Insert your complete Name");
                            String name= user_input3.nextLine();
                            System.out.println("Insert your Age");
                            int age = user_input.nextInt();
                            System.out.println("Insert your Address");
                            String home_address=user_input2.nextLine();
                            System.out.println("Insert your UserId");
                            String userId2= user_input.next();
                            System.out.println("Insert your Password");
                            String password2= user_input.next();
                            User tmp= new User(name,age,password2,home_address,userId2);
                            server.addUser(tmp);
                            if(server.searchUser(tmp.getUserid(),tmp.getPassword()).compareTo(tmp)==1){
                                System.out.println("User "+tmp.toString()+" added correctly");
                            }
                            else {
                                System.out.printf("error");
                            }

                        break;
                    case 2:
                        go = false;
                        System.out.println("Quitting client");
                        break;
                    case 3:
                        server.doIntensiveTask();
                        break;

                }
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
