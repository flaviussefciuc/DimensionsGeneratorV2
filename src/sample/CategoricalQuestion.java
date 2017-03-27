package sample;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavius.sefciuc on 3/17/2017.
 */
public class CategoricalQuestion extends Question {
    int upperLimit;
    int lowerLimit;
    List<Precode<String,String>> precodeList = new ArrayList<Precode<String,String>>();
    QuestionTemplate template;

    public CategoricalQuestion() {
        this.upperLimit = 1;
        this.lowerLimit = 1;
        this.name="";
        this.questionText="";
        this.questionType=questionTypes.CATEGORICAL;
        this.questionOrder=questionOrders.NORMAL;
        this.template=new QuestionTemplate();
    }

    public QuestionTemplate getTemplate() {
        return template;
    }

    public void setTemplate(QuestionTemplate template) {
        this.template = template;
    }

    public List<Precode<String, String>> getPrecodeList() {
        return precodeList;
    }

    public void setPrecodeList(List<Precode<String, String>> precodeList) {
        this.precodeList = precodeList;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public String toStringWithTemplate(String thePattern_start,String thePattern_precodes,String thePattern_end)
    {
        String returnedString;

        returnedString=thePattern_start.replaceAll("#questionName",name).replaceAll("#questionText",questionText).replaceAll("#lowerLimit",String.valueOf(lowerLimit)).replaceAll("#upperLimit",String.valueOf(upperLimit));

        for (int i = 0; i < precodeList.size(); i++) {
            returnedString= returnedString + thePattern_precodes.replaceAll("#precodCode",precodeList.get(i).getPrecod()).replaceAll("#precodLabel",precodeList.get(i).getLabel()) ;
        }

        returnedString = returnedString.replaceAll(",$", "");
        returnedString = returnedString + thePattern_end;

        return returnedString;
    }

    @Override
    public String toString() {
        String thePattern_start,thePattern_precodes,thePattern_end,returnedString;

        thePattern_start = template.getPatern_Start();
        thePattern_precodes = template.getPatern_precodes();
        thePattern_end = template.getPatern_end();

        returnedString=thePattern_start.replaceAll("#questionName",name).replaceAll("#questionText",questionText).replaceAll("#lowerLimit",String.valueOf(lowerLimit)).replaceAll("#upperLimit",String.valueOf(upperLimit));

        for (int i = 0; i < precodeList.size(); i++) {
            returnedString= returnedString + thePattern_precodes.replaceAll("#precodCode",precodeList.get(i).getPrecod()).replaceAll("#precodLabel",precodeList.get(i).getLabel()) ;
        }

        returnedString = returnedString.replaceAll(",$", "");
        returnedString = returnedString + thePattern_end;

        return returnedString;
    }
}
