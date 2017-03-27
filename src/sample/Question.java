package sample;

/**
 * Created by flavius.sefciuc on 3/16/2017.
 */
public abstract class Question {
    public enum questionTypes{
        TEXT,LONG,CATEGORICAL,GRID
    }
    public enum questionOrders{
        RAN,ROT,ASC,DESC,REV,NORMAL
    }
    String name;
    String questionText;
    questionTypes questionType;
    questionOrders questionOrder;

    public questionOrders getQuestionOrder() {
        return questionOrder;
    }

    public questionTypes getQuestionType() {
        return questionType;
    }

    public void setQuestionType(questionTypes questionType) {
        this.questionType = questionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
