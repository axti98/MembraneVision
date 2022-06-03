#include "SegmentationHandler.h"

SegmentationHandler::SegmentationHandler(std::string *AImage_paths, int AImage_count) {
   image_paths = AImage_paths;
   image_count = AImage_count;
}

cv::Mat* SegmentationHandler::execute_segmentation() {

    cv::Mat images[image_count];
    cv::Mat* threshholded_images = new cv::Mat[image_count];

    for(int i = 0; i < image_count; i++)
    {
        images[i] = cv::imread(image_paths[i], cv::IMREAD_COLOR);
        cv::threshold(images[i], threshholded_images[i], 125, 255, 0);
        cv::cvtColor(threshholded_images[i], threshholded_images[i], cv::COLOR_BGR2GRAY);
        cv::imwrite("im" + std::to_string(i) + ".png",threshholded_images[i]);
    }

    return threshholded_images;
}

