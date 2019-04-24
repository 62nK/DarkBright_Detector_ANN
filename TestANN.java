import ArtificialNeuralNetwork.NeuralNetwork;
import ArtificialNeuralNetwork.InputTargetPair;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestANN extends Application implements Constants {

    // Variables
    int layerCount = LAYER_COUNT;
    int inputLayerNeuronCount = INPUT_LAYER_NEURON_COUNT;
    int hiddenLayerNeuronCount = HIDDEN_LAYER_NEURON_COUNT;
    int outputLayerNeuronCount = OUTPUT_LAYER_NEURON_COUNT;
    int epochs = EPOCHS;
    double learningRate = LEARNING_RATE;
    double momentum = MOMENTUM;

    String filename = PATH+FILENAME;

    @Override
    public void start(Stage primaryStage) {

        // Objects
        NeuralNetwork artificialNeuralNetwork;
        BorderPane borderPane = new BorderPane();
        InputImagePane inputImagePane = new InputImagePane(inputLayerNeuronCount);
        OutputDisplay outputDisplay = new OutputDisplay();
        VBox vBoxIO = new VBox();
        vBoxIO.setSpacing(5);
        vBoxIO.setPadding(new Insets(5));
        vBoxIO.getChildren().addAll(inputImagePane, outputDisplay);
        borderPane.setCenter(vBoxIO);


        // Input
        ArrayList<InputTargetPair> trainingSet = new ArrayList<>();
        {
            trainingSet.add(new InputTargetPair(new double[]{-10, -10, -10, -10}, new double[]{0, 1}));
            trainingSet.add(new InputTargetPair(new double[]{-10, -10, -10, 10}, new double[]{0, 1}));
            trainingSet.add(new InputTargetPair(new double[]{-10, -10, 10, -10}, new double[]{0, 1}));
            trainingSet.add(new InputTargetPair(new double[]{-10, -10, 10, 10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{-10, 10, -10, -10}, new double[]{0, 1}));
            trainingSet.add(new InputTargetPair(new double[]{-10, 10, -10, 10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{-10, 10, 10, -10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{-10, 10, 10, 10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, -10, -10, -10}, new double[]{0, 1}));
            trainingSet.add(new InputTargetPair(new double[]{10, -10, -10, 10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, -10, 10, -10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, -10, 10, 10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, 10, -10, -10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, 10, -10, 10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, 10, 10, -10}, new double[]{1, 0}));
            trainingSet.add(new InputTargetPair(new double[]{10, 10, 10, 10}, new double[]{1, 0}));
        }
        ArrayList<InputTargetPair> testSet = new ArrayList<>();
        {
            testSet.add(new InputTargetPair(new double[]{10, 10, -10, -10}, new double[]{1, 0}));
            testSet.add(new InputTargetPair(new double[]{-10, 10, 10, -10}, new double[]{1, 0}));
            testSet.add(new InputTargetPair(new double[]{10, 10, 10, 10}, new double[]{1, 0}));
            testSet.add(new InputTargetPair(new double[]{-10, -10, -10, -10}, new double[]{0, 1}));
            testSet.add(new InputTargetPair(new double[]{-10, -10, 10, -10}, new double[]{0, 1}));
            testSet.add(new InputTargetPair(new double[]{10, 10, 10, 10}, new double[]{1, 0}));

        }
        ArrayList<double[]> inputs = new ArrayList<>();
        for(InputTargetPair itp: trainingSet) inputs.add(itp.getInput());

        artificialNeuralNetwork = new NeuralNetwork(layerCount, inputLayerNeuronCount, hiddenLayerNeuronCount, outputLayerNeuronCount);
        // Output
        if(DEBUG) print(artificialNeuralNetwork);

        // TextFields
        HBox hBoxEpochs = new HBox();
        Text textEpochs = new Text(0,0, "Epochs");
        TextField textFieldEpochs = new TextField(""+epochs);
        textFieldEpochs.setMaxWidth(60);
        textFieldEpochs.textProperty().addListener(e -> {
            int value = Integer.parseInt(textFieldEpochs.getText());
            if(value>=0)
                epochs = value;
            if(DEBUG) System.out.println("Epochs value changed to: "+epochs);
        });
        hBoxEpochs.setAlignment(Pos.CENTER_LEFT);
        hBoxEpochs.setSpacing(10);
        hBoxEpochs.getChildren().add(textEpochs);
        hBoxEpochs.getChildren().add(textFieldEpochs);

        HBox hbLearningRate = new HBox();
        Text textLearningRate = new Text(0,0, "Learning Rate");
        TextField textFieldLearningRate = new TextField(""+learningRate);
        textFieldLearningRate.setMaxWidth(50);
        textFieldLearningRate.textProperty().addListener(e -> {
            double value = Double.parseDouble(textFieldLearningRate.getText());
            if(value<=1 && value>=0 || value==-1)
                learningRate = value;
            if(DEBUG) System.out.println("learning rate value changed to: "+learningRate);
        });
        hbLearningRate.setAlignment(Pos.CENTER_LEFT);
        hbLearningRate.setSpacing(10);
        hbLearningRate.getChildren().add(textLearningRate);
        hbLearningRate.getChildren().add(textFieldLearningRate);

        HBox hbMomentum = new HBox();
        Text textMomentum = new Text(0,0, "Momentum");
        TextField textFieldMomentum = new TextField(""+momentum);
        textFieldMomentum.setMaxWidth(50);
        textFieldMomentum.textProperty().addListener(e -> {
            double value = Double.parseDouble(textFieldMomentum.getText());
            if(value<=1 && value>=0)
                momentum = value;
            if(DEBUG) System.out.println("momentum value changed to: "+momentum);
        });
        hbMomentum.setAlignment(Pos.CENTER_LEFT);
        hbMomentum.setSpacing(10);
        hbMomentum.getChildren().add(textMomentum);
        hbMomentum.getChildren().add(textFieldMomentum);

        // Buttons
        Button nextState = new Button("Next Example");
        Button train = new Button("Train (Back Propagation)");
        Button test = new Button("Test Set");
        Button reset = new Button("Reset Synapses");
        Button restore = new Button("Import ANN");
        Button save = new Button("Export ANN");
        VBox controlPanel = new VBox();
        controlPanel.setAlignment(Pos.TOP_CENTER);
        controlPanel.setSpacing(5);
        controlPanel.setPadding(new Insets(5));
        controlPanel.getChildren().addAll(hBoxEpochs, hbLearningRate, hbMomentum,
                restore, save, reset, train, test, nextState);
        nextState.setMinHeight(40);
        borderPane.setLeft(controlPanel);
        nextState.setOnMouseClicked(event -> {
            inputImagePane.randomGenerator();
            inputImagePane.draw();
            outputDisplay.drawOutput(categorize(artificialNeuralNetwork, inputImagePane.getInput()));
        });
        train.setOnMouseClicked(event -> {
            train(artificialNeuralNetwork, trainingSet, epochs, learningRate, momentum);
        });
        test.setOnMouseClicked(event -> {
            test(artificialNeuralNetwork, testSet);
        });
        reset.setOnMouseClicked(event -> {
            reset(artificialNeuralNetwork);
        });
        restore.setOnMouseClicked(event -> {
            read(artificialNeuralNetwork, filename);
        });
        save.setOnMouseClicked(event -> {
            write(artificialNeuralNetwork, filename);
        });

        // Put the pane on the scene and the scene on the stage
        Scene scene = new Scene(borderPane, WIDTH, Constants.HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Artificial Neural Network Dark/Bright detection with Simulated Annealing - Andrea Pinardi");
        primaryStage.show();

    }

    public static void reset(NeuralNetwork neuralNetwork){
        neuralNetwork.resetSynapses();
        if(DEBUG) print(neuralNetwork);
    }
    public static void write(NeuralNetwork neuralNetwork, String filename){
        neuralNetwork.writeANNToFile(filename);
        if(DEBUG) System.out.println(neuralNetwork.toString("Neural Network written to file \""+filename+"\""));
    }
    public static void print(NeuralNetwork neuralNetwork){
        System.out.println(neuralNetwork.toString("NeuralNetwork"));
    }
    private static void read(NeuralNetwork neuralNetwork, String filename){
        neuralNetwork.toNeurons(NeuralNetwork.readANNFromFile(filename));
        if(DEBUG) System.out.println(neuralNetwork.toString("Neural Network read from file \""+filename+"\""));
    }
    public static void train(NeuralNetwork neuralNetwork, ArrayList<InputTargetPair> trainingSet, int epochs, double learningRate, double momentum){
        neuralNetwork.train(epochs, learningRate, trainingSet, momentum);
        if(DEBUG) System.out.println("Training set error("+trainingSet.size()+"):"+neuralNetwork.getNetworkError());
    }
    private static void test(NeuralNetwork neuralNetwork, ArrayList<InputTargetPair> testSet){
        neuralNetwork.test(testSet);
        if(DEBUG) System.out.println("Test set error("+testSet.size()+"):"+neuralNetwork.getNetworkError());
    }
    private static double[] categorize(NeuralNetwork neuralNetwork, double[] input) {
        neuralNetwork.feedForward(input);
        if (DEBUG) System.out.println(neuralNetwork.outputToString());
        return neuralNetwork.getOutput();
    }

}