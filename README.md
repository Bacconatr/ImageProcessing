# Basic Image Processing

## New Features

1. Added blur, sharpen, sepia, and greyscale commands that the user can use to change the image
   1. Blur blurs the image 
   2. Sharpen sharpens the image
   3. Greyscale uses a luma based greyscale
   4. Sepia applies a sepia tone to the image
2. Users can now load and save image files (bmp, jpg, png, etc.) in addition do .ppm files
   1. The user can specify the extension of the file when providing the file path to the save 
      command
3. Program can now parse script files (.txt files) to read and execute commands.

## Design/Implementation Changes and Additions with Justification

1. Model
    1. Refactored the model (ImageProcessingModelImpl) and its interface (ImageProcessingModel) 
       to no longer support load and save commands as load and save take in user input and 
       instead should be done by the Controller
    2. The ImageProcessingModel interface consequently no longer ensure the readPPM and loadPPM 
       methods.
    3. Added a method to the ImageProcessingModel called addImage() that takes in a 2D Pixel 
       array (which represents the state of an image) and the String name of the image. addImage 
       adds that image state and String value to the field that stores the loaded images and 
       their respective names. This change was necessary so that the controller can parse image 
       data and pass it to the model to be worked on.
       1. ImageProcessingModelImpl implements addImage() method.
2. BiFunctions
   1. Created a new FilterBiFunction that applies a kernel on a pixel (adjusting the RGB values).
      This is used by the createRepresentation method and allows for blur and sharpen functionality
   2. Created a new ColorTransformationBiFunction that applies a provided 3x3 matrix to 
      transform the RGB value at the given pixel. This is used by the createRepresentation 
      method and allows for Sepia and Greyscale functionality.
3. Controller
    1. Changed the Controller so that it now supports load and save command since they take in user
       input. The Load and Save functionalities are not public methods since direct access to 
       these methods is not required.
    2. The Save and Load macros were removed since the model passed to those macros can no
       longer perform load and save operations.
    3. The StartProcessing Method was refactored to handle the load and save commands since the 
       macros are no longer functional.
    4. An additional controller was created ImageControllerAdvancedImpl that extends the 
       previous controller (ImageControllerImpl) but allows for PPM support and image file save and 
       load support
    5. Added four new commands "blur", "sharpen", "sepia", and "greyscale" into the map of known 
       commands (allows for the user to actually call the commands).
4. Main Method
   1. Refactored the Main Method to use the updated controller (ImageControllerAdvancedImpl).
   2. Refactored the Main method to accept script files in addition to the previous functionality.

## Purpose of Program:

This application allows for the processing of images. A user is able to upload their images by 
providing the source path into the program and perform processes on that image. Basic image 
processes that are supported include brightening an image, darkening an image, flipping an image,
and grey-scaling an image.

## _**Running the Program**_

### Using the Program Interactively

Running the program without command line arguments allows the user to type in inputs that tell
the program what to do. This program by design is primarily meant to be interacted with through 
the script command line arguments are also possible and are supported (see below).

If the user types in an argument with spaces or new lines in between arguments things will work 
normally. In the script.txt file seen in the res/ directory, the first three lines is an example 
of code with commands and arguments with both spaces and new lines separating inputs.

The program first prompts a user for a valid command (see list of possible commands in Command 
Design Pattern section). If the user types a command that is invalid then they will be asked to 
type in a new command (without having to go to a new line). 

Once the user types a valid command, they will then be prompted for the arguments necessary for 
that command.

#### _**Invalid Inputs**_

If a user inputs a valid command but has invalid arguments, they 
are required to go to a new line and enter a valid command with valid arguments again. This was 
a design choice to ensure that a user is able to separate the invalid command with invalid 
arguments from a correct one. Although this stops the command line from re-prompting after 
invalid arguments, it is a design choice we stick with for improved user interaction in the 
terminal.

To summarize, we believe that the user can have incorrect inputs for a command and stay on a new 
line, but once they type in an existing command, the focus is now on what arguments they are 
typing and if those are invalid we emphasize starting with a new command.

### Using Command Line Arguments

If command arguments are used then scripting is not supported. If command line arguments have 
invalid commands the program will throw an exception since we implemented our controller to skip 
a line if illegal arguments are found. Consequently, the command line arguments which are 
represented as one line are all skipped and an exception is thrown. We believed this to be valid 
since in an interactive program it is easier to type mistakes, but through the command line 
users can be more precise and have exact commands to run. 

This is not to say that we don't handle illegal arguments, as we still throw exceptions for 
invalid user input. 

## Interface Design and Associated Models:

1. Model
   1. We first created an interface named _ImageProcessingState_. This has 2 purposes.

         First it 
         allows for interface segregation. Second, it allows us to represent any given 
      image 
         state as a 2d array of type pixel (although our interface allows for different types of 
         implementations of image state depending on the model's needs). 
         1. Although the view does not currently use the model's state for rendering, we added this 
            interface and the associated image state methods since we believe that future views 
            will require this information. 
   2. Then we created an interface named ImageProcessingModel. This houses all of our
         method stubs like our supported operations on images such as read, save, flip, brighten,
      etc.
   3. Next was to implement all of our interfaces, so we could perform operations to process the
         image. To do so we created a class called ImageProcessingModelImpl which implements 
         ImageProcessingModel interface and that interface extends ImageProcessingState. In this 
         class we implement all the operations that would be needed to process an image or 
         perform operation like grey-scaling or flipping an image.
      1. In this model the images are represented and stored into a HashMap as a 2D Pixel Array 
         with a String as a key (which acts as an image's name). When a user loads an image and 
         provides it an image name, this image name is used as the key and becomes associated 
         with the 2D Pixel array that represents the image that was loaded.
   4. For our model we needed to create a Pixel inner class to represent a single pixel in an 
      image. A Pixel is a simple object that stores three integers: A Red value, a Green value, 
      and a Blue value (representing the RGB values of a pixel). All RGB values are set at a 
      range of 0-255 which is enforced in the constructor. 
      The Pixel class has methods that 
      allow us to know the values of the fields 

2. View
   1. For the View we first started off with the IProcessingImageView interface, in this view we
         have 2 method stubs introMessage() and renderMessage(), this interface, when implemented, 
         would allow us to transmit introductory messages or any other messages that we would like 
         to have written to the output.
   2. For the implementation we created a class called BasicImageProcessingView which implements
         our IProcessingImage interface. With this we could finally render an introductory 
         message, welcoming the user to our program and we could also render messages to the output 
         after a user has successfully (or unsuccessfully) used a command. 

3. Controller
   1. Lastly we needed the Controller in order to take in user input so then we could pass to
         our model. In order to do so we first started off with the IProcessingImageController 
         interface. This had one method stub of startProcessing() which would be used to 
         run the program and pass the user inputs into our model so that the correct operation 
         could be performed on the user inputted information. 
   2. We then created ImageControllerImpl which implements IProcessingImageController, and in 
      here we would define our method stub from our interface and thus had a ready, working 
      controller that would run the program and await for user input to then be sent to our model.
   3. This controller uses the Command Design Pattern which is covered in another section. All the 
      existing commands are stored in a Map of image processing commands.
   4. The controller first prompts the user to input a valid command. If the user inputs a 
      command that is not existing then they will be re-prompted until a valid command is 
      inputted. From there the controller then prompts for the appropriate arguments. 



## The Command Design Pattern

To avoid excessive modification of source code and long switch-statements, we utilized the 
Command Design pattern in our controller. All possible commands are placed into a HashMap in 
which the controller can retrieve an appropriate macro given the key.

### Storing The Commands (As Macros)
To store the commands we had a Map of imageProcessingCommands that have a String as a key which 
acts as the command name. All the possible keys are the first string of each command shown 
below in "Support Commands". 

### Supported Commands
    load [file path] [image name]

    save [file path] [image name]

    brighten [increment (a possitive integer)] [image name] [new image name]

    darken [increment (a positie] [image name] [new image name]

    vertical-flip [image name] [new image name]

    horizontal-flip [image name] [new image name]

    red-component [image name] [new image name]

    green-component [image name] [new image name]

    blue-component [image name] [new image name]

    value-component [image name] [new image name]

    intensity-component [image name] [new image name]

    luma-component [image name] [new image name]

    sharpen [image name] [new image name]


### Valid Inputs

An input is valid if it follows the format above. Generally, arguments and commands are 
separated by spaces or new-lines. As long as the commands and arguments are valid, spaces are 
all that is needed to seperate the inputs (this is not the case for invalid arguments).

### Invalid Inputs

Invalid inputs are made up by commands that do not exist and arguments that are invalid for a 
given command. 

# Images Utilized

We used an elephant as our image demonstration.

![elephant image](res/elephant.png)

Credit: https://www.pngall.com/elephant-png

Creative Commons Info from the website: https://creativecommons.org/licenses/by-nc/4.0/

A 2x2 square image was self-made and is used for testing.
