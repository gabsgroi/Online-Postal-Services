import java.util.ArrayList;

public class PackList {

        private ArrayList<Pack> packlist = new ArrayList<>();

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

}
