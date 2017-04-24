package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class Controller {


    @FXML //fx:id="patternStartTextArea"
    private TextArea patternStartTextArea;

    @FXML //fx:id="patternPrecodesTextArea"
    private TextArea patternPrecodesTextArea;

    @FXML //fx:id="patternEndTextArea"
    private TextArea patternEndTextArea;

    @FXML //fx:id="templateChoiceBox"
    private ChoiceBox templateChoiceBox;

    @FXML //fx:id="generateChoiceBox"
    private ChoiceBox generateChoiceBox;

    @FXML // fx:id="gridAcrossTextArea"
    private TextArea gridAcrossTextArea;

    @FXML // fx:id="generateButton"
    private Button generateButton;

    @FXML // fx:id="gridDownTextArea"
    private TextArea gridDownTextArea;

    @FXML // fx:id="questionTextHtml"
    private HTMLEditor questionTextHtml;

    @FXML // fx:id="resultTextArea"
    private TextArea resultTextArea;

    @FXML // fx:id="multipleRadio"
    private RadioButton multipleRadio;

    @FXML // fx:id="gridRadio"
    private RadioButton gridRadio;

    @FXML // fx:id="singleRadio"
    private RadioButton singleRadio;

    @FXML // fx:id="precodesOnlyRadio"
    private RadioButton precodesOnlyRadio;

    @FXML // fx:id="questionNameTextField"
    private TextField questionNameTextField;

    @FXML // fx:id="questionNameLabel"
    private Label questionNameLabel;

    @FXML // fx:id="questionTextLabel"
    private Label questionTextLabel;

    @FXML // fx:id="gridAcrossLabel"
    private Label gridAcrossLabel;

    @FXML // fx:id="gridDownLabel"
    private Label gridDownLabel;

    @FXML // fx:id="closeButton"
    private Button closeButton;

    @FXML //fx:id="webViewPreview"
    private WebView webViewPreview;

    @FXML //fx:id="refreshButton"
    private Button refreshButton;

    @FXML //fx:id="minimizeButton"
    private Button minimizeButton;

    @FXML //fx:id="answersContextMenu"
    private ContextMenu answersContextMenu;

    @FXML //fx:id="boldMenuItem"
    private MenuItem boldMenuItem;

    @FXML //fx:id="underlineMenuItem"
    private MenuItem underlineMenuItem;

    @FXML //fx:id="italicMenuItem"
    private MenuItem italicMenuItem;

    @FXML
    private ToggleGroup Group1;

    private CategoricalQuestion categoricalSingleQuestion =new CategoricalQuestion();
    private CategoricalQuestion categoricalMultipleQuestion =new CategoricalQuestion();
    private GridQuestion myGridQuestion=new GridQuestion();
    private int contor=0;

    @FXML
    public void initialize() {
        //disable gridAcross from start, it will be show if the gridradiobutton is checked:
        gridAcrossTextArea.setDisable(true);

/*        webViewPreview.getEngine().load(
                Controller.class.getResource("Cachequestion.htm").toExternalForm()
        );*/
        //by default have the precodesonly radio selected:
        precodesOnlyRadio.setSelected(true);

        //add some default question templates , to be loaded from save file/database in the future TBD!
        QuestionTemplate defaultTemplate=new QuestionTemplate();
        QuestionTemplate defaultTemplate1=new QuestionTemplate("Categorical with bold",
                "\t#questionName \"#questionText\" \n\tcategorical [#lowerLimit..#upperLimit] \n\t{\n",
                "\t\t#precodCode \"<b>#precodLabel</b>\",\n",
                "\t};");
        QuestionTemplate defaultTemplate2=new QuestionTemplate("Categorical with images",
                "\t#questionName \"#questionText\" \n\tcategorical [#lowerLimit..#upperLimit] \n\t{\n",
                "\t\t#precodCode \"<img src='https:\\\\cdn.ipsos.com\\{#Currentsid}\\#precodCode.jpg'/></br>#precodLabel\",\n",
                "\t};");

        List<QuestionTemplate> list = new ArrayList<>();
        list.add(defaultTemplate);
        list.add(defaultTemplate1);
        list.add(defaultTemplate2);
        ObservableList<QuestionTemplate> observableList = FXCollections.observableList(list);
        templateChoiceBox.setItems(observableList);
        generateChoiceBox.setItems(observableList);

        //set the first item from the choice box to be selected:
        templateChoiceBox.getSelectionModel().selectFirst();
        generateChoiceBox.getSelectionModel().selectFirst();

        //set the default text areas from the template tab:
        patternStartTextArea.setText("\t#questionName \"#questionText\" \n\tcategorical [#lowerLimit..#upperLimit] \n\t{\n");
        patternPrecodesTextArea.setText("\t\t#precodCode \"#precodLabel\",\n");
        patternEndTextArea.setText("\t};");

        // listen for changes to the choiceBox selection and update the textareas accordingly
        templateChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuestionTemplate>() {
             @Override public void changed(ObservableValue<? extends QuestionTemplate> selected, QuestionTemplate oldFruit, QuestionTemplate newFruit) {
                QuestionTemplate tempQtemplate;
                tempQtemplate=selected.getValue();
                patternStartTextArea.setText(tempQtemplate.getPatern_Start());
                patternPrecodesTextArea.setText(tempQtemplate.getPatern_precodes());
                patternEndTextArea.setText(tempQtemplate.getPatern_end());
                //categoricalSingleQuestion.setTemplate(tempQtemplate);
            }
        });

        // listen for changes to the choiceBox selection and update the question object question template
        generateChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuestionTemplate>() {
            @Override public void changed(ObservableValue<? extends QuestionTemplate> selected, QuestionTemplate oldFruit, QuestionTemplate newFruit) {
                QuestionTemplate tempQtemplate;
                tempQtemplate=selected.getValue();
                categoricalSingleQuestion.setTemplate(tempQtemplate);
                categoricalMultipleQuestion.setTemplate(tempQtemplate);
            }
        });

    }

    @FXML
    void generateButtonAction(ActionEvent event) {



            String generated;

            //get the options for grid down:
            String myAnswers;
            myAnswers=gridDownTextArea.getText().trim();
            String [] GroupOfAnswers;
            GroupOfAnswers=myAnswers.split("\n");

            //get the options for grid across:
            String myScale;
            myScale=gridAcrossTextArea.getText().trim();
            String [] ScaleGroup;
            ScaleGroup=myScale.split("\n");

            generated="\t"+questionNameTextField.getText()+ " \""+getNormalText(questionTextHtml.getHtmlText())+"\"";

            //create CategoricalQuestion object:
            List<Precode<String,String>> precodeList = new ArrayList<>();
            Precode myPrecode;
            categoricalSingleQuestion.setName(questionNameTextField.getText());
            categoricalSingleQuestion.setQuestionText(getNormalText(questionTextHtml.getHtmlText()));
            categoricalMultipleQuestion.setName(questionNameTextField.getText());
            categoricalMultipleQuestion.setQuestionText(getNormalText(questionTextHtml.getHtmlText()));

            //create GridQuestion:
            List<Precode<String,String>> iterationList = new ArrayList<>();
            List<Precode<String,String>> answerList = new ArrayList<>();
            myGridQuestion.setName(questionNameTextField.getText());
            myGridQuestion.setQuestionText(getNormalText(questionTextHtml.getHtmlText()));

            // only precodes:
            if (precodesOnlyRadio.isSelected())
            {

                String precode;
                precode=null;

                generated="";
                for (int i=0;i<GroupOfAnswers.length;i++)
                {
                    int firstindex,lastindex;
                    firstindex=GroupOfAnswers[i].length();
                    if (GroupOfAnswers[i].contains("["))
                    {

                        firstindex=GroupOfAnswers[i].lastIndexOf("[");
                        lastindex=GroupOfAnswers[i].lastIndexOf("]");
                        if ((firstindex>-1) && (lastindex>-1))
                        {
                            precode=GroupOfAnswers[i].substring(firstindex+1, lastindex);
                        }
                    }
                    if (precode==null)
                    {
                        precode=Integer.toString(i+1);
                        firstindex=firstindex+1;
                    }

                    myPrecode=new Precode("_"+precode,GroupOfAnswers[i].substring(0, firstindex-1));
                    precodeList.add(myPrecode);
                    generated=generated+"\t\t_"+precode+" \""+GroupOfAnswers[i].substring(0, firstindex-1)+"\",\n";
                    precode=null;
                }

                categoricalSingleQuestion.setPrecodeList(precodeList);
                System.out.print(categoricalSingleQuestion.toString());

                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");

            }

            // Single answer questions:
            if (singleRadio.isSelected())
            {
                String precode;
                precode=null;

                generated=generated+"\n\tcategorical [1..1]";
                generated=generated+"\n\t{\n";
                for (int i=0;i<GroupOfAnswers.length;i++)
                {
                    int firstindex,lastindex;
                    firstindex=GroupOfAnswers[i].length();
                    if (GroupOfAnswers[i].contains("["))
                    {

                        firstindex=GroupOfAnswers[i].lastIndexOf("[");
                        lastindex=GroupOfAnswers[i].lastIndexOf("]");
                        if ((firstindex>-1) && (lastindex>-1))
                        {
                            precode=GroupOfAnswers[i].substring(firstindex+1, lastindex);
                        }
                    }
                    if (precode==null)
                    {
                        precode=Integer.toString(i+1);
                        firstindex=firstindex+1;
                    }
                    generated=generated+"\t\t_"+precode+" \""+GroupOfAnswers[i].substring(0, firstindex-1)+"\",\n";

                    myPrecode=new Precode("_"+precode,GroupOfAnswers[i].substring(0, firstindex-1));
                    precodeList.add(myPrecode);
                    precode=null;
                }

                categoricalSingleQuestion.setPrecodeList(precodeList);
//                System.out.print(categoricalSingleQuestion.toString());

                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");
                generated=generated+"\t};";


                generated= categoricalSingleQuestion.toString();
            }

            // Multiple answer question:
            if (multipleRadio.isSelected())
            {
                String precode;
                precode=null;
                generated=generated+"\n\tcategorical [1..]";
                generated=generated+"\n\t{\n";


                for (int i=0;i<GroupOfAnswers.length;i++)
                {
                    int firstindex,lastindex;
                    firstindex=GroupOfAnswers[i].length();
                    if (GroupOfAnswers[i].contains("["))
                    {

                        firstindex=GroupOfAnswers[i].lastIndexOf("[");
                        lastindex=GroupOfAnswers[i].lastIndexOf("]");
                        if ((firstindex>-1) && (lastindex>-1))
                        {
                            precode=GroupOfAnswers[i].substring(firstindex+1, lastindex);
                        }
                    }
                    if (precode==null)
                    {
                        precode=Integer.toString(i+1);
                        firstindex=firstindex+1;
                    }
                    generated=generated+"\t\t_"+precode+" \""+GroupOfAnswers[i].substring(0, firstindex-1)+"\",\n";

                    myPrecode=new Precode("_"+precode,GroupOfAnswers[i].substring(0, firstindex-1));
                    precodeList.add(myPrecode);
                    precode=null;
                }

                categoricalMultipleQuestion.setPrecodeList(precodeList);
                categoricalMultipleQuestion.setUpperLimit(precodeList.size());
                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");
                generated=generated+"\t};";

                generated= categoricalMultipleQuestion.toString();
            }

            // Grid question:
            if(gridRadio.isSelected())
            {
                int firstindex,lastindex;
                String precode;
                String iterationsGrid,iterationsScale;
                iterationsGrid="";
                iterationsScale="";

                precode=null;
                generated=generated+"\n\tloop";
                generated=generated+"\n\t{\n";

                for (int i=0;i<GroupOfAnswers.length;i++)
                {

                    firstindex=GroupOfAnswers[i].length();
                    if (GroupOfAnswers[i].contains("["))
                    {

                        firstindex=GroupOfAnswers[i].lastIndexOf("[");
                        lastindex=GroupOfAnswers[i].lastIndexOf("]");
                        if ((firstindex>-1) && (lastindex>-1))
                        {
                            precode=GroupOfAnswers[i].substring(firstindex+1, lastindex);
                        }
                    }
                    if (precode==null)
                    {
                        precode=Integer.toString(i+1);
                        firstindex=firstindex+1;
                    }
                    generated=generated+"\t\t_"+precode+" \""+GroupOfAnswers[i].substring(0, firstindex-1)+"\",\n";


                    myPrecode=new Precode("_"+precode,GroupOfAnswers[i].substring(0, firstindex-1));
                    iterationList.add(myPrecode);


                    iterationsGrid=iterationsGrid+"_"+precode+",";
                    precode=null;
                }

                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");

                //for generating QA - WIP
                //iterationsGrid=iterationsGrid+"_"+precode;

                precode=null;
                generated=generated+"\t}";
                generated=generated+" fields\n\t(\n\t\t"+questionNameTextField.getText()+"_scale \"\" \n\t\tcategorical[1..1]\n\t\t{\n";

                for (int i=0;i<ScaleGroup.length;i++)
                {
                    //////
                    firstindex=ScaleGroup[i].length();
                    if (ScaleGroup[i].contains("["))
                    {

                        firstindex=ScaleGroup[i].lastIndexOf("[");
                        lastindex=ScaleGroup[i].lastIndexOf("]");
                        if ((firstindex>-1) && (lastindex>-1))
                        {
                            precode=ScaleGroup[i].substring(firstindex+1, lastindex);
                        }
                    }
                    if (precode==null)
                    {
                        precode=Integer.toString(i+1);
                        firstindex=firstindex+1;
                    }
                    generated=generated+"\t\t\t_"+precode+" \""+ScaleGroup[i].substring(0, firstindex-1)+"\",\n";
                    iterationsScale=iterationsScale+"_"+precode+",";

                    myPrecode=new Precode("_"+precode,ScaleGroup[i].substring(0, firstindex-1));
                    answerList.add(myPrecode);

                    precode=null;
                    //////
                    ///generated=generated+"\t\t\t_"+(i+1)+" \""+ScaleGroup[i]+"\",\n";
                }

                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");

                //for generating QA - WIP
                //iterationsScale=iterationsScale+"_"+precode;

                generated=generated+"\t\t};\n\t)expand grid;";

                myGridQuestion.setIterationList(iterationList);
                myGridQuestion.setAnswerList(answerList);

                generated = myGridQuestion.toString();

            }
            //generated=generated.replaceAll("&","&amp;");
            resultTextArea.setText(generated);

            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(generated);
            clipboard.setContent(content);

            //For Cachepage:
            if(singleRadio.isSelected()) {
                String tempString;

                //without files:
                tempString= categoricalSingleQuestion.GetCategoricalButtonsSingleTemplate();
                tempString = tempString.replace("#NumeIntrebare", categoricalSingleQuestion.getName());
                tempString = tempString.replace("#TextIntrebare", categoricalSingleQuestion.getQuestionText());
                tempString = tempString.replace("#TabelGenerat", categoricalSingleQuestion.generateTableForCachePage());
                webViewPreview.getEngine().loadContent(tempString);
            }

            if(multipleRadio.isSelected()) {
                String tempString;

                //without files:
                tempString= categoricalMultipleQuestion.GetCategoricalButtonsMultipleTemplate();
                tempString = tempString.replace("#NumeIntrebare", categoricalMultipleQuestion.getName());
                tempString = tempString.replace("#TextIntrebare", categoricalMultipleQuestion.getQuestionText());
                tempString = tempString.replace("#TabelGenerat", categoricalMultipleQuestion.generateTableForCachePage());
                webViewPreview.getEngine().loadContent(tempString);
            }

            if(gridRadio.isSelected()) {
                String tempString;
                tempString="";

                //generating cachepage with files:
       /*         File input = new File("Cachequestion.htm");
                //contor++;
                //String fileName = "Cachequestion_ProgressiveGrid_" + myGridQuestion.getName() + ".htm";

                try {
                    Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
                    tempString = doc.toString();

                    tempString= GridQuestion.GridProgresiveTemplate;
                    tempString = tempString.replaceAll("#NumeIntrebare", myGridQuestion.getName());
                    tempString = tempString.replaceAll("#TextIntrebare", myGridQuestion.getQuestionText());
                    tempString = tempString.replaceAll("#TabelGenerat", myGridQuestion.generateTableForCachePage());

                    // The name of the file to create/open.
                   *//*String path = System.getProperty("user.dir");
                    FileWriter fileWriter = new FileWriter(path + "\\out\\production\\DimensionsGeneratorV2\\sample\\" + fileName);
                    System.out.println(path + "\\src\\sample\\" + fileName);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(tempString);

                    // Always close files.
                    bufferedWriter.close();
                    fileWriter.close();*//*

                } catch (IOException e) {
                    System.out.print("Nu am gasit fisierul");
                    e.printStackTrace();
                }

   *//*             webViewPreview.getEngine().load(
                        Controller.class.getResource(fileName).toExternalForm()
                );*/

                //without files:
                tempString = myGridQuestion.GetGridProgresiveTemplate();
                tempString = tempString.replace("#NumeIntrebare", myGridQuestion.getName());
                tempString = tempString.replace("#TextIntrebare", myGridQuestion.getQuestionText());
                tempString = tempString.replace("#TabelGenerat", myGridQuestion.generateTableForCachePage());
                //System.out.print(tempString);
                webViewPreview.getEngine().loadContent(tempString);
            }

    }

    @FXML
    void actionContextMenu(ActionEvent event)
    {
        if (event.getSource()==boldMenuItem) {
            gridDownTextArea.replaceSelection("<b>" + gridDownTextArea.getSelectedText() + "</b>");
        }else if (event.getSource()==underlineMenuItem)
        {
            gridDownTextArea.replaceSelection("<u>" + gridDownTextArea.getSelectedText() + "</u>");
        }
        else if(event.getSource()==italicMenuItem)
        {
            gridDownTextArea.replaceSelection("<i>" + gridDownTextArea.getSelectedText() + "</i>");
        }
    }

    public static String getNormalText(String htmlText) {

        String result;


        Document doc = Jsoup.parse(htmlText,"UTF-8");
        System.out.println(doc.html());
        Element theBody=doc.body();

        result = theBody.html().replaceAll("<b>", "#sfbold");
        result = result.replaceAll("</b>", "#sfclosebold");
        result = result.replaceAll("<u>", "#sfunderline");
        result = result.replaceAll("</u>", "#sfcloseunderline");
        result = result.replaceAll("<i>", "#sfItalic");
        result = result.replaceAll("</i>", "#sfcloseItalic");
        result = result.replaceAll("<[^>]*>", "").trim();
        result = result.replaceAll("#sfbold", "<b>");
        result = result.replaceAll("#sfclosebold", "</b>");
        result = result.replaceAll("#sfunderline", "<u>");
        result = result.replaceAll("#sfcloseunderline", "</u>");
        result = result.replaceAll("#sfItalic", "<i>");
        result = result.replaceAll("#sfcloseItalic", "</i>");
        result = result.replaceAll("&nbsp;", ""); //remove nbsp
        result = result.replaceAll("(?m) +$", ""); ///remove trailing spaces for each line
        result = result.replaceAll("(?m)^+\\s", ""); ///remove spaces from the start of the line
        result = result.replaceAll("\n\n", "\n"); ///remove double new line

        result = result.trim();
        System.out.println(result);
        return result;
    }

    @FXML
    void closeButtonAction(ActionEvent event){
        if(event.getSource()==closeButton) {
            Stage stage = (Stage) generateButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void hideGridAcrossTextArea(ActionEvent event)
    {
        gridAcrossTextArea.setDisable(true);
    }

    @FXML
    void showGridAcrossTextArea(ActionEvent event)
    {
        gridAcrossTextArea.setDisable(false);
    }


    @FXML
    void minimizeButtonAction(ActionEvent event){
            ((Stage)((Button)event.getSource()).getScene().getWindow()).setIconified(true);
    }



}
