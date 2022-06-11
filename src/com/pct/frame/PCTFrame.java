package com.pct.frame;

import com.pct.Const;
import com.pct.Essentials;
import com.pct.frame.listeners.*;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URI;

public class PCTFrame extends JFrame {

    private final JPanel imagePreviewPane = new JPanel();
    private final JPanel userControlPane = new JPanel();
    private final JPanel controlsBox = new JPanel();
    private final JPanel movePanel = new JPanel();
    private final JPanel userParameterInputBox = new JPanel();
    private final JPanel addPanel = new JPanel();
    private final JPanel infoBox = new JPanel();
    private final JPanel distancePanel = new JPanel();
    private final JPanel buttonPane = new JPanel();
    private final JPanel expansionDegreePanel = new JPanel();
    private final JPanel buttons = new JPanel();
    private final JPanel radioButtons = new JPanel();
    private final JPanel conversionPane = new JPanel();
    private final JLabel voxelSizeLabel = new JLabel(Const.DISTANCE_LABEL);
    private final JLabel expansionDegreeLabel = new JLabel(Const.EXPANSION_DEGREE_LABEL);
    private final JLabel conversionModeLabel = new JLabel(Const.CONVERSION_MODE_LABEL);
    private final JLabel imagePreviewLabel = new JLabel();
    private final JButton imgUpButton = new JButton(Const.UP_TEXT);
    private final JButton imgDownButton = new JButton(Const.DOWN_TEXT);
    private final JButton imgAddButton = new JButton(Const.ADD_TEXT);
    private final JButton imgRemoveButton = new JButton(Const.REMOVE_TEXT);
    private final JButton imprintButton = new JButton(Const.IMPRINT_TITLE);
    private final JButton aboutButton = new JButton(Const.ABOUT_TITLE);
    private final JButton changeZOrder = new JButton(Const.CHANGE_Z_ORDER_TEXT);
    private final JButton convertButton = new JButton(Const.CONVERT_TEXT);
    private final JTextField expansionDegreeText = new JTextField();
    private final JTextField voxelSizeText = new JTextField();
    private final JRadioButton listRadioButton = new JRadioButton(Const.LIST_RADIO_TEXT);
    private final JRadioButton dimensionalRadioButton = new JRadioButton(Const.DIMENSIONAL_RADIO_TEXT);
    private final ButtonGroup conversionModeGroup = new ButtonGroup();
    public final JList<String> imageFileList = new JList<>();
    private final JScrollPane imageListPane = new JScrollPane(imageFileList);

    private final Point screenCenter = new Point();

    public PCTFrame(){
        super();
        this.initUI();
    }

    private void initUI()
    {
        this.setTitle(Const.FRAME_TITLE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(Const.DEFAULT_FRAME_WIDTH, Const.DEFAULT_FRAME_HEIGHT);
        this.setResizable(true);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(!imageFileList.isSelectionEmpty()) {
                    imagePreviewLabel.setIcon(Essentials.createCroppedImage(
                            imageFileList.getSelectedValue(),
                            imagePreviewLabel.getWidth(),
                            imagePreviewLabel.getHeight())
                    );
                }
            }
        });

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

        this.setupComponents();
        this.addComponents();
        this.pack();
    }

    private void addComponents() {
        this.movePanel.add(imgUpButton, BorderLayout.PAGE_START);
        this.movePanel.add(imgDownButton, BorderLayout.PAGE_END);

        this.addPanel.add(imgAddButton, BorderLayout.PAGE_START);
        this.addPanel.add(imgRemoveButton, BorderLayout.PAGE_END);

        this.expansionDegreePanel.add(expansionDegreeLabel);
        this.expansionDegreePanel.add(expansionDegreeText);
        this.distancePanel.add(voxelSizeLabel, BorderLayout.PAGE_END);
        this.distancePanel.add(voxelSizeText);

        this.controlsBox.add(movePanel, BorderLayout.LINE_START);
        this.controlsBox.add(addPanel, BorderLayout.LINE_END);

        this.buttonPane.add(expansionDegreePanel, BorderLayout.PAGE_START);
        this.buttonPane.add(distancePanel, BorderLayout.PAGE_END);

        this.conversionPane.add(listRadioButton, BorderLayout.PAGE_START);
        this.conversionPane.add(dimensionalRadioButton, BorderLayout.PAGE_START);

        this.conversionModeGroup.add(dimensionalRadioButton);
        this.conversionModeGroup.add(listRadioButton);

        this.radioButtons.add(conversionModeLabel);
        this.radioButtons.add(listRadioButton);
        this.radioButtons.add(dimensionalRadioButton);
        this.conversionPane.add(radioButtons, BorderLayout.PAGE_START);

        this.buttons.add(changeZOrder);
        this.buttons.add(convertButton);
        this.conversionPane.add(buttons, BorderLayout.PAGE_END);

        this.userParameterInputBox.add(buttonPane, BorderLayout.LINE_START);
        this.userParameterInputBox.add(conversionPane, BorderLayout.CENTER);

        this.infoBox.add(aboutButton, BorderLayout.NORTH);
        this.infoBox.add(imprintButton, BorderLayout.SOUTH);

        this.imagePreviewPane.add(this.imagePreviewLabel, BorderLayout.CENTER);

        this.getUserControlPane().add(controlsBox, BorderLayout.LINE_START);
        this.getUserControlPane().add(userParameterInputBox, BorderLayout.CENTER);
        this.getUserControlPane().add(infoBox, BorderLayout.LINE_END);

        this.add(this.getImageListPane(),    BorderLayout.LINE_START);
        this.add(this.getImagePreviewPane(), BorderLayout.CENTER);
        this.add(this.getUserControlPane(),  BorderLayout.PAGE_END);
    }

    private void setupComponents(){
        this.getImageListPane().setPreferredSize(new Dimension(
                (int) (this.getWidth()*Const.PREFERRED_LIST_WIDTH_FACTOR),
                (int) (this.getHeight()*Const.PREFERRED_LIST_HEIGHT_FACTOR)
        ));

        this.getImagePreviewPane().setBackground(Color.BLACK);
        this.getImagePreviewPane().setLayout(new BorderLayout());
        this.getImagePreviewPane().setPreferredSize(new Dimension(
                (int) (this.getWidth()*Const.PREFERRED_SIZE_FACTOR),
                (int) (this.getHeight()*Const.PREFERRED_SIZE_FACTOR)
        ));

        this.getUserControlPane().setPreferredSize(new Dimension(
                this.getWidth(),
                (int) (this.getHeight()*Const.PREFERRED_INPUT_HEIGHT_FACTOR)
        ));

        // TODO: Make label use the image resolution and ratio (no cropping, etc.)
        this.imagePreviewLabel.setPreferredSize(this.getImagePreviewPane().getPreferredSize());
        this.imagePreviewLabel.setText("");

        this.getUserControlPane().setLayout(new BorderLayout());

        this.imageFileList.setModel(new DefaultListModel<>());
        this.imageFileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.imageFileList.addListSelectionListener(e -> {
            if (this.getImageFileList().getModel().getSize() != Const.NO_IMAGE_IN_LIST
                    && this.getImageFileList().getSelectedValue() != null) {
                this.imagePreviewLabel.setIcon(Essentials.createCroppedImage(
                        imageFileList.getSelectedValue(),
                        imagePreviewLabel.getWidth(),
                        imagePreviewLabel.getHeight()));
            } else {
                this.imagePreviewLabel.setIcon(null);
            }
        });

        this.imgUpButton.setToolTipText(ToolTipTexts.upButtonToolTip);
        this.imgUpButton.addActionListener(new ImageUpListener(this.getImageFileList()));
        this.imgUpButton.setEnabled(false);

        this.imgDownButton.setToolTipText(ToolTipTexts.downButtonToolTip);
        this.imgDownButton.addActionListener(new ImageDownListener(this.getImageFileList()));
        this.imgDownButton.setEnabled(false);

        this.controlsBox.setPreferredSize(new Dimension(
                (int) this.getImageListPane().getPreferredSize().getWidth(),
                (int) this.getUserControlPane().getPreferredSize().getHeight()
        ));
        this.controlsBox.setLayout(new BorderLayout());

        this.movePanel.setLayout(new BorderLayout());
        this.movePanel.setBorder(BorderFactory.createEmptyBorder(
                Const.MOVE_MARGIN_TOP,
                Const.MOVE_MARGIN_LEFT,
                Const.MOVE_MARGIN_BOT,
                Const.MOVE_MARGIN_RIGHT
        ));

        this.imgAddButton.addActionListener(new AddImageListener(this.getImageFileList().getModel()));
        this.imgAddButton.setToolTipText(ToolTipTexts.addButtonToolTip);

        this.imgRemoveButton.addActionListener(new RemoveImageListener(this.getImageFileList()));
        this.imgRemoveButton.setToolTipText(ToolTipTexts.removeButtonToolTip);
        this.imgRemoveButton.setEnabled(false);

        this.addPanel.setLayout(new BorderLayout());
        this.addPanel.setBorder(BorderFactory.createEmptyBorder(
                Const.ADD_MARGIN_TOP,
                Const.ADD_MARGIN_LEFT,
                Const.ADD_MARGIN_BOT,
                Const.ADD_MARGIN_RIGHT
        ));

        this.userParameterInputBox.setLayout(new BorderLayout());

        this.expansionDegreeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.expansionDegreeLabel.setPreferredSize(Const.LABEL_DEFAULT_DIMENSION);

        this.expansionDegreeText.setPreferredSize(Const.TEXTFIELD_DEFAULT_DIMENSION);
        this.expansionDegreeText.setToolTipText(ToolTipTexts.expansionDegreeTextToolTip);
        this.expansionDegreeText.setText("1");
        PlainDocument docExpansionDegree = (PlainDocument) this.expansionDegreeText.getDocument();
        docExpansionDegree.setDocumentFilter(new IntegerFilter());

        this.voxelSizeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.voxelSizeLabel.setPreferredSize(Const.LABEL_DEFAULT_DIMENSION);

        this.voxelSizeText.setPreferredSize(Const.TEXTFIELD_DEFAULT_DIMENSION);
        this.voxelSizeText.setToolTipText(ToolTipTexts.distanceTextToolTip);
        this.voxelSizeText.setText("1");
        PlainDocument docVoxelSize = (PlainDocument) this.voxelSizeText.getDocument();
        docVoxelSize.setDocumentFilter(new IntegerFilter());

        this.changeZOrder.setToolTipText(ToolTipTexts.changeZOrderButtonToolTip);

        this.dimensionalRadioButton.setToolTipText(ToolTipTexts.single3DRadioToolTip);

        this.listRadioButton.setSelected(true);
        this.listRadioButton.setToolTipText(ToolTipTexts.list2DRadioToolTip);

        this.convertButton.setToolTipText(ToolTipTexts.convertButtonToolTip);
        this.convertButton.setEnabled(false);
        this.convertButton.addActionListener(new ConvertListener(
                this.getImageFileList(),
                Integer.parseInt(this.expansionDegreeText.getText()),
                Integer.parseInt(this.voxelSizeText.getText()),
                this.listRadioButton.isSelected()
        ));

        // CHANGE TO TRUE WHEN 3D WILL BE AVAILABLE
        this.changeZOrder.setEnabled(false);
        this.dimensionalRadioButton.setEnabled(false);
        // ----------------------------------------

        this.buttonPane.setLayout(new BorderLayout());
        this.buttonPane.setBorder(BorderFactory.createEmptyBorder(
                Const.INFO_MARGIN_TOP,
                Const.INFO_MARGIN_LEFT,
                Const.INFO_MARGIN_BOT,
                Const.INFO_MARGIN_RIGHT
        ));

        this.conversionPane.setLayout(new BorderLayout());
        this.conversionPane.setBorder(BorderFactory.createEmptyBorder(
                Const.INFO_MARGIN_TOP,
                Const.INFO_MARGIN_LEFT,
                Const.INFO_MARGIN_BOT,
                Const.INFO_MARGIN_RIGHT
        ));

        this.conversionModeLabel.setHorizontalAlignment(SwingUtilities.RIGHT);
        this.conversionModeLabel.setPreferredSize(Const.LABEL_DEFAULT_DIMENSION);

        this.aboutButton.setToolTipText(ToolTipTexts.aboutButtonToolTip);
        this.aboutButton.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                Const.ABOUT_TEXT,
                Const.ABOUT_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        ));

        this.imprintButton.setToolTipText(ToolTipTexts.imprintButtonToolTip);
        this.imprintButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create(Const.IMPRINT_URL));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.infoBox.setLayout(new BorderLayout(Const.INFO_HGAP, Const.INFO_VGAP));
        this.infoBox.setBorder(BorderFactory.createEmptyBorder(
                Const.INFO_MARGIN_TOP,
                Const.INFO_MARGIN_LEFT,
                Const.INFO_MARGIN_BOT,
                Const.INFO_MARGIN_RIGHT
        ));
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
        return this.imagePreviewPane;
    }

    public JScrollPane getImageListPane() {
        return this.imageListPane;
    }

    public JPanel getUserControlPane() {
        return this.userControlPane;
    }

    public JList<String> getImageFileList(){return this.imageFileList;}

    public JButton getImgUpButton() {
        return imgUpButton;
    }

    public JButton getImgDownButton() {
        return imgDownButton;
    }
    public JButton getImgRemoveButton() {
        return imgRemoveButton;
    }

    public JButton getConvertButton() {
        return convertButton;
    }

}
