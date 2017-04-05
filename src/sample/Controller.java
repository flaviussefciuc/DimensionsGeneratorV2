package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
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

    @FXML // fx:id="questionTextTextArea"
    private TextArea questionTextTextArea;

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

    @FXML
    private ToggleGroup Group1;

    private CategoricalQuestion myQuestion=new CategoricalQuestion();
    private GridQuestion myGridQuestion=new GridQuestion();
    private int contor=0;

    @FXML
    public void initialize() {
        //disable gridAcross from start, it will be show if the gridradiobutton is checked:
        gridAcrossTextArea.setDisable(true);

        webViewPreview.getEngine().load(
                Controller.class.getResource("Cachequestion.htm").toExternalForm()
        );
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
                //myQuestion.setTemplate(tempQtemplate);
            }
        });

        // listen for changes to the choiceBox selection and update the question object question template
        generateChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuestionTemplate>() {
            @Override public void changed(ObservableValue<? extends QuestionTemplate> selected, QuestionTemplate oldFruit, QuestionTemplate newFruit) {
                QuestionTemplate tempQtemplate;
                tempQtemplate=selected.getValue();
                myQuestion.setTemplate(tempQtemplate);
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

            generated="\t"+questionNameTextField.getText()+ " \""+questionTextTextArea.getText()+"\"";

            //create CategoricalQuestion object:
            List<Precode<String,String>> precodeList = new ArrayList<>();
            Precode myPrecode;
            myQuestion.setName(questionNameTextField.getText());
            myQuestion.setQuestionText(questionTextTextArea.getText());

            //create GridQuestion:
            List<Precode<String,String>> iterationList = new ArrayList<>();
            List<Precode<String,String>> answerList = new ArrayList<>();
            myGridQuestion.setName(questionNameTextField.getText());
            myGridQuestion.setQuestionText(questionTextTextArea.getText());


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

                myQuestion.setPrecodeList(precodeList);
                System.out.print(myQuestion.toString());

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

                myQuestion.setPrecodeList(precodeList);
//                System.out.print(myQuestion.toString());

                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");
                generated=generated+"\t};";


                generated=myQuestion.toString();
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

                    precode=null;
                }

                //replace last comma with nothing:
                generated = generated.replaceAll(",$", "");
                generated=generated+"\t};";
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
            resultTextArea.setText(generated);


            //For Cachepage:
            if(gridRadio.isSelected()) {
                String tempString;
                tempString="";

                File input = new File("Cachequestion.htm");
                //contor++;
                //String fileName = "Cachequestion_ProgressiveGrid_" + myGridQuestion.getName() + ".htm";

                try {
                    Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
                    tempString = doc.toString();

                    tempString = tempString.replaceAll("#NumeIntrebare", myGridQuestion.getName());
                    tempString = tempString.replaceAll("#TextIntrebare", myGridQuestion.getQuestionText());
                    tempString = tempString.replaceAll("#TabelGenerat", myGridQuestion.generateTableForCachePage());

                    // The name of the file to create/open.
                   /* String path = System.getProperty("user.dir");
                    FileWriter fileWriter = new FileWriter(path + "\\out\\production\\DimensionsGeneratorV2\\sample\\" + fileName);
                    System.out.println(path + "\\src\\sample\\" + fileName);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(tempString);

                    // Always close files.
                    bufferedWriter.close();
                    fileWriter.close();*/

                } catch (IOException e) {
                    System.out.print("Nu am gasit fisierul");
                    e.printStackTrace();
                }

   /*             webViewPreview.getEngine().load(
                        Controller.class.getResource(fileName).toExternalForm()
                );*/
                webViewPreview.getEngine().loadContent(tempString);
            }

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
