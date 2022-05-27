package com.pct.frame;

public final class ToolTipTexts {

    private ToolTipTexts(){}

    public static final String addButtonToolTip = "Opens a dialog to search for .tiff files on your computer and adds " +
            "them to the list";

    public static final String removeButtonToolTip = "Removes the selected file from the image list";

    public static final String upButtonToolTip = "Moves the selected image one position up in the list";

    public static final String downButtonToolTip = "Moves the selected image one position down in the list";

    public static final String aboutButtonToolTip = "Information's about the program and it's author.";

    public static final String imprintButtonToolTip = "Opens the authors imprint page.";

    public static final String changeZOrderButtonToolTip = "Opens a window where the position in the depth coordinate " +
            "z can  be changed.";

    public static final String convertButtonToolTip = "Starts the conversion of the given images in the selected mode " +
            "into spherical harmonics.";

    public static final String list2DRadioToolTip = "In this mode, all opened images in the list will be converted " +
            "into spherical harmonics independently.";

    public static final String single3DRadioToolTip = "In this mode, a single 3D image will be created by the list of " +
            "images. It is necessary that they make up a meaningful object.";

    public static final String expansionDegreeTextToolTip = "In this field you can enter the degree of expansion in" +
            "for the spherical harmonics. Note that in this expansion, a degree of k means 2k+1 parameters that must be" +
            "saved.";

    public static final String distanceTextToolTip = "Here you can enter the distance between the image sections that" +
            "will be used to calculate the voxel size.";
}
