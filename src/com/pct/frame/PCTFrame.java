package com.pct.frame;

import javax.swing.*;
import java.awt.*;

public class PCTFrame extends JFrame {
    private JPanel imagePreviewPane;
    private JPanel imageListPane;
    private JPanel userControlPane;

    private final Point screenCenter = new Point();

    public PCTFrame(){
        super();
        initUI();
    }

    // TODO: Adding Tooltips

    private void initUI()
    {
        this.setTitle(Const.FRAME_TITLE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Const.DEFAULT_FRAME_WIDTH, Const.DEFAULT_FRAME_HEIGHT);
        this.setResizable(true);

        this.setMinimumSize(new Dimension(
                (int) Const.MINIMUM_FRAME_WIDTH,
                (int) Const.MINIMUM_FRAME_HEIGHT
        ));

        this.updateScreenCenter();

        this.setLocation(this.getScreenCenter());
        this.setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }

        this.setUpUserInterface();
        this.setupComponents();
        this.addComponents();
        this.pack();
    }

    private void addComponents() {
        this.add(this.getImageListPane(),    BorderLayout.LINE_START);
        this.add(this.getImagePreviewPane(), BorderLayout.CENTER);
        this.add(this.getUserControlPane(),  BorderLayout.PAGE_END);
    }

    private void setupComponents(){
        //------------------------------------------------------------------------------------------------------
        // IMAGE LIST SECTION
        // TODO: Set design of list view
        this.getImageListPane().setPreferredSize(new Dimension(
                (int) (this.getWidth()*Const.PREFERRED_LIST_WIDTH_FACTOR),
                (int) (this.getHeight()*Const.PREFERRED_LIST_HEIGHT_FACTOR)
        ));

        this.getImageListPane().setLayout(new BorderLayout());

        JList<String> imageFileList = new JList<>();
        imageFileList.setListData(new String[]{
                "Test1",
                "Test2",
                "Test3",
                "Test4",
                "Test5",
                "Test6",
        });

        this.getImageListPane().add(imageFileList, BorderLayout.CENTER);
        //------------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------------
        // IMAGE PREVIEW SECTION
        this.getImagePreviewPane().setPreferredSize(new Dimension(
                (int) (this.getWidth()*Const.PREFERRED_SIZE_FACTOR),
                (int) (this.getHeight()*Const.PREFERRED_SIZE_FACTOR)
        ));

        this.getImagePreviewPane().setBackground(Color.BLACK);
        //------------------------------------------------------------------------------------------------------


        //------------------------------------------------------------------------------------------------------
        // CONTROLS BOX SECTION
        this.getUserControlPane().setPreferredSize(new Dimension(
                this.getWidth(),
                (int) (this.getHeight()*Const.PREFERRED_INPUT_HEIGHT_FACTOR)
        ));

        this.getUserControlPane().setBackground(Color.MAGENTA);

        this.getUserControlPane().setLayout(new BorderLayout());

        JPanel controlsBox = new JPanel();
        controlsBox.setPreferredSize(new Dimension(
                (int) this.getImageListPane().getPreferredSize().getWidth(),
                (int) this.getUserControlPane().getPreferredSize().getHeight()
        ));
        controlsBox.setLayout(new BorderLayout());

        JPanel movePanel = new JPanel();
        JButton imgUpButton = new JButton(Const.UP_TEXT);
        JButton imgDownButton = new JButton(Const.DOWN_TEXT);
        movePanel.setLayout(new BorderLayout());
        movePanel.setBorder(BorderFactory.createEmptyBorder(
                Const.MOVE_MARGIN_TOP,
                Const.MOVE_MARGIN_LEFT,
                Const.MOVE_MARGIN_BOT,
                Const.MOVE_MARGIN_RIGHT
        ));
        movePanel.add(imgUpButton, BorderLayout.PAGE_START);
        movePanel.add(imgDownButton, BorderLayout.PAGE_END);
        controlsBox.add(movePanel, BorderLayout.LINE_START);

        JPanel addPanel = new JPanel();
        JButton imgAddButton = new JButton(Const.ADD_TEXT);
        JButton imgRemoveButton = new JButton(Const.REMOVE_TEXT);
        addPanel.setLayout(new BorderLayout());
        addPanel.setBorder(BorderFactory.createEmptyBorder(
                Const.ADD_MARGIN_TOP,
                Const.ADD_MARGIN_LEFT,
                Const.ADD_MARGIN_BOT,
                Const.ADD_MARGIN_RIGHT
        ));
        addPanel.add(imgAddButton, BorderLayout.PAGE_START);
        addPanel.add(imgRemoveButton, BorderLayout.PAGE_END);
        controlsBox.add(addPanel, BorderLayout.LINE_END);

        JPanel userParameterInputBox = new JPanel();
        userParameterInputBox.setLayout(new BorderLayout());

        JPanel expansionDegreePanel = new JPanel();
        JLabel expansionDegreeLabel = new JLabel(Const.EXPANSION_DEGREE_LABEL);
        expansionDegreeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        expansionDegreeLabel.setPreferredSize(Const.LABEL_DEFAULT_DIMENSION);
        expansionDegreePanel.add(expansionDegreeLabel);

        JTextField expansionDegreeText = new JTextField();
        expansionDegreeText.setPreferredSize(Const.TEXTFIELD_DEFAULT_DIMENSION);
        expansionDegreePanel.add(expansionDegreeText);

        JPanel distancePanel = new JPanel();
        JLabel distanceLabel = new JLabel(Const.DISTANCE_LABEL);
        distanceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        distanceLabel.setPreferredSize(Const.LABEL_DEFAULT_DIMENSION);
        distancePanel.add(distanceLabel, BorderLayout.PAGE_END);

        JTextField distanceText = new JTextField();
        distanceText.setPreferredSize(Const.TEXTFIELD_DEFAULT_DIMENSION);
        distancePanel.add(distanceText);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BorderLayout());
        buttonPane.setBorder(BorderFactory.createEmptyBorder(
                Const.INFO_MARGIN_TOP,
                Const.INFO_MARGIN_LEFT,
                Const.INFO_MARGIN_BOT,
                Const.INFO_MARGIN_RIGHT
        ));
        buttonPane.add(expansionDegreePanel, BorderLayout.PAGE_START);
        buttonPane.add(distancePanel, BorderLayout.PAGE_END);

        JPanel conversionPane = new JPanel();
        conversionPane.setLayout(new BorderLayout());
        conversionPane.setBorder(BorderFactory.createEmptyBorder(
                Const.INFO_MARGIN_TOP,
                Const.INFO_MARGIN_LEFT,
                Const.INFO_MARGIN_BOT,
                Const.INFO_MARGIN_RIGHT
        ));

        JLabel conversionModeLabel = new JLabel(Const.CONVERSION_MODE_LABEL);
        conversionModeLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
        conversionModeLabel.setPreferredSize(Const.LABEL_DEFAULT_DIMENSION);

        JRadioButton listRadioButton = new JRadioButton(Const.LIST_RADIO_TEXT);
        listRadioButton.setSelected(true);
        conversionPane.add(listRadioButton, BorderLayout.PAGE_START);

        JRadioButton dimensionalRadioButton = new JRadioButton(Const.DIMENSIONAL_RADIO_TEXT);
        conversionPane.add(dimensionalRadioButton, BorderLayout.PAGE_START);

        ButtonGroup conversionModeGroup = new ButtonGroup();
        conversionModeGroup.add(dimensionalRadioButton);
        conversionModeGroup.add(listRadioButton);

        JPanel radioButtons = new JPanel();
        radioButtons.add(conversionModeLabel);
        radioButtons.add(listRadioButton);
        radioButtons.add(dimensionalRadioButton);
        conversionPane.add(radioButtons, BorderLayout.PAGE_START);

        JButton changeZOrder = new JButton(Const.CHANGE_Z_ORDER_TEXT);
        JButton convertButton = new JButton(Const.CONVERT_TEXT);

        JPanel buttons = new JPanel();
        buttons.add(changeZOrder);
        buttons.add(convertButton);
        conversionPane.add(buttons, BorderLayout.PAGE_END);

        userParameterInputBox.add(buttonPane, BorderLayout.LINE_START);
        userParameterInputBox.add(conversionPane, BorderLayout.CENTER);

        JButton aboutButton = new JButton(Const.ABOUT_TITLE);
        aboutButton.addActionListener(e -> {
            JDialog aboutDialog = new JDialog();
            aboutDialog.setTitle(Const.ABOUT_TITLE);
            aboutDialog.setSize(Const.DIALOG_WIDTH, Const.DIALOG_HEIGHT);
            aboutDialog.setLocationRelativeTo(this);
            aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            aboutDialog.setResizable(false);
            aboutDialog.setVisible(true);
        });

        JButton imprintButton = new JButton(Const.IMPRINT_TITLE);
        imprintButton.addActionListener(e -> {
            JDialog imprintDialog = new JDialog();
            imprintDialog.setTitle(Const.IMPRINT_TITLE);
            imprintDialog.setSize(Const.DIALOG_WIDTH, Const.DIALOG_HEIGHT);
            imprintDialog.setLocationRelativeTo(this);
            imprintDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            imprintDialog.setResizable(false);
            imprintDialog.setVisible(true);
        });

        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BorderLayout(Const.INFO_HGAP, Const.INFO_VGAP));
        infoBox.setBorder(BorderFactory.createEmptyBorder(
                Const.INFO_MARGIN_TOP,
                Const.INFO_MARGIN_LEFT,
                Const.INFO_MARGIN_BOT,
                Const.INFO_MARGIN_RIGHT
        ));

        infoBox.add(aboutButton, BorderLayout.NORTH);
        infoBox.add(imprintButton, BorderLayout.SOUTH);

        this.getUserControlPane().add(controlsBox, BorderLayout.LINE_START);
        this.getUserControlPane().add(userParameterInputBox, BorderLayout.CENTER);
        this.getUserControlPane().add(infoBox, BorderLayout.LINE_END);
        //------------------------------------------------------------------------------------------------------
    }

    private void setUpUserInterface() {
        this.setImagePreviewPane(new JPanel());
        this.setImageListPane(new JPanel());
        this.setUserControlPane(new JPanel());
    }

    private Point getScreenCenter() {
        return this.screenCenter;
    }

    private void updateScreenCenter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int xPositionCenter = (screenSize.width - this.getWidth())/2;
        int yPositionCenter = (screenSize.height - this.getHeight())/2;

        this.screenCenter.setLocation(xPositionCenter, yPositionCenter);
    }

    public JPanel getImagePreviewPane() {
        return imagePreviewPane;
    }

    public void setImagePreviewPane(JPanel imagePreviewPane) {
        this.imagePreviewPane = imagePreviewPane;
    }

    public JPanel getImageListPane() {
        return imageListPane;
    }

    public void setImageListPane(JPanel imageListPane) {
        this.imageListPane = imageListPane;
    }

    public JPanel getUserControlPane() {
        return userControlPane;
    }

    public void setUserControlPane(JPanel userControlPane) {
        this.userControlPane = userControlPane;
    }
}
