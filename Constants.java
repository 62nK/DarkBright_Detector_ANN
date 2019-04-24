public interface Constants {

    // Constants
    int PROBLEM_SIZE = 4;
    int WHITE_PIXEL = 10;
    int BLACK_PIXEL = -10;

    /** Neural Network **/
    // Neuron Configuration
    int LAYER_COUNT = 3;
    int INPUT_LAYER_NEURON_COUNT = 4;
    int HIDDEN_LAYER_NEURON_COUNT = 2;
    int OUTPUT_LAYER_NEURON_COUNT = 2;

    /** FLAGS **/
    // Debug: std output messages
    boolean DEBUG = true;

    // Training
    int EPOCHS = 500;
    double LEARNING_RATE = 0.7; // -1 for learning rate = 1/(1+epoch#)
    double MOMENTUM = 0.4;

    // IO
    String PATH = "src/cfg/";
    String BACKUP = "backup.ann";
    String FILENAME = LAYER_COUNT+"L"+INPUT_LAYER_NEURON_COUNT+"I"+HIDDEN_LAYER_NEURON_COUNT+"H"+OUTPUT_LAYER_NEURON_COUNT+"O.ann";

    // JavaFX
    double SQUARE_SIZE = 100;
    double HEIGHT = 315;
    double WIDTH = 465;
}
