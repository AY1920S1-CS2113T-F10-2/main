package module;

import parser.Parser;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Conjunctive Modules indicate that they are Modules of a standard type which can be used to complement each other
 * EG CEG Requirements: CG3207 OR CS3230
 * Explanation: Fulfilling either of these 2 counts towards "fulfilling both"
 */
public class ConjunctiveModule extends Module {
    private Map<String, String> modules = new TreeMap<>();
    private String fullModuleName;

    /**
     * Conjuctive Modules are those linked together as either or requirements
     * Fulfilling any one of them is equivalent to fulfilling the MCs allocated to them
     *
     * @param input String of modules separated by " OR "
     * @param mc is the credit amount of a module
     */
    public ConjunctiveModule(String input, Integer mc)
    {
        this.mc = mc;
        this.fullModuleName = input;
        String[] split = input.split("\\s+OR\\s+");
        for(String full: split)
        {
            Scanner temp = new Scanner(full);
            String code = temp.next();
            String name = temp.nextLine();
            name = name.strip();
            modules.put(code, name);
            temp.close();
        }
    }

    /**
     * Returns the codes tagged to the conjunctive modules, separated by |
     *
     * @return String of codes separated by |
     */
    public String getCode(){
        StringBuilder list = new StringBuilder();
        for(Map.Entry<String,String> entry : modules.entrySet()) {
            String key = entry.getKey();

            list.append(key).append("|");
        }
        list.setLength(Math.max(list.length() - 1, 0));
        return list.toString();
    }

    /**
     * Gets the string to be printed with a fixed width
     *
     * @param setWidth the width the string should be fixed to
     * @return the String to be printed
     */
    @Override
    public String getPrint(int setWidth) {
        StringBuilder module = new StringBuilder();
        for(Map.Entry<String,String> entry : modules.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            module.append(key).append(" ").append(value).append(" OR ");
        }
        module.setLength(Math.max(module.length() - 4, 0));
        if(module.length() > setWidth - 5) {
            module.setLength(setWidth - 9);
            module.append("...  ");
        }
        else {
            char[] pad = new char[Math.max(setWidth - module.length() - 4, 0)];
            Arrays.fill(pad, ' ');
            module.append(pad);
        }
        module.append(getMc());
        while(module.length() < setWidth)
        {
            module.append(" ");
        }
        return module.toString();
    }

    /**
     * Returns the module's Name for the side tab
     *
     * @return String which is the module name
     */
    @Override
    public String tabModuleName() {
        StringBuilder list = new StringBuilder();
        for(Map.Entry<String,String> entry : modules.entrySet()) {
            String value = entry.getValue();

            list.append(value).append(" OR ");
        }
        list.setLength(Math.max(list.length() - 4, 0));
        return list.toString();
    }

    /**
     * Returns the view friendly version consisting of the module code and name separated by OR
     *
     * @return String in the fashion described above
     */
    @Override
    public void print()
    {
        StringBuilder list = new StringBuilder();
        for(Map.Entry<String,String> entry : modules.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            list.append(key).append(" ").append(value).append(" OR ");
        }
        list.setLength(Math.max(list.length() - 4, 0));
        char[] pad = new char[Parser.windowWidth - list.length() - 4];
        Arrays.fill(pad, ' ');
        list.append(pad);
        list.append(getMc());
        System.out.println(list.toString());
    }

    /**
     * Returns both names of conjunctive modules in one string
     *
     * @return the names of both conjunctive modules as a string
     */
    @Override
    public String getFullModuleName() {
        return this.fullModuleName;
    }

}
