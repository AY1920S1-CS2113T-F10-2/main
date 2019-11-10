package list;

import exception.DukeException;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DegreeListStorage {
    private String filename = "../data/savedegree.txt"; //text file that stores all the information
    File file = new File(filename);
    ArrayList<String> list;
    private List<String> lines;
    UpdateFile upd = new UpdateFile();
    List<Pair<String, Integer>> store = new ArrayList<Pair<String, Integer>>();

    /**
     * The method reads the text file upon launching Duke.
     * The Degreelist of the user is then updated based on the rank each degree is given by the user/
     *
     */
    public void ReadFile(List<String> st) throws DukeException {
        //Check for existence of external save file first
        if (!file.exists()) { //If it does not exist, read from internally
            this.filename = "/data/savedegree.txt"; //text file that stores all the information
            this.file = new File(filename);
        }
        try {
            for (int i = 0; i < st.size(); i++) {
                String[] data = st.get(i).split("-");
                if (data[0].equals("degree")) {
                    if (data.length < 4) {
                        store.add(new Pair<String, Integer>(data[1], Integer.parseInt(data[2])));
                    }
                }
            }
            store.sort(new Comparator<Pair<String, Integer>>() {
                @Override
                public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                    if (o1.getValue() < o2.getValue()) {
                        return -1;
                    } else if (o1.getValue().equals(o2.getValue())) {
                        return 0; // You can change this to make it then look at the
                        //words alphabetical order
                    } else {
                        return 1;
                    }
                }
            });

            for(int i = 0; i < store.size(); i++) {
                list.add(store.get(i).getKey());
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            list.clear();
            throw new DukeException("Issues trying to read savedegree.txt file. Creating new list of choices.");
        }


    }

    /**
     * The method deletes an item from the text file if the user wishes to remove a degree.
     *
     * @param imp
     * @return newLines
     */
    public List<String> AddRemoved(String imp) {
        List<String> newLines = new ArrayList<String>();
        for(String line: lines){
            String [] vals = line.split("-");
            if(vals[1].equals(imp)){
                    newLines.add(vals[0] + "-" + vals[1] +  "-" + vals[2] + "-" + "REMOVED");
            } else {
                newLines.add(line);
            }
        }
        return newLines;
    }

    /**
     * The method calls the AddRemoved function in order to delete the item from the text file.
     *
     * @param imp
     * @throws IOException
     */
    public void processing(String imp) throws IOException {
        lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        AddRemoved(imp); //the description of the task is passed onto the AddRemoved method which has been described above.
        Files.write(file.toPath(), AddRemoved(imp), Charset.defaultCharset());
        int index = list.indexOf(imp);
        for(int i = index + 1; i < list.size(); i++) {
            upd.reduce_index(list.get(i));
      }
        //System.out.print(index);
    }

    /**
     * The method updates the index of the degrees in the text file
     *
     * @param degree
     * @param index
     * @return newLines
     */

    public List<String> Swap(String degree, String index) {
        List<String> newLines = new ArrayList<String>();
        for(String line: lines){
            String [] vals = line.split("-");
            if(vals[1].equals(degree)){
                newLines.add(vals[0] + "-" + vals[1] +  "-" + index);
            } else {
                newLines.add(line);
            }
        }
        return newLines;
    }

    /**
     * The method calls the Swap function in order to update the text file with the indices of the degrees post swapping.
     *
     * @param degree
     * @param index
     * @throws IOException
     */
    public void work(String degree, String index) throws IOException {
        lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
        Swap(degree, index);
        Files.write(file.toPath(), Swap(degree, index), Charset.defaultCharset());
    }

    public void setDegreeList(DegreeList lists) {
        this.list = lists.getDegrees();
    }

}