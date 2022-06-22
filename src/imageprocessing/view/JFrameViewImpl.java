package imageprocessing.view;

import com.sun.jdi.Value;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Filter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import imageprocessing.controller.Features;
import imageprocessing.controller.ImageControllerImpl;
import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.model.componentbifunctions.BlueBiFunction;
import imageprocessing.model.componentbifunctions.ColorTransformationBiFunction;
import imageprocessing.model.componentbifunctions.FilterBiFunction;
import imageprocessing.model.componentbifunctions.GreenBiFunction;
import imageprocessing.model.componentbifunctions.IntensityBiFunction;
import imageprocessing.model.componentbifunctions.LumaBiFunction;
import imageprocessing.model.componentbifunctions.RedBiFunction;
import imageprocessing.model.componentbifunctions.ValueBiFunction;

/**
 *
 */
public class JFrameViewImpl extends JFrame implements IJFrameView {
  private final IProcessingImageView delegate = new BasicImageProcessingView();

  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JButton exitButton;
  private JButton horizontalFlipButton;
  private JButton verticaLFlipButton;
  private JButton greyscaleButton;
  private JButton sepiaButton;
  private JButton blurButton;
  private JButton sharpenButton;
  // drop down
  private JButton componentButton;
  // user will type in a value
  private JButton brightnessButton;

  private JPanel imagePanel = new JPanel();
  private JButton fileOpenButton;
  private JLabel imageLabel;

  private JButton fileSaveButton;

  /**
   *
   */
  public JFrameViewImpl() {
    super();
    setTitle("Image Processor");

    setDefaultLookAndFeelDecorated(false);
    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // THE MAIN PANEL
    mainPanel = new JPanel();
    // mainPanel.setLayout(new GridLayout());
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);


    // displays a scrollable image
    // a border around the panel with a caption

    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    imagePanel.setMaximumSize(new Dimension(1000, 800));
    mainPanel.add(imagePanel);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(""));
    imageScrollPane.setPreferredSize(new Dimension(1000, 800));
    imagePanel.add(imageScrollPane);


    // PANEL CONTAINING ALL COMMANDS
    JPanel commandPanel = new JPanel();
    commandPanel.setBorder(BorderFactory.createTitledBorder("Image Processing Commands"));
    mainPanel.add(commandPanel);

    // Horizontal flip button
    horizontalFlipButton = new JButton("Horizontal flip");
    horizontalFlipButton.setActionCommand("Horizontal flip");
    commandPanel.add(horizontalFlipButton);

    // Vertical flip button
    verticaLFlipButton = new JButton("Vertical flip");
    verticaLFlipButton.setActionCommand("Vertical flip");
    commandPanel.add(verticaLFlipButton);

    // Sepia button
    sepiaButton = new JButton("Sepia");
    sepiaButton.setActionCommand("Sepia");
    commandPanel.add(sepiaButton);

    // Greyscale button
    greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("Greyscale");
    commandPanel.add(greyscaleButton);

    // Blur button
    blurButton = new JButton("Blur");
    blurButton.setActionCommand("Blur");
    commandPanel.add(blurButton);

    // Sharpen button
    sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("Sharpen");
    commandPanel.add(sharpenButton);


    // Component Options Button
    JPanel componentOptionsPanel = new JPanel();
    componentOptionsPanel.setLayout(new FlowLayout());
    commandPanel.add(componentOptionsPanel);

    componentButton = new JButton("Component Representation");
    componentButton.setActionCommand("Component Option");
    componentOptionsPanel.add(componentButton);

    // Brightness Adjusting Button
    brightnessButton = new JButton("Adjust Brightness");
    brightnessButton.setActionCommand("Brightness");
    commandPanel.add(brightnessButton);

    // PANEL THAT CONTAINS SAVE AND LOAD
    JPanel loadSave = new JPanel();
    loadSave.setBorder(BorderFactory.createTitledBorder(""));
    mainPanel.add(loadSave);

    // file open
    JPanel fileOpenPanel = new JPanel();
    fileOpenPanel.setLayout(new FlowLayout());
    loadSave.add(fileOpenPanel);
    fileOpenButton = new JButton("Open an image");
    fileOpenButton.setActionCommand("Open image");
    fileOpenPanel.add(fileOpenButton);

    // file save
    JPanel fileSavePanel = new JPanel();
    fileSavePanel.setLayout(new FlowLayout());
    loadSave.add(fileSavePanel);
    fileSaveButton = new JButton("Save");
    fileSaveButton.setActionCommand("Save file");
    fileSavePanel.add(fileSaveButton);

    // PANEL THAT CONTAINS EXIT
    JPanel exiting = new JPanel();
    exiting.setBorder(BorderFactory.createTitledBorder(""));
    mainPanel.add(exiting);

    // exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit");
    exiting.add(exitButton);

    pack();
    setVisible(true);
  }

  /**
   * @return
   */
  @Override
  public String userSavePath() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return null;
    // throw new IllegalStateException("The filetype was not valid");
  }

  /**
   * @return
   */
  @Override
  public String userLoadPath() {
    final JFileChooser fchooser = new JFileChooser(".");

    // adding ppm as a valid file extension
    String[] noPPM = ImageIO.getReaderFormatNames();
    String[] extensions = new String[noPPM.length + 1];
    System.arraycopy(noPPM, 0, extensions, 0, noPPM.length);
    extensions[noPPM.length] = "ppm";

    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Standard Image Extensions", extensions);
    fchooser.setFileFilter(filter);
    int retValue = fchooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      // imageLabel.setIcon(new ImageIcon(f.getAbsolutePath()));
      return f.getAbsolutePath();
    }
    return null;
    // throw new IllegalStateException("The filetype was not valid");
  }

  /**
   * @param newImage
   */
  @Override
  public void updateImage(BufferedImage newImage) {
    imageLabel.setIcon(new ImageIcon(newImage));
  }

  /**
   * @return
   */
  @Override
  public BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
          ImageProcessingModelImpl.Pixel> showComponentOptions() {
    // the different possible options, represented as strings (used as names for the buttons)
    String[] options = {"Red", "Green", "Blue", "Value", "Intensity", "Luma"};
    // Array of BiFunctions that corresponds if the options
    BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
            ImageProcessingModelImpl.Pixel>[] representation = new BiFunction[]{
            new RedBiFunction(), new GreenBiFunction(), new BlueBiFunction(), new ValueBiFunction(),
            new IntensityBiFunction(), new LumaBiFunction()};
    // choosing a BiFunction given what button was pressed
    int retValue = JOptionPane.showOptionDialog(this, "Component to be Visualized",
            "Components", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
            representation[4]);
    if (retValue == -1) {
      throw new IllegalStateException("There was no user input");
    }
    // return the BiFunction
    return representation[retValue];
  }

  @Override
  public int userBrightnessInput()  {
    String input = "";
    try {
      input = JOptionPane.showInputDialog("negative number to darken, positive number to brighten");
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      if (input != null) {
        showErrorMessage("Must provide an integer value.");
      }
    }
    throw new IllegalStateException("No input provided");
  }

  /**
   * @param errorMessage
   */
  @Override
  public void showErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(this, errorMessage, "Error!",
            JOptionPane.PLAIN_MESSAGE);
  }

  /**
   * @param features
   */
  public void addFeatures(Features features) {
    exitButton.addActionListener(event -> features.exitProgram());
    horizontalFlipButton.addActionListener(event -> features.flipImage(FlipType.Horizontal));
    verticaLFlipButton.addActionListener(event -> features.flipImage(FlipType.Vertical));
    sepiaButton.addActionListener(event -> features.colorChanging(new ColorTransformationBiFunction(
            ImageControllerImpl.SEPIA_TONE)));
    greyscaleButton.addActionListener(event -> features.colorChanging(
            new ColorTransformationBiFunction(ImageControllerImpl.GREYSCALE)));
    sharpenButton.addActionListener(event -> features.colorChanging(
            new FilterBiFunction(ImageControllerImpl.SHARPEN_KERNEL)));
    blurButton.addActionListener(event -> features.colorChanging(
            new FilterBiFunction(ImageControllerImpl.BLUR_KERNEL)));
    componentButton.addActionListener(event -> features.componentOptions());
    brightnessButton.addActionListener(event -> features.imageBrightness());

    fileOpenButton.addActionListener(event -> features.loadImageToDisplay());
    fileSaveButton.addActionListener(event -> features.saveCurrentImage());
  }


  // #################################### RELATED TO SCRIPTING ####################################

  /**
   * Renders a provided message by appending it to the appendable. This is used in Scripting and not
   * GUI.
   *
   * @param message the message to be rendered.
   */
  @Override
  public void renderMessage(String message) {
    delegate.renderMessage(message);
  }

  /**
   * Displays an intro message when the program is started. This is used for the Scripting mode and
   * is not GUI related.
   */
  @Override
  public void introMessage() {
    delegate.introMessage();
  }
}
