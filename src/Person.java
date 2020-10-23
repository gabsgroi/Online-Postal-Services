import java.io.Serializable;

 public class  Person implements Serializable {

    private String userid;
    private String name;
    private int age;
    private String password;
    private String address;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person(String name, int age, String password, String address, String userid) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.address=address;
        this.userid=userid;
    }

    @Override
    public String toString() {
        return name +" "+ age+ "yo"+"\n"+address+"\n"+"ID: "+userid+"\n";
    }

    public String getPassword() {
        return password;
    }


 }
