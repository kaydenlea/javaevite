package src.survey.viewController;

import java.time.LocalDate;

/**
 * The UI interface is programmed by Kendra, which includes two subclasses UIConsole and UIPopUP dealing with
 * console input and pop-up-window input, respectively, for user interaction.
 * I incorporated it in my program and basically used UIConsole with user interaction.
 */
public interface UI {
    public void print(String output);

    public void print(Object output);

    public void printPretty(String output);

    public String inputLine(String prompt);

    public String inputWord(String prompt);

    public String inputWord(String prompt, String... options);

    public int inputInt(String prompt);

    public int inputInt(String prompt, int minValue, int maxValue);

    public int inputInt(String prompt, int minValue, int maxValue, int quitValue);

    public int inputPostiveInt(String prompt);

    public double inputDouble(String prompt);

    public boolean inputBoolean(String prompt);

    public boolean inputYesNo(String prompt);

    public LocalDate getDate(String msg);
}
