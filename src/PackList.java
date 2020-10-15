import java.io.Serializable;
import java.util.ArrayList;

public class PackList implements Serializable {

        public ArrayList<Pack> packlist = new ArrayList<>();

        public synchronized void addPack(Pack p) {
            packlist.add(p);
        }

        public ArrayList<Pack> getPacklist() {
            //return list;
            ArrayList<Pack> anotherlist = new ArrayList<>();
            for (Pack p: packlist) {
                Pack x = new Pack(p.getLenght(),p.getWidth(),p.getDepth(),p.getWeight());
                anotherlist.add(x);
            }

            return anotherlist;
        }
        public void removePack(Pack p) {

        packlist.remove(p);
    }
        public void removeAllPack() {
        for(Pack p: packlist){
            packlist.remove(p);
        }
    }
        public String printList () {
            String str_packlist="\n";
            int i=1;
            for (Pack p: packlist){

                 str_packlist= str_packlist+("Pack "+i+": "+p.toString()+"\n") ;
                 i++;

            }
            return  str_packlist;
        }

    @Override
    public String toString() {
        return "Pack list: "+this.printList();
    }
}
