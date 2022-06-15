package imageprocessing.model;

public interface IAdvanceImageProcessing extends ImageProcessingModel {

  void blur();

  void sharpen();


/*
  int[][] kernel = { 1/16, 1/8, 1/16
                     1/16, 1/4, 1/16
                     1/16, 1/8, 1/16}


  Pixel[][] image = { 100, 200, 80
                      50, 30, 60
                      90, 110, 255}




  pixel 100 = image[0][0]
  pixel 200 = image[1][0]
  pixel 50 = image[0][1]
  pixel 30 = image[1][1]

  pixel 100 = image(kernel[0][0])
  pixel 200 = image(kernel[1][0])


  */





}
