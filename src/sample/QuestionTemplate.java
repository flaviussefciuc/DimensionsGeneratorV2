package sample;

/**
 * Created by flavius.sefciuc on 3/23/2017.
 */
public class QuestionTemplate {
    String patern_name;
    String patern_Start;
    String patern_precodes;
    String patern_end;

    public QuestionTemplate(String patern_name,String patern_Start, String patern_precodes, String patern_end) {
        this.patern_name=patern_name;
        this.patern_Start = patern_Start;
        this.patern_precodes = patern_precodes;
        this.patern_end = patern_end;
    }

    public QuestionTemplate() {
        patern_name="Categorical default";
        patern_Start="\t#questionName \"#questionText\" \n\tcategorical [#lowerLimit..#upperLimit] \n\t{\n";;
        patern_precodes="\t\t#precodCode \"#precodLabel\",\n";
        patern_end="\t};";
    }

    public String getPatern_name() {
        return patern_name;
    }

    public void setPatern_name(String patern_name) {
        this.patern_name = patern_name;
    }

    public String getPatern_Start() {
        return patern_Start;
    }

    public void setPatern_Start(String patern_Start) {
        this.patern_Start = patern_Start;
    }

    public String getPatern_precodes() {
        return patern_precodes;
    }

    public void setPatern_precodes(String patern_precodes) {
        this.patern_precodes = patern_precodes;
    }

    public String getPatern_end() {
        return patern_end;
    }

    public void setPatern_end(String patern_end) {
        this.patern_end = patern_end;
    }

    @Override
    public String toString() {
        return patern_name ;
    }
}
