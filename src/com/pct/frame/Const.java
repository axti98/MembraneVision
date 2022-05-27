package com.pct.frame;

import java.awt.*;

public abstract class Const {

    public static final String FILE_CHOOSER_TITLE = "Choose a file...";

    private Const(){}

    // FRAME CONSTANTS
    public static final int DEFAULT_FRAME_WIDTH  = 1280;
    public static final int DEFAULT_FRAME_HEIGHT = 720;

    public static final float MINIMUM_FRAME_WIDTH = DEFAULT_FRAME_WIDTH * 0.8f;
    public static final float MINIMUM_FRAME_HEIGHT = DEFAULT_FRAME_HEIGHT * 0.5f;

    public static final String FRAME_TITLE = "Parameter Converter Tool";
    public static final int DIALOG_WIDTH = 640;
    public static final int DIALOG_HEIGHT = 480;
    public static final String ABOUT_TITLE = "About";
    public static final String IMPRINT_TITLE = "Imprint";

    // IMAGE PREVIEW CONSTANTS
    public static final float PREFERRED_SIZE_FACTOR = 0.85f;

    // IMAGE LIST CONSTANTS
    public static final float PREFERRED_LIST_WIDTH_FACTOR = 0.15f;
    public static final float PREFERRED_LIST_HEIGHT_FACTOR = PREFERRED_SIZE_FACTOR;

    // USER INPUT CONSTANTS
    public static final String EXPANSION_DEGREE_LABEL = "Expansion degree:";
    public static final String DISTANCE_LABEL = "Distance:";

    public static final String CONVERSION_MODE_LABEL = "Conversion mode:";

    public static final String LIST_RADIO_TEXT = "List of 2D images";

    public static final String DIMENSIONAL_RADIO_TEXT = "Single 3D image";

    public static final String CHANGE_Z_ORDER_TEXT = "Change z-order";

    public static final String CONVERT_TEXT = "Convert";

    public static final String UP_TEXT = "Up";
    public static final String DOWN_TEXT = "Down";
    public static final String ADD_TEXT = "Add";
    public static final String REMOVE_TEXT = "Remove";

    public static final Dimension TEXTFIELD_DEFAULT_DIMENSION = new Dimension(100,25);
    public static final Dimension LABEL_DEFAULT_DIMENSION = new Dimension(135,25);
    public static final float PREFERRED_INPUT_HEIGHT_FACTOR = 0.15f;

    public static final int INFO_MARGIN_LEFT = 0;
    public static final int INFO_MARGIN_TOP = 15;
    public static final int INFO_MARGIN_BOT = 15;
    public static final int INFO_MARGIN_RIGHT = 5;
    public static final int INFO_HGAP = 0;
    public static final int INFO_VGAP = 5;

    public static final int MOVE_MARGIN_LEFT = 5;
    public static final int MOVE_MARGIN_TOP = 20;
    public static final int MOVE_MARGIN_BOT = 20;
    public static final int MOVE_MARGIN_RIGHT = 0;

    public static final int ADD_MARGIN_LEFT = 5;
    public static final int ADD_MARGIN_TOP = 20;
    public static final int ADD_MARGIN_BOT = 20;
    public static final int ADD_MARGIN_RIGHT = 5;

    // INFORMATIONS

    public static final String IMPRINT_URL = "https://www.uni-muenster.de/Physik.TP/research/wittkowski/";
    // Todo: Write better about text
    public static final String ABOUT_TEXT = "This application was developed by Alexander Tiekötter of \n"
                                           +"the Institute of Theoretical Phyics at the University of Münster.";

    public static final int NO_INDEX_SELECTED = -1;

    // FILE EXTENSIONS
    public static final String EXT_TIF = "tif";
    public static final String EXT_TIFF = "tiff";
    public static final String EXT_JPG = "jpg";
    public static final String EXT_PNG = "PNG";
    public static final String EXT_RSE = "rose";
}
