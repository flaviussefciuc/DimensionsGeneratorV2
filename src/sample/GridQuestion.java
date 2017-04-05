package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavius.sefciuc on 4/5/2017.
 */
public class GridQuestion extends Question {
    int upperLimit;
    int lowerLimit;
    List<Precode<String,String>> IterationList = new ArrayList<Precode<String,String>>();
    List<Precode<String,String>> AnswerList = new ArrayList<Precode<String,String>>();

    public GridQuestion() {
        this.upperLimit = 1;
        this.lowerLimit = 1;
        this.name="";
        this.questionText="";
        this.questionType=questionTypes.GRID;
        this.questionOrder=questionOrders.NORMAL;
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

    public List<Precode<String, String>> getIterationList() {
        return IterationList;
    }

    public void setIterationList(List<Precode<String, String>> iterationList) {
        IterationList = iterationList;
    }

    public List<Precode<String, String>> getAnswerList() {
        return AnswerList;
    }

    public void setAnswerList(List<Precode<String, String>> answerList) {
        AnswerList = answerList;
    }

    public String generateTableForCachePage()
    {
        String generatedTable;
        generatedTable="";
        int numarCategorii=IterationList.size();
        int numarRaspunsuri=AnswerList.size();
        String numeIntrebare=this.getName();

        for(int i=0;i<=numarCategorii;i++)
        {
            generatedTable=generatedTable+"<tr>\n";

            for(int j=0;j<=numarRaspunsuri;j++)
            {
                if (i==0)
                {
                    if(j==0){
                        generatedTable=generatedTable+"\t<td id=\"Cell.0.0\"/>";
                    }
                    else
                    {
                        generatedTable=generatedTable+"\t<td id=\"Cell."+j+".0\" class=\"mrGridQuestionText\" style=\"text-Align: Center;vertical-align: Middle;width: 10%;\">\n" +
                                "\t\t<span class=\"mrQuestionText\" style=\"\">"+AnswerList.get(j-1).getLabel()+"</span>\n" +
                                "\t</td>\n";
                    }
                }
                else
                {
                    if(j==0)
                    {
                        generatedTable=generatedTable+"\t<td id=\"Cell.0."+i+"  \" class=\"mrGridCategoryText\" style=\"text-Align: Left;vertical-align: Middle;\">\n" +
                                "\t\t<span class=\"mrQuestionText\" style=\"\">"+IterationList.get(i-1).getLabel()+"</span>\n" +
                                "\t</td>\n";
                    }
                    else
                    {
                        generatedTable=generatedTable+"\t<td id=\"Cell."+j+"."+i+"\" style=\"text-Align: Center;vertical-align: Middle;\">\n" +
                                "\t\t<div></div>\n" +
                                "\t\t<input type=\"checkbox\" name=\"_Q"+numeIntrebare+"_Q__"+i+"_Q"+numeIntrebare+"__scale_C__"+j+"\" id=\"_Q0_Q"+(i-1)+"_Q0_C"+(j-1)+"\" class=\"mrMultiple\" style=\"\" value=\"__"+j+"\"></input>\n" +
                                "\t</td>\n";
                    }
                }
            }
            generatedTable=generatedTable+"</tr>\n";
        }

        return generatedTable;
    }
    @Override
    public String toString() {
        String returnedString;
        returnedString = "\t" + this.getName() + " \"" + this.getQuestionText() + "\"\n";
        returnedString = returnedString + "\tloop\n";
        returnedString = returnedString + "\t{\n";

        for (int i = 0; i < IterationList.size(); i++) {
            returnedString= returnedString + "\t\t" + IterationList.get(i).getPrecod() + " \"" + IterationList.get(i).getLabel() + "\",\n";
        }
        returnedString = returnedString.replaceAll(",$", "");

        returnedString = returnedString + "\t} fields\n";
        returnedString = returnedString + "\t(\n";
        returnedString = returnedString + "\t\t" + this.getName() + "_scale \"\"\n";
        returnedString = returnedString + "\t\tcategorical [" +this.getLowerLimit() + ".." + this.getUpperLimit() + "]\n";
        returnedString = returnedString + "\t\t{\n";

        for (int i = 0; i < AnswerList.size(); i++) {
            returnedString= returnedString + "\t\t\t" + AnswerList.get(i).getPrecod() + " \"" + AnswerList.get(i).getLabel() + "\",\n";
        }
        returnedString = returnedString.replaceAll(",$", "");

        returnedString = returnedString + "\t\t};\n" ;
        returnedString = returnedString + "\t) expand grid;" ;

        return returnedString;
    }

}
