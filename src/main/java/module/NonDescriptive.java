package module;

/**
 * Constructs the NonDescriptive module which does not use the conventional code
 * It can be used as a placeholder to indicate other modular credit requirements
 * EG: Industrial Attachment, Technical Electives
 */
public class NonDescriptive extends Module {

    /**
     * Constructs the NonDescriptive module which does not use the conventional code
     * It can be used as a placeholder to indicate other modular credit requirements
     * EG: Industrial Attachment, Technical Electives
     *
     * @param input String indicating the name used as a code
     * @param mcs the credit bearing value tagged to it;
     */
    public NonDescriptive(String input, Integer mcs)
    {
        this.code = input;
        this.mc = mcs;
    }

    /**
     * Returns the module's Name for the side tab
     *
     * @return String which is the module name
     */
    @Override
    public String tabModuleName() {
        return this.getCode();
    }

    /**
     * Returns the module's code
     *
     * @return string which is the module's code
     */
    @Override
    public String tabModuleCode() {
        return "-";
    }

    /**
     * Returns the code and the allocated mc value
     *
     * @return String in the form of code [MC]
     */
    @Override
    public String toString() {
        return this.getCode() + " " + this.getMc();
    }
}
